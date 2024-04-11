package vn.com.gsoft.customer.service.impl;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.com.gsoft.customer.constant.RecordStatusContains;
import vn.com.gsoft.customer.entity.KhachHangs;
import vn.com.gsoft.customer.model.dto.KhachHangsReq;
import vn.com.gsoft.customer.model.dto.KhachHangsRes;
import vn.com.gsoft.customer.model.dto.PhieuXuatNoDauKy;
import vn.com.gsoft.customer.model.dto.ZaloOARes;
import vn.com.gsoft.customer.model.system.Profile;
import vn.com.gsoft.customer.repository.KhachHangsRepository;
import vn.com.gsoft.customer.service.KhachHangsService;
import vn.com.gsoft.customer.util.system.DataUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Log4j2
public class KhachHangsServiceImpl extends BaseServiceImpl<KhachHangs, KhachHangsReq,Long> implements KhachHangsService {

	private KhachHangsRepository hdrRepo;
	@Autowired
	public KhachHangsServiceImpl(KhachHangsRepository hdrRepo) {
		super(hdrRepo);
		this.hdrRepo = hdrRepo;
	}

	@Override
	public Page<KhachHangsRes> searchCustomerManagementPage(KhachHangsReq req) throws Exception {
		Profile userInfo = this.getLoggedUser();
		if (userInfo == null)
			throw new Exception("Bad request.");

		String storeCode = userInfo.getNhaThuoc().getMaNhaThuoc();
		req.setMaNhaThuoc(storeCode);
		req.setRecordStatusId(req.getDataDelete() ? RecordStatusContains.DELETED : RecordStatusContains.ACTIVE);
		Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
		return DataUtils.convertPage(hdrRepo.searchCustomerManagementPage(req, pageable), KhachHangsRes.class);
	}

