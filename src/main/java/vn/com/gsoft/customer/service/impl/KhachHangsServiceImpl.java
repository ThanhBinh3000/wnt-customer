package vn.com.gsoft.customer.service.impl;


import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.protocol.types.Field;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.com.gsoft.customer.constant.RecordStatusContains;
import vn.com.gsoft.customer.entity.KhachHangs;
import vn.com.gsoft.customer.entity.NhomKhachHangs;
import vn.com.gsoft.customer.model.dto.KhachHangsReq;
import vn.com.gsoft.customer.model.dto.KhachHangsRes;
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
		Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
		return DataUtils.convertPage(hdrRepo.searchCustomerManagementPage(req, pageable), KhachHangsRes.class);
	}

	@Override
	public KhachHangs insertCustomer(KhachHangsReq req) throws Exception {
		Profile userInfo = this.getLoggedUser();
		if (userInfo == null)
			throw new Exception("Bad request.");

		var drugStoreCode = userInfo.getNhaThuoc().getMaNhaThuoc();
		if (req.getSoDienThoai() != null){
			List<KhachHangs> customers = this.hdrRepo.findCustomerByPhoneNumber(req.getSoDienThoai(), drugStoreCode);
			if(!customers.isEmpty()) throw new Exception("Số điện thoại khách hàng đã tồn tại");
		}
		if (req.getCode() != null){
			List<KhachHangs> customers = this.hdrRepo.findCustomerByCode(req.getCode(), drugStoreCode);
			if(!customers.isEmpty()) throw new Exception("Mã khách hàng đã tồn tại");
		}
		if (req.getBarCode() != null){
			List<KhachHangs> customers = this.hdrRepo.findCustomerByBarcode(req.getBarCode(), drugStoreCode);
			if(!customers.isEmpty()) throw new Exception("Mã vạch khách hàng đã tồn tại");
		}
		KhachHangs e = new KhachHangs();
		BeanUtils.copyProperties(req, e, "id");
		if (e.getRecordStatusId() == null) {
			e.setRecordStatusId(RecordStatusContains.ACTIVE);
		}
		if (e.getMaNhomKhachHang() == null){
			e.setMaNhomKhachHang(0L);
		}
		e.setCreated(Date.from(Instant.now()));
		e.setCreatedByUserId(userInfo.getId());
		e = hdrRepo.save(e);
		if(e.getId() > 0 && e.getNoDauKy().compareTo(BigDecimal.valueOf(0)) > 0){

		}
		return e;
	}
}

