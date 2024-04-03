package vn.com.gsoft.customer.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.gsoft.customer.entity.BaseEntity;
import vn.com.gsoft.customer.model.system.BaseRequest;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class KhachHangsReq extends BaseRequest {

    private Long id;
    private String tenKhachHang;
    private String diaChi;
    private String soDienThoai;
    private BigDecimal noDauKy;
    private String donViCongTac;
    private String email;
    private String ghiChu;
    private String maNhaThuoc;
    private Integer maNhomKhachHang;
    private Date created;
    private Date modified;
    private Integer createdByUserId;
    private Integer modifiedByUserId;
    private Boolean active;
    private Integer customerTypeId;
    private String barCode;
    private Date birthDate;
    private String code;
    private BigDecimal score;
    private BigDecimal initScore;
    private Integer archivedId;
    private Integer referenceId;
    private Integer storeId;
    private Integer regionId;
    private Integer cityId;
    private Integer wardId;
    private Integer masterId;
    private Integer metadataHash;
    private Integer preMetadataHash;
    private String nationalFacilityCode;
    private Integer mappingStoreId;
    private BigDecimal totalScore;
    private Integer sexId;
    private String nameContacter;
    private String phoneContacter;
    private String refCus;
    private Boolean cusType;
    private String taxCode;
    private String medicalIdentifier;
    private String citizenIdentification;
    private String healthInsuranceNumber;
    private String job;
    private String abilityToPay;
    private String zaloId;
}

