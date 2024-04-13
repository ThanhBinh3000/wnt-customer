package vn.com.gsoft.customer.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class PhieuXuatNoDauKyRes {
    private String maNhaThuoc;
    private Long maKhachHang;
    private Date ngayXuat;
    private Date created ;
    private Long createdByUserId;
    private Long recordStatusId;
    private Boolean isDebt;
    private BigDecimal tongTien;
    private Long maLoaiXuatNhap;
    private Long storeId;
    private Long modifiedByUserId;
    private Date modified;
    private Long id;
}
