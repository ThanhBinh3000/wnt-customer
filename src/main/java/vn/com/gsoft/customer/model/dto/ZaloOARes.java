package vn.com.gsoft.customer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZaloOARes {
    private Long id;
    private String userName;
    private String userId;
    private String drugStoreCode;
    private String avatar;
}
