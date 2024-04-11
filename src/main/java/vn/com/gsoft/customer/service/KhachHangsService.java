package vn.com.gsoft.customer.service;


import org.springframework.data.domain.Page;
import vn.com.gsoft.customer.entity.KhachHangs;
import vn.com.gsoft.customer.model.dto.KhachHangsReq;
import vn.com.gsoft.customer.model.dto.KhachHangsRes;
import vn.com.gsoft.customer.model.dto.ZaloOARes;

import java.util.List;

public interface KhachHangsService extends BaseService<KhachHangs, KhachHangsReq, Long> {
    Page<KhachHangsRes> searchCustomerManagementPage(KhachHangsReq objReq) throws Exception;

    List<ZaloOARes> searchListFllowerOAByStoreCode(String storeCode) throws Exception;
}