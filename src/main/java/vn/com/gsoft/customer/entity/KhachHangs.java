package vn.com.gsoft.customer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.gsoft.customer.model.system.BaseRequest;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KhachHangs")
public class KhachHangs extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "MaKhachHang")
//    private Integer maKhachHang;
    @Column(name = "TenKhachHang")
    private String tenKhachHang;
    @Column(name = "DiaChi")
    private String diaChi;
    @Column(name = "SoDienThoai")
    private String soDienThoai;
    @Column(name = "NoDauKy")
    private BigDecimal noDauKy;
    @Column(name = "DonViCongTac")
    private String donViCongTac;
    @Column(name = "Email")
    private String email;
    @Column(name = "GhiChu")
    private String ghiChu;
    @Column(name = "MaNhaThuoc")
    private String maNhaThuoc;
    @Column(name = "MaNhomKhachHang")
    private Integer maNhomKhachHang;
    @Column(name = "Active")
    private Boolean active;
    @Column(name = "CustomerTypeId")
    private Integer customerTypeId;
    @Column(name = "BarCode")
    private String barCode;
    @Column(name = "BirthDate")
    private Date birthDate;
    @Column(name = "Code")
    private String code;
    @Column(name = "Score")
    private BigDecimal score;
    @Column(name = "InitScore")
    private BigDecimal initScore;
    @Column(name = "ArchivedId")
    private Integer archivedId;
    @Column(name = "ReferenceId")
    private Integer referenceId;
    @Column(name = "StoreId")
    private Integer storeId;
    @Column(name = "RegionId")
    private Integer regionId;
    @Column(name = "CityId")
    private Integer cityId;
    @Column(name = "WardId")
    private Integer wardId;
    @Column(name = "MasterId")
    private Integer masterId;
    @Column(name = "MetadataHash")
    private Integer metadataHash;
    @Column(name = "PreMetadataHash")
    private Integer preMetadataHash;
    @Column(name = "NationalFacilityCode")
    private String nationalFacilityCode;
    @Column(name = "MappingStoreId")
    private Integer mappingStoreId;
    @Column(name = "TotalScore")
    private BigDecimal totalScore;
    @Column(name = "SexId")
    private Integer sexId;
    @Column(name = "NameContacter")
    private String nameContacter;
    @Column(name = "PhoneContacter")
    private String phoneContacter;
    @Column(name = "RefCus")
    private String refCus;
    @Column(name = "CusType")
    private Boolean cusType;
    @Column(name = "TaxCode")
    private String taxCode;
    @Column(name = "MedicalIdentifier")
    private String medicalIdentifier;
    @Column(name = "CitizenIdentification")
    private String citizenIdentification;
    @Column(name = "HealthInsuranceNumber")
    private String healthInsuranceNumber;
    @Column(name = "Job")
    private String job;
    @Column(name = "AbilityToPay")
    private String abilityToPay;
    @Column(name = "ZaloId")
    private String zaloId;
}

