package vn.com.gsoft.customer.service.impl;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.gsoft.customer.entity.KhachHangs;
import vn.com.gsoft.customer.model.dto.KhachHangsReq;
import vn.com.gsoft.customer.repository.KhachHangsRepository;
import vn.com.gsoft.customer.service.KhachHangsService;


@Service
@Log4j2
public class KhachHangsServiceImpl extends BaseServiceImpl<KhachHangs, KhachHangsReq,Long> implements KhachHangsService {

	private KhachHangsRepository hdrRepo;
	@Autowired
	public KhachHangsServiceImpl(KhachHangsRepository hdrRepo) {
		super(hdrRepo);
		this.hdrRepo = hdrRepo;
	}

}