	@Override
	public KhachHangs create(KhachHangsReq req) throws Exception {
		Profile userInfo = this.getLoggedUser();
		if (userInfo == null)
			throw new Exception("Bad request.");

		var storeCode = userInfo.getNhaThuoc().getMaNhaThuoc();
		if ( req.getSoDienThoai() != null && !req.getSoDienThoai().trim().isEmpty()){
			List<KhachHangs> customers = this.hdrRepo.findCustomerByPhoneNumber(req.getSoDienThoai(), storeCode, null);
			if(!customers.isEmpty()) throw new Exception("Số điện thoại khách hàng đã tồn tại");
		}
		if (req.getCode() != null && !req.getCode().trim().isEmpty()){
			List<KhachHangs> customers = this.hdrRepo.findCustomerByCode(req.getCode(), storeCode, null);
			if(!customers.isEmpty()) throw new Exception("Mã khách hàng đã tồn tại");
		}
		if (req.getBarCode() != null && !req.getBarCode().trim().isEmpty()){
			List<KhachHangs> customers = this.hdrRepo.findCustomerByBarcode(req.getBarCode(), storeCode, null);
			if(!customers.isEmpty()) throw new Exception("Mã vạch khách hàng đã tồn tại");
		}
		KhachHangs e = new KhachHangs();
		BeanUtils.copyProperties(req, e,"id");
		if (e.getRecordStatusId() == null) {
			e.setRecordStatusId(RecordStatusContains.ACTIVE);
		}
		e.setCreated(Date.from(Instant.now()));
		e.setCreatedByUserId(userInfo.getId());

		e = hdrRepo.save(e);
		if (e.getNoDauKy() != null && e.getId() > 0){
			if(e.getNoDauKy().compareTo(BigDecimal.valueOf(0)) > 0){
              taoPhieuDauKy(storeCode, e.getId(), userInfo.getId(), e.getNoDauKy(),
					  userInfo.getNhaThuoc().getId());
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
		if ( req.getSoDienThoai() != null && !req.getSoDienThoai().trim().isEmpty()){
			List<KhachHangs> customers = this.hdrRepo.findCustomerByPhoneNumber(req.getSoDienThoai(), storeCode, req.getId());
			if(!customers.isEmpty()) throw new Exception("Số điện thoại khách hàng đã tồn tại");
		}
		if (req.getCode() != null && !req.getCode().trim().isEmpty()){
			List<KhachHangs> customers = this.hdrRepo.findCustomerByCode(req.getCode(), storeCode, req.getId());
			if(!customers.isEmpty()) throw new Exception("Mã khách hàng đã tồn tại");
		}
		if (req.getBarCode() != null && !req.getBarCode().trim().isEmpty()){
			List<KhachHangs> customers = this.hdrRepo.findCustomerByBarcode(req.getBarCode(), storeCode, req.getId());
			if(!customers.isEmpty()) throw new Exception("Mã vạch khách hàng đã tồn tại");
		}
		Optional<KhachHangs> optional = hdrRepo.findById(req.getId());
		KhachHangs e = optional.get();
		BeanUtils.copyProperties(req, e,"id");

		e.setModified(Date.from(Instant.now()));
		e.setModifiedByUserId(userInfo.getId());

		e = hdrRepo.save(e);
		if (e.getNoDauKy() != null){
			taoPhieuDauKy(storeCode, e.getId(), userInfo.getId(), e.getNoDauKy(), userInfo.getNhaThuoc().getId());
		}
		return e;
	}
	@Override
	public List<ZaloOARes> searchListFllowerOAByStoreCode(String storeCode) throws Exception{
		Profile userInfo = this.getLoggedUser();
		if (userInfo == null)
			throw new Exception("Bad request.");
		storeCode = userInfo.getNhaThuoc().getMaNhaThuoc();
		return DataUtils.convertList(hdrRepo.searchListFllowerOAByStoreCode(storeCode), ZaloOARes.class);

	}
	private void taoPhieuDauKy(String storeCode, Long maKhachHang,
							   Long userId, BigDecimal tongTien, Long storeId) throws Exception{
		var phieuXuatNoDauKy = new PhieuXuatNoDauKy();
		List<PhieuXuatNoDauKy> phieuXuatNoDauKys = this.hdrRepo.findPhieuXuatNoDauKyById(storeCode, maKhachHang);
		Long recordStatusId = 0L;
		if(!phieuXuatNoDauKys.isEmpty()){
			recordStatusId = tongTien.compareTo(BigDecimal.valueOf(0)) == 0
					? RecordStatusContains.DELETED : RecordStatusContains.ACTIVE;
			phieuXuatNoDauKy.setModified(Date.from(Instant.now()));
			phieuXuatNoDauKy.setModifiedByUserId(userId);
			phieuXuatNoDauKy.setRecordStatusId(recordStatusId);
			phieuXuatNoDauKy.setTongTien(tongTien);
			phieuXuatNoDauKy.setId(phieuXuatNoDauKys.get(0).getId());
			hdrRepo.updatePhieuXuatNoDauKy(phieuXuatNoDauKy);
		}else{
			phieuXuatNoDauKy.setNgayXuat(Date.from(Instant.now()));
			phieuXuatNoDauKy.setCreated(Date.from(Instant.now()));
			phieuXuatNoDauKy.setMaLoaiXuatNhap(7L);
			phieuXuatNoDauKy.setMaKhachHang(maKhachHang);
			phieuXuatNoDauKy.setCreatedByUserId(userId);
			phieuXuatNoDauKy.setIsDebt(true);
			phieuXuatNoDauKy.setStoreId(storeId);
			phieuXuatNoDauKy.setMaNhaThuoc(storeCode);
			phieuXuatNoDauKy.setTongTien(tongTien);
			phieuXuatNoDauKy.setRecordStatusId(RecordStatusContains.ACTIVE);

			this.hdrRepo.insertPhieuXuatNoDauKy(phieuXuatNoDauKy);
		}


	}

}

