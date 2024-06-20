package vn.com.gsoft.customer.service.impl;


import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.com.gsoft.customer.constant.ENoteType;
import vn.com.gsoft.customer.constant.ImportConstant;
import vn.com.gsoft.customer.constant.RecordStatusContains;
import vn.com.gsoft.customer.entity.CustomerBonusPayment;
import vn.com.gsoft.customer.entity.KhachHangs;
import vn.com.gsoft.customer.entity.NhomKhachHangs;
import vn.com.gsoft.customer.entity.PhieuXuats;
import vn.com.gsoft.customer.model.dto.*;
import vn.com.gsoft.customer.model.system.PaggingReq;
import vn.com.gsoft.customer.model.system.Profile;
import vn.com.gsoft.customer.model.system.WrapData;
import vn.com.gsoft.customer.repository.*;
import vn.com.gsoft.customer.service.KafkaProducer;
import vn.com.gsoft.customer.service.KhachHangsService;
import vn.com.gsoft.customer.util.system.DataUtils;
import vn.com.gsoft.customer.util.system.ExportExcel;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;


@Service
@Log4j2
public class KhachHangsServiceImpl extends BaseServiceImpl<KhachHangs, KhachHangsReq, Long> implements KhachHangsService {
    //region Fields
    private KhachHangsRepository hdrRepo;
    private KhachHangsRepository khachHangsRepository;
    private CustomerBonusPaymentRepository customerBonusPaymentRepository;
    private NhaThuocsRepository nhaThuocsRepository;
    private FollowerZaloOARepository followerZaloOARepository;
    private NhomKhachHangsRepository nhomKhachHangsRepository;
    private PhieuXuatsRepository phieuXuatsRepository;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Value("${wnt.kafka.internal.consumer.topic.import-master}")
    private String topicName;

