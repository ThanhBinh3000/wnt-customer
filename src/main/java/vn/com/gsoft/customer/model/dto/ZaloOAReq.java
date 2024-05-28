package vn.com.gsoft.customer.model.dto;

import lombok.Data;
import vn.com.gsoft.customer.model.system.BaseRequest;
@Data
public class ZaloOAReq extends BaseRequest {
    private Long id;
    private String userName;
    private String userId;
    private String drugStoreCode;
    private String avatar;
}
