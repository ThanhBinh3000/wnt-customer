package vn.com.gsoft.customer.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
public class KhachHangsRes extends ThongTinKhuVucReq {
    private Long id;
    private String code;
    private String tenKhachHang;
    private String soDienThoai;
    private String barcode;
    private String tenNhomKhachHang;
    private Long mappingStoreId;
    private String zaloId;
    private Date created;
    private Date birthDate;
    private Integer cityId;
    private Integer wardId;
    private Integer regionId;
    private String diaChi;
}
