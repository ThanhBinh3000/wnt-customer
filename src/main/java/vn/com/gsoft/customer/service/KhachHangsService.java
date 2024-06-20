package vn.com.gsoft.customer.service;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import vn.com.gsoft.customer.entity.KhachHangs;
import vn.com.gsoft.customer.model.dto.*;

import java.util.List;

public interface KhachHangsService extends BaseService<KhachHangs, KhachHangsReq, Long> {
    Page<ZaloOARes> searchPageFlowerOAByStoreCode(ZaloOAReq req) throws Exception;
    Integer updateMappingStore(MappingKhachHangReq req) throws Exception;
    Integer updateMappingZaloOA(MappingKhachHangReq req) throws Exception;
    Integer updateThongTinKhuVuc(ThongTinKhuVucReq req) throws Exception;

    Double getPaymentScore(Long id);
    void export(KhachHangsReq req, HttpServletResponse response) throws  Exception;
    boolean importExcel(MultipartFile file) throws Exception;
}