    //endregion
    //region Interface Implementation
    @Autowired
    public KhachHangsServiceImpl(KhachHangsRepository hdrRepo,
                                 KhachHangsRepository khachHangsRepository,
                                 CustomerBonusPaymentRepository customerBonusPaymentRepository,
                                 NhaThuocsRepository nhaThuocsRepository,
                                 FollowerZaloOARepository followerZaloOARepository,
                                 NhomKhachHangsRepository nhomKhachHangsRepository,
                                 PhieuXuatsRepository phieuXuatsRepository) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
        this.customerBonusPaymentRepository = customerBonusPaymentRepository;
        this.khachHangsRepository= khachHangsRepository;
        this.nhaThuocsRepository = nhaThuocsRepository;
        this.followerZaloOARepository = followerZaloOARepository;
        this.nhomKhachHangsRepository = nhomKhachHangsRepository;
        this.phieuXuatsRepository = phieuXuatsRepository;
    }

    @Override
    public Page<KhachHangs> searchPage(KhachHangsReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        String storeCode = userInfo.getNhaThuoc().getMaNhaThuoc();
        req.setMaNhaThuoc(storeCode);
        req.setRecordStatusId(req.getDataDelete() ? RecordStatusContains.DELETED : RecordStatusContains.ACTIVE);
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        Page<KhachHangs> khachHangs= hdrRepo.searchPage(req,pageable);
        for (KhachHangs kh: khachHangs.getContent()){
            if(kh.getMappingStoreId() != null && kh.getMappingStoreId() > 0){
               kh.setNhaThuoc(this.nhaThuocsRepository.findByIdAndHoatDong(Long.valueOf(kh.getMappingStoreId()), true));
            }
            if(kh.getZaloId()!= null && !kh.getZaloId().isEmpty()){
                kh.setFollowerZaloOA(this.followerZaloOARepository.findByUserId(kh.getZaloId()));
            }
            if(kh.getMaNhomKhachHang() != null && kh.getMaNhomKhachHang() > 0){
                Optional<NhomKhachHangs> byId = nhomKhachHangsRepository.findById(kh.getMaNhomKhachHang());
                byId.ifPresent(kh::setNhomKhachHangs);
            }
        }
        return khachHangs;
    }

    @Override
    public KhachHangs create(KhachHangsReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        var storeCode = userInfo.getNhaThuoc().getMaNhaThuoc();
        if (req.getSoDienThoai() != null && !req.getSoDienThoai().trim().isEmpty()) {
            List<KhachHangs> customers = this.hdrRepo.findCustomerByPhoneNumber(req.getSoDienThoai(), storeCode, null);
            if (!customers.isEmpty()) throw new Exception("Số điện thoại khách hàng đã tồn tại");
        }
        if (req.getCode() != null && !req.getCode().trim().isEmpty()) {
            List<KhachHangs> customers = this.hdrRepo.findCustomerByCode(req.getCode(), storeCode, null);
            if (!customers.isEmpty()) throw new Exception("Mã khách hàng đã tồn tại");
        }
        if (req.getBarCode() != null && !req.getBarCode().trim().isEmpty()) {
            List<KhachHangs> customers = this.hdrRepo.findCustomerByBarcode(req.getBarCode(), storeCode, null);
            if (!customers.isEmpty()) throw new Exception("Mã vạch khách hàng đã tồn tại");
        }
        KhachHangs e = new KhachHangs();
        BeanUtils.copyProperties(req, e, "id");
        if (e.getRecordStatusId() == null) {
            e.setRecordStatusId(RecordStatusContains.ACTIVE);
        }
        e.setCreated(Date.from(Instant.now()));
        e.setCreatedByUserId(userInfo.getId());

        e = hdrRepo.save(e);
        if (e.getNoDauKy() != null && e.getId() > 0) {
            if (e.getNoDauKy().compareTo(BigDecimal.valueOf(0)) > 0) {
                taoPhieuDauKy(storeCode, e.getId(), userInfo.getId(), e.getNoDauKy().doubleValue(), userInfo.getNhaThuoc().getId());
            }
        }
        return e;
    }

    @Override
    public KhachHangs update(KhachHangsReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        var storeCode = userInfo.getNhaThuoc().getMaNhaThuoc();
        if (req.getSoDienThoai() != null && !req.getSoDienThoai().trim().isEmpty()) {
            List<KhachHangs> customers = this.hdrRepo.findCustomerByPhoneNumber(req.getSoDienThoai(), storeCode, req.getId());
            if (!customers.isEmpty()) throw new Exception("Số điện thoại khách hàng đã tồn tại");
        }
        if (req.getCode() != null && !req.getCode().trim().isEmpty()) {
            List<KhachHangs> customers = this.hdrRepo.findCustomerByCode(req.getCode(), storeCode, req.getId());
            if (!customers.isEmpty()) throw new Exception("Mã khách hàng đã tồn tại");
        }
        if (req.getBarCode() != null && !req.getBarCode().trim().isEmpty()) {
            List<KhachHangs> customers = this.hdrRepo.findCustomerByBarcode(req.getBarCode(), storeCode, req.getId());
            if (!customers.isEmpty()) throw new Exception("Mã vạch khách hàng đã tồn tại");
        }
        Optional<KhachHangs> optional = hdrRepo.findById(req.getId());
        KhachHangs e = optional.get();
        BeanUtils.copyProperties(req, e, "id");

        e.setModified(Date.from(Instant.now()));
        e.setModifiedByUserId(userInfo.getId());

        e = hdrRepo.save(e);
        if (e.getNoDauKy() != null) {
            taoPhieuDauKy(storeCode, e.getId(), userInfo.getId(), e.getNoDauKy().doubleValue(), userInfo.getNhaThuoc().getId());
        }
        return e;
    }

    @Override
    public Page<ZaloOARes> searchPageFlowerOAByStoreCode(ZaloOAReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        var storeCode = userInfo.getNhaThuoc().getMaNhaThuoc();
        req.setMaNhaThuoc(storeCode);
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        return DataUtils.convertPage(hdrRepo.searchPageFlowerOAByStoreCode(req, pageable), ZaloOARes.class);

    }

    @Override
    public Integer updateMappingStore(MappingKhachHangReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        if (req.getMaKhachHang() <= 0) return 0;
        Optional<KhachHangs> optional = hdrRepo.findById(req.getMaKhachHang());
        KhachHangs e = optional.get();
        e.setMappingStoreId(req.getMappingStoreId());
        e = hdrRepo.save(e);
        return 1;
    }

    @Override
    public Integer updateMappingZaloOA(MappingKhachHangReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        if (req.getMaKhachHang() <= 0) return 0;
        Optional<KhachHangs> optional = hdrRepo.findById(req.getMaKhachHang());
        KhachHangs e = optional.get();
        e.setZaloId(req.getZaloId());
        e = hdrRepo.save(e);
        return 1;
    }

    @Override
    public Integer updateThongTinKhuVuc(ThongTinKhuVucReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        if (req.getId() < 0) return 0;
        Optional<KhachHangs> optional = hdrRepo.findById(req.getId());
        KhachHangs e = optional.get();
        e.setWardId(req.getWardId());
        e.setCityId(req.getCityId());
        e.setRegionId(req.getRegionId());
        e.setDiaChi(req.getDiaChi());
        e = hdrRepo.save(e);
        return 1;
    }

    @Override
    public Double getPaymentScore(Long id) {
        List<Long> status = List.of(RecordStatusContains.ACTIVE, RecordStatusContains.ARCHIVED);
        List<CustomerBonusPayment> payments = customerBonusPaymentRepository.findByCustomerIdAndRecordStatusIdIn(id, status);
        Optional<KhachHangs> customer = khachHangsRepository.findById(id);
        double paymentScore = 0d;
        if (!payments.isEmpty()) {
            paymentScore = payments.stream().mapToDouble(CustomerBonusPayment::getScore)
                    .sum();
        }

        if (customer.isEmpty()){
            return 0d;
        }

        double total = customer.map(KhachHangs::getTotalScore).orElse(0d);

        return total - paymentScore;
    }

    //export
    @Override
    public void export(KhachHangsReq req, HttpServletResponse response) throws Exception{
        PaggingReq paggingReq = new PaggingReq();
        paggingReq.setPage(0);
        paggingReq.setLimit(Integer.MAX_VALUE);
        req.setPaggingReq(paggingReq);
        Page<KhachHangs> page = this.searchPage(req);
        List<KhachHangs> data = page.getContent();

        String title = "Khách hàng";
        String fileName = "danh_sach_khach_hang.xlsx";
        String[] rowsName = new String[]{
                "Id",
                "Mã khách hàng",
                "Nhóm khách hàng",
                "Tên khách hàng",
                "Loại khách hàng",
                "Mã số thuế",
                "Địa chỉ",
                "Số điện thoại",
                "Mã vạch",
                "Nợ đầu kỳ",
                "Đơn vị công tác",
                "Email",
                "Ghi chú",
                "Ngày sinh"
        };

        List<Object[]> dataList = convertToExcelModel(data, rowsName, false);

        ExportExcel exportExcel = new ExportExcel(title, fileName, rowsName, dataList, response);
        exportExcel.export();

    }
    //import
    @Override
    public boolean importExcel(MultipartFile file) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        Supplier<KhachHangs> khachHangSupplier = KhachHangs::new;
        BaseServiceImpl<KhachHangs, KhachHangsReq,Long> service = new BaseServiceImpl<>(khachHangSupplier);
        InputStream inputStream = file.getInputStream();
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            List<String> propertyNames = Arrays.asList("idText",
                    "code",
                    "tenNhomKhachHang",
                    "tenKhachHang",
                    "loaiKhachHang",
                    "taxCode",
                    "diaChi",
                    "soDienThoai",
                    "barCode",
                    "noDauKyText",
                    "donViCongTac",
                    "email",
                    "ghiChu",
                    "birthDateText");
            List<KhachHangs> khachHangs = new ArrayList<>(service.handleImportExcel(workbook, propertyNames));
            List<KhachHangs> khachHangsDone = new ArrayList<KhachHangs>();
            List<KhachHangs> khachHangsError = new ArrayList<KhachHangs>();
            for (int i = 1; i< khachHangs.size(); i ++){
                var khachHang = khachHangs.get(i);
                var msg = validateKhachHang(khachHang);
                if(!msg.isEmpty()){
                    khachHang.setResult(msg);
                    khachHangsError.add(khachHang);
                }else {
                    khachHang.setMaNhaThuoc(userInfo.getNhaThuoc().getMaNhaThuoc());
                    khachHang.setRecordStatusId(RecordStatusContains.ACTIVE);
                    khachHang.setCusType(khachHang.getLoaiKhachHang().equals("Tổ chức"));
                    if(!khachHang.getNoDauKyText().trim().isEmpty()){
                        khachHang.setNoDauKyText(khachHang.getNoDauKyText().replace(".00", ""));
                        khachHang.setNoDauKy(BigDecimal.valueOf(Integer.parseInt(khachHang.getNoDauKyText())));
                    }
                    if(!khachHang.getIdText().trim().isEmpty()){
                        khachHang.setId(Long.valueOf(khachHang.getIdText()));
                    }
                    if(!khachHang.getBirthDateText().trim().isEmpty())
                    {
                        khachHang.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(khachHang.getBirthDateText()));
                    }
                    khachHangsDone.add(khachHang);
                }
            }
            if(!khachHangsDone.isEmpty()){
                pushToKafka(khachHangsDone, userInfo);
            }

            if(!khachHangsError.isEmpty()){
                String title = "Khách hàng";
                String fileName = "danh_sach_khach_hang_loi.xlsx";
                String[] rowsName = new String[]{
                        "Id",
                        "Mã khách hàng",
                        "Nhóm khách hàng",
                        "Tên khách hàng",
                        "Loại khách hàng",
                        "Mã số thuế",
                        "Địa chỉ",
                        "Số điện thoại",
                        "Mã vạch",
                        "Nợ đầu kỳ",
                        "Đơn vị công tác",
                        "Email",
                        "Ghi chú",
                        "Ngày sinh",
                        "Result"
                };

                //List<Object[]> dataList = convertToExcelModel(khachHangsError, rowsName, true);

                //ExportExcel exportExcel = new ExportExcel(title, fileName, rowsName, dataList, response);
                //exportExcel.export();
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //endregion
    //region Private Methods
    private void taoPhieuDauKy(String storeCode, Long maKhachHang,
                               Long userId, Double tongTien, Long storeId) throws Exception {
        //kiểm tra xem tồn tại phiếu chưa
         PhieuXuats phieuXuat = this.phieuXuatsRepository.findByNhaThuocMaNhaThuocAndKhachHangMaKhachHangAndMaLoaiXuatNhapAndRecordStatusId(
                 storeCode,
                 maKhachHang,
                 ENoteType.InitialSupplierDebt,
                 (int) RecordStatusContains.ACTIVE);
        if(phieuXuat != null && phieuXuat.getId() != null && phieuXuat.getId() > 0){
           phieuXuat.setRecordStatusId(tongTien > 0 ? RecordStatusContains.ACTIVE : RecordStatusContains.DELETED_FOREVER);
           phieuXuat.setTongTien(tongTien);
           phieuXuat.setIsDebt(tongTien > 0);
        }else {
            phieuXuat = new PhieuXuats();
            phieuXuat.setKhachHangMaKhachHang(maKhachHang);
            phieuXuat.setSoPhieuXuat(0L);
            phieuXuat.setRecordStatusId(RecordStatusContains.ACTIVE);
            phieuXuat.setCreated(new Date());
            phieuXuat.setCreatedByUserId(userId);
            phieuXuat.setTongTien(tongTien);
            phieuXuat.setIsDebt(true);
            phieuXuat.setMaLoaiXuatNhap(ENoteType.InitialSupplierDebt.longValue());
            phieuXuat.setNhaThuocMaNhaThuoc(storeCode);
            phieuXuat.setStoreId(storeId);
            phieuXuat.setTargetId(null);
            phieuXuat.setTargetStoreId(null);
            phieuXuat.setTargetManagementId(null);
            phieuXuat.setIsModified(false);
            phieuXuat.setBackPaymentAmount(new BigDecimal(0L));
            phieuXuat.setConnectivityStatusID(0L);
            phieuXuat.setDaTra(0d);
            phieuXuat.setDiscount(0d);
            phieuXuat.setPaymentScore(new BigDecimal(0L));
            phieuXuat.setVat(0);
        }
        phieuXuatsRepository.save(phieuXuat);
    }

    private String validateKhachHang(KhachHangs khachHang){
        var msg = "";
        if(khachHang.getTenKhachHang().trim().isEmpty()){
            msg = "Tên khách hàng không được để trống";
        }
        if(khachHang.getTenNhomKhachHang().trim().isEmpty()){
            msg = msg + " ,Tên nhóm khách hàng không được để trống";
        }
        if(khachHang.getNoDauKyText() != null && !NumberUtils.isNumber(khachHang.getNoDauKyText())){
            msg = msg + ", Nợ đầu kỳ phải là số";
        }
        return  msg;
    }

    private List<Object[]> convertToExcelModel(List<KhachHangs> data, String[] rowsName, boolean duLieuLoi){
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objects = null;

        objects = new Object[rowsName.length];
        objects[0] = "Id";
        objects[1] = "Code";
        objects[2] ="TenNhomKhachHang";
        objects[3] = "TenKhachHang";
        objects[4] = "LoaiKhachHang";
        objects[5] = "TaxCode";
        objects[6] = "DiaChi";
        objects[7] = "SoDienThoai";
        objects[8] = "BarCode";
        objects[9] = "NoDauKy";
        objects[10] = "DonViCongTac";
        objects[11] = "Email";
        objects[12]= "GhiChu";
        objects[13]= "BirthDate";
        if(duLieuLoi){
            objects[14]= "Result";
        }
        dataList.add(objects);

        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        for (int i = 0; i< data.size(); i ++){
            KhachHangs khachHang = data.get(i);
            objects = new Object[rowsName.length];
            objects[0] = khachHang.getId();
            objects[1] = khachHang.getCode();
            objects[2] = khachHang.getNhomKhachHangs() != null ? khachHang.getNhomKhachHangs().getTenNhomKhachHang() : "";
            objects[3] = khachHang.getTenKhachHang();
            objects[4] = khachHang.getCusType() != null && khachHang.getCusType() ? "Tổ chức" : "Cá nhân";
            objects[5] = khachHang.getTaxCode();
            objects[6] = khachHang.getDiaChi();
            objects[7] = khachHang.getSoDienThoai();
            objects[8] = khachHang.getBarCode();
            objects[9] = khachHang.getNoDauKy();
            objects[10] = khachHang.getDonViCongTac();
            objects[11] = khachHang.getEmail();
            objects[12] = khachHang.getGhiChu();
            objects[13] = khachHang.getBirthDate() != null
                    ? simpleDateFormat.format(khachHang.getBirthDate()) : null;
            if(duLieuLoi){
                objects[14] = khachHang.getResult();
            }
            dataList.add(objects);
        }
        return dataList;
    }

    //thêm kafka
    private void pushToKafka(List<KhachHangs> khachHangs, Profile profile) throws ExecutionException, InterruptedException, TimeoutException {
        int size = khachHangs.size();
        int index = 1;
        UUID uuid = UUID.randomUUID();
        String bathKey = uuid.toString();
        for(KhachHangs kh :khachHangs){
            String key = kh.getMaNhaThuoc();
            WrapData data = new WrapData();
            data.setBathKey(bathKey);
            data.setCode(ImportConstant.KHACH_HANG);
            data.setSendDate(new Date());
            data.setData(kh);
            data.setTotal(size);
            data.setIndex(index++);
            data.setProfile(profile);
            kafkaProducer.sendInternal(topicName, key, new Gson().toJson(data));
        }
    }
    //endregion
}

