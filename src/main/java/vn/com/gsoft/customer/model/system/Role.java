package vn.com.gsoft.customer.model.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private Long roleId;
    private String roleName;
    private Boolean isDeleted;
    private String maNhaThuoc;
    private String description;
    private Integer type;  // 0 mặc định, 1 của nhà thuốc
}

