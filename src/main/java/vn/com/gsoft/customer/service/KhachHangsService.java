package vn.com.gsoft.customer.service;


import org.springframework.data.domain.Page;
import vn.com.gsoft.customer.entity.KhachHangs;
import vn.com.gsoft.customer.model.dto.*;

import java.util.List;

public interface KhachHangsService extends BaseService<KhachHangs, KhachHangsReq, Long> {
    Page<KhachHangsRes> searchCustomerManagementPage(KhachHangsReq objReq) throws Exception;
    Page<ZaloOARes> searchPageFlowerOAByStoreCode(ZaloOAReq req) throws Exception;
    int updateMappingStore(MappingKhachHangReq req) throws Exception;
    int updateMappingZaloOA(MappingKhachHangReq req) throws Exception;
}