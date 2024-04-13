package vn.com.gsoft.customer.model.dto;

import lombok.Data;
import vn.com.gsoft.customer.model.system.BaseRequest;

@Data
public class ThongTinKhuVucReq extends BaseRequest {
    private Long id;
    private Integer cityId;
    private Integer wardId;
    private Integer regionId;
}
