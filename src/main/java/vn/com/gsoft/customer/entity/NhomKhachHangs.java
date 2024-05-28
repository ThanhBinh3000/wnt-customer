package vn.com.gsoft.customer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "NhomKhachHangs")
public class NhomKhachHangs extends BaseEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    private String tenNhomKhachHang;
    private String ghiChu;
    @Column(name="NhaThuoc_MaNhaThuoc")
    private String nhaThuocMaNhaThuoc;
    private Boolean active;
    private Long groupTypeId;
    private String fullName;
    private String idCard;
    private Date birthDate;
    private String classId;
    private String mobile;
    private Long archivedId;
    private Long storeId;

}

