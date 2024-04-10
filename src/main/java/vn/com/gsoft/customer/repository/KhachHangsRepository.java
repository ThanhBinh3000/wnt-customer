package vn.com.gsoft.customer.repository;

import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.gsoft.customer.entity.KhachHangs;
import vn.com.gsoft.customer.entity.NhomKhachHangs;
import vn.com.gsoft.customer.model.dto.KhachHangsReq;

import java.util.List;
import java.util.Optional;

@Repository
public interface KhachHangsRepository extends BaseRepository<KhachHangs, KhachHangsReq, Long> {

  @Query("SELECT c FROM KhachHangs c " +
         "WHERE 1=1 "
//          + " AND (:#{#param.maKhachHang} IS NULL OR c.maKhachHang = :#{#param.maKhachHang}) "
          + " AND (:#{#param.tenKhachHang} IS NULL OR lower(c.tenKhachHang) LIKE lower(concat('%',CONCAT(:#{#param.tenKhachHang},'%'))))"
          + " AND (:#{#param.diaChi} IS NULL OR lower(c.diaChi) LIKE lower(concat('%',CONCAT(:#{#param.diaChi},'%'))))"
          + " AND (:#{#param.soDienThoai} IS NULL OR lower(c.soDienThoai) LIKE lower(concat('%',CONCAT(:#{#param.soDienThoai},'%'))))"
          + " AND (:#{#param.noDauKy} IS NULL OR c.noDauKy = :#{#param.noDauKy}) "
          + " AND (:#{#param.donViCongTac} IS NULL OR lower(c.donViCongTac) LIKE lower(concat('%',CONCAT(:#{#param.donViCongTac},'%'))))"
          + " AND (:#{#param.email} IS NULL OR lower(c.email) LIKE lower(concat('%',CONCAT(:#{#param.email},'%'))))"
          + " AND (:#{#param.ghiChu} IS NULL OR lower(c.ghiChu) LIKE lower(concat('%',CONCAT(:#{#param.ghiChu},'%'))))"
          + " AND (:#{#param.maNhaThuoc} IS NULL OR lower(c.maNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.maNhaThuoc},'%'))))"
          + " AND (:#{#param.maNhomKhachHang} IS NULL OR c.maNhomKhachHang = :#{#param.maNhomKhachHang}) "
//          + " AND (:#{#param.created} IS NULL OR c.created >= :#{#param.createdFrom}) "
//          + " AND (:#{#param.created} IS NULL OR c.created <= :#{#param.createdTo}) "
//          + " AND (:#{#param.modified} IS NULL OR c.modified >= :#{#param.modifiedFrom}) "
//          + " AND (:#{#param.modified} IS NULL OR c.modified <= :#{#param.modifiedTo}) "
          + " AND (:#{#param.createdByUserId} IS NULL OR c.createdByUserId = :#{#param.createdByUserId}) "
          + " AND (:#{#param.modifiedByUserId} IS NULL OR c.modifiedByUserId = :#{#param.modifiedByUserId}) "
          + " AND (:#{#param.customerTypeId} IS NULL OR c.customerTypeId = :#{#param.customerTypeId}) "
          + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId}) "
          + " AND (:#{#param.barCode} IS NULL OR lower(c.barCode) LIKE lower(concat('%',CONCAT(:#{#param.barCode},'%'))))"
//          + " AND (:#{#param.birthDate} IS NULL OR c.birthDate >= :#{#param.birthDateFrom}) "
//          + " AND (:#{#param.birthDate} IS NULL OR c.birthDate <= :#{#param.birthDateTo}) "
          + " AND (:#{#param.code} IS NULL OR lower(c.code) LIKE lower(concat('%',CONCAT(:#{#param.code},'%'))))"
          + " AND (:#{#param.score} IS NULL OR c.score = :#{#param.score}) "
          + " AND (:#{#param.initScore} IS NULL OR c.initScore = :#{#param.initScore}) "
          + " AND (:#{#param.archivedId} IS NULL OR c.archivedId = :#{#param.archivedId}) "
          + " AND (:#{#param.referenceId} IS NULL OR c.referenceId = :#{#param.referenceId}) "
          + " AND (:#{#param.storeId} IS NULL OR c.storeId = :#{#param.storeId}) "
          + " AND (:#{#param.regionId} IS NULL OR c.regionId = :#{#param.regionId}) "
          + " AND (:#{#param.cityId} IS NULL OR c.cityId = :#{#param.cityId}) "
          + " AND (:#{#param.wardId} IS NULL OR c.wardId = :#{#param.wardId}) "
          + " AND (:#{#param.masterId} IS NULL OR c.masterId = :#{#param.masterId}) "
          + " AND (:#{#param.metadataHash} IS NULL OR c.metadataHash = :#{#param.metadataHash}) "
          + " AND (:#{#param.preMetadataHash} IS NULL OR c.preMetadataHash = :#{#param.preMetadataHash}) "
          + " AND (:#{#param.nationalFacilityCode} IS NULL OR lower(c.nationalFacilityCode) LIKE lower(concat('%',CONCAT(:#{#param.nationalFacilityCode},'%'))))"
          + " AND (:#{#param.mappingStoreId} IS NULL OR c.mappingStoreId = :#{#param.mappingStoreId}) "
          + " AND (:#{#param.totalScore} IS NULL OR c.totalScore = :#{#param.totalScore}) "
          + " AND (:#{#param.sexId} IS NULL OR c.sexId = :#{#param.sexId}) "
          + " AND (:#{#param.nameContacter} IS NULL OR lower(c.nameContacter) LIKE lower(concat('%',CONCAT(:#{#param.nameContacter},'%'))))"
          + " AND (:#{#param.phoneContacter} IS NULL OR lower(c.phoneContacter) LIKE lower(concat('%',CONCAT(:#{#param.phoneContacter},'%'))))"
          + " AND (:#{#param.refCus} IS NULL OR lower(c.refCus) LIKE lower(concat('%',CONCAT(:#{#param.refCus},'%'))))"
          + " AND (:#{#param.taxCode} IS NULL OR lower(c.taxCode) LIKE lower(concat('%',CONCAT(:#{#param.taxCode},'%'))))"
          + " AND (:#{#param.medicalIdentifier} IS NULL OR lower(c.medicalIdentifier) LIKE lower(concat('%',CONCAT(:#{#param.medicalIdentifier},'%'))))"
          + " AND (:#{#param.citizenIdentification} IS NULL OR lower(c.citizenIdentification) LIKE lower(concat('%',CONCAT(:#{#param.citizenIdentification},'%'))))"
          + " AND (:#{#param.healthInsuranceNumber} IS NULL OR lower(c.healthInsuranceNumber) LIKE lower(concat('%',CONCAT(:#{#param.healthInsuranceNumber},'%'))))"
          + " AND (:#{#param.job} IS NULL OR lower(c.job) LIKE lower(concat('%',CONCAT(:#{#param.job},'%'))))"
          + " AND (:#{#param.abilityToPay} IS NULL OR lower(c.abilityToPay) LIKE lower(concat('%',CONCAT(:#{#param.abilityToPay},'%'))))"
          + " AND (:#{#param.zaloId} IS NULL OR lower(c.zaloId) LIKE lower(concat('%',CONCAT(:#{#param.zaloId},'%'))))"
          + " ORDER BY c.tenKhachHang desc"
  )
  Page<KhachHangs> searchPage(@Param("param") KhachHangsReq param, Pageable pageable);


  
  @Query("SELECT c FROM KhachHangs c " +
         "WHERE 1=1 "
//          + " AND (:#{#param.maKhachHang} IS NULL OR c.maKhachHang = :#{#param.maKhachHang}) "
          + " AND (:#{#param.tenKhachHang} IS NULL OR lower(c.tenKhachHang) LIKE lower(concat('%',CONCAT(:#{#param.tenKhachHang},'%'))))"
          + " AND (:#{#param.diaChi} IS NULL OR lower(c.diaChi) LIKE lower(concat('%',CONCAT(:#{#param.diaChi},'%'))))"
          + " AND (:#{#param.soDienThoai} IS NULL OR lower(c.soDienThoai) LIKE lower(concat('%',CONCAT(:#{#param.soDienThoai},'%'))))"
          + " AND (:#{#param.noDauKy} IS NULL OR c.noDauKy = :#{#param.noDauKy}) "
          + " AND (:#{#param.donViCongTac} IS NULL OR lower(c.donViCongTac) LIKE lower(concat('%',CONCAT(:#{#param.donViCongTac},'%'))))"
          + " AND (:#{#param.email} IS NULL OR lower(c.email) LIKE lower(concat('%',CONCAT(:#{#param.email},'%'))))"
          + " AND (:#{#param.ghiChu} IS NULL OR lower(c.ghiChu) LIKE lower(concat('%',CONCAT(:#{#param.ghiChu},'%'))))"
          + " AND (:#{#param.maNhaThuoc} IS NULL OR lower(c.maNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.maNhaThuoc},'%'))))"
          + " AND (:#{#param.maNhomKhachHang} IS NULL OR c.maNhomKhachHang = :#{#param.maNhomKhachHang}) "
//          + " AND (:#{#param.created} IS NULL OR c.created >= :#{#param.createdFrom}) "
//          + " AND (:#{#param.created} IS NULL OR c.created <= :#{#param.createdTo}) "
//          + " AND (:#{#param.modified} IS NULL OR c.modified >= :#{#param.modifiedFrom}) "
//          + " AND (:#{#param.modified} IS NULL OR c.modified <= :#{#param.modifiedTo}) "
          + " AND (:#{#param.createdByUserId} IS NULL OR c.createdByUserId = :#{#param.createdByUserId}) "
          + " AND (:#{#param.modifiedByUserId} IS NULL OR c.modifiedByUserId = :#{#param.modifiedByUserId}) "
          + " AND (:#{#param.customerTypeId} IS NULL OR c.customerTypeId = :#{#param.customerTypeId}) "
          + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId}) "
          + " AND (:#{#param.barCode} IS NULL OR lower(c.barCode) LIKE lower(concat('%',CONCAT(:#{#param.barCode},'%'))))"
//          + " AND (:#{#param.birthDate} IS NULL OR c.birthDate >= :#{#param.birthDateFrom}) "
//          + " AND (:#{#param.birthDate} IS NULL OR c.birthDate <= :#{#param.birthDateTo}) "
          + " AND (:#{#param.code} IS NULL OR lower(c.code) LIKE lower(concat('%',CONCAT(:#{#param.code},'%'))))"
          + " AND (:#{#param.score} IS NULL OR c.score = :#{#param.score}) "
          + " AND (:#{#param.initScore} IS NULL OR c.initScore = :#{#param.initScore}) "
          + " AND (:#{#param.archivedId} IS NULL OR c.archivedId = :#{#param.archivedId}) "
          + " AND (:#{#param.referenceId} IS NULL OR c.referenceId = :#{#param.referenceId}) "
          + " AND (:#{#param.storeId} IS NULL OR c.storeId = :#{#param.storeId}) "
          + " AND (:#{#param.regionId} IS NULL OR c.regionId = :#{#param.regionId}) "
          + " AND (:#{#param.cityId} IS NULL OR c.cityId = :#{#param.cityId}) "
          + " AND (:#{#param.wardId} IS NULL OR c.wardId = :#{#param.wardId}) "
          + " AND (:#{#param.masterId} IS NULL OR c.masterId = :#{#param.masterId}) "
          + " AND (:#{#param.metadataHash} IS NULL OR c.metadataHash = :#{#param.metadataHash}) "
          + " AND (:#{#param.preMetadataHash} IS NULL OR c.preMetadataHash = :#{#param.preMetadataHash}) "
          + " AND (:#{#param.nationalFacilityCode} IS NULL OR lower(c.nationalFacilityCode) LIKE lower(concat('%',CONCAT(:#{#param.nationalFacilityCode},'%'))))"
          + " AND (:#{#param.mappingStoreId} IS NULL OR c.mappingStoreId = :#{#param.mappingStoreId}) "
          + " AND (:#{#param.totalScore} IS NULL OR c.totalScore = :#{#param.totalScore}) "
          + " AND (:#{#param.sexId} IS NULL OR c.sexId = :#{#param.sexId}) "
          + " AND (:#{#param.nameContacter} IS NULL OR lower(c.nameContacter) LIKE lower(concat('%',CONCAT(:#{#param.nameContacter},'%'))))"
          + " AND (:#{#param.phoneContacter} IS NULL OR lower(c.phoneContacter) LIKE lower(concat('%',CONCAT(:#{#param.phoneContacter},'%'))))"
          + " AND (:#{#param.refCus} IS NULL OR lower(c.refCus) LIKE lower(concat('%',CONCAT(:#{#param.refCus},'%'))))"
          + " AND (:#{#param.taxCode} IS NULL OR lower(c.taxCode) LIKE lower(concat('%',CONCAT(:#{#param.taxCode},'%'))))"
          + " AND (:#{#param.medicalIdentifier} IS NULL OR lower(c.medicalIdentifier) LIKE lower(concat('%',CONCAT(:#{#param.medicalIdentifier},'%'))))"
          + " AND (:#{#param.citizenIdentification} IS NULL OR lower(c.citizenIdentification) LIKE lower(concat('%',CONCAT(:#{#param.citizenIdentification},'%'))))"
          + " AND (:#{#param.healthInsuranceNumber} IS NULL OR lower(c.healthInsuranceNumber) LIKE lower(concat('%',CONCAT(:#{#param.healthInsuranceNumber},'%'))))"
          + " AND (:#{#param.job} IS NULL OR lower(c.job) LIKE lower(concat('%',CONCAT(:#{#param.job},'%'))))"
          + " AND (:#{#param.abilityToPay} IS NULL OR lower(c.abilityToPay) LIKE lower(concat('%',CONCAT(:#{#param.abilityToPay},'%'))))"
          + " AND (:#{#param.zaloId} IS NULL OR lower(c.zaloId) LIKE lower(concat('%',CONCAT(:#{#param.zaloId},'%'))))"
          + " ORDER BY c.tenKhachHang desc"
  )
  List<KhachHangs> searchList(@Param("param") KhachHangsReq param);
  @Query(value = "SELECT c.id AS id, c.code AS code, c.tenKhachHang AS tenKhachHang,"
          + " c.soDienThoai AS soDienThoai, c.barcode AS barcode, n.tenNhomKhachHang AS tenNhomKhachHang,"
          + " c.MappingStoreId as mappingStoreId, c.zaloId AS zaloId, c.created AS created"
          + " FROM KhachHangs c"
          + " JOIN NhomKhachHangs n ON c.maNhomKhachHang = n.id"
          + " WHERE 1=1"
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.tenKhachHang} IS NULL OR lower(c.tenKhachHang) LIKE lower(concat('%',CONCAT(:#{#param.tenKhachHang},'%'))))"
          + " AND (:#{#param.diaChi} IS NULL OR lower(c.diaChi) LIKE lower(concat('%',CONCAT(:#{#param.diaChi},'%'))))"
          + " AND (:#{#param.soDienThoai} IS NULL OR lower(c.soDienThoai) LIKE lower(concat('%',CONCAT(:#{#param.soDienThoai},'%'))))"
          + " AND (:#{#param.ghiChu} IS NULL OR lower(c.ghiChu) LIKE lower(concat('%',CONCAT(:#{#param.ghiChu},'%'))))"
          + " AND (:#{#param.maNhaThuoc} IS NULL OR c.maNhaThuoc = :#{#param.maNhaThuoc} )"
          + " AND (:#{#param.maNhomKhachHang} IS NULL OR c.maNhomKhachHang = :#{#param.maNhomKhachHang}) "
          + " AND (:#{#param.createdByUserId} IS NULL OR c.createdBy_UserId = :#{#param.createdByUserId}) "
          + " AND (:#{#param.modifiedByUserId} IS NULL OR c.modifiedBy_UserId = :#{#param.modifiedByUserId}) "
          + " AND (:#{#param.customerTypeId} IS NULL OR c.customerTypeId = :#{#param.customerTypeId}) "
          + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId}) "
          + " AND (:#{#param.barCode} IS NULL OR lower(c.barCode) LIKE lower(concat('%',CONCAT(:#{#param.barCode},'%'))))"
          + " AND (:#{#param.code} IS NULL OR lower(c.code) LIKE lower(concat('%',CONCAT(:#{#param.code},'%'))))"
          + " AND (:#{#param.regionId} IS NULL OR c.regionId = :#{#param.regionId})"
          + " AND (:#{#param.cityId} IS NULL OR c.cityId = :#{#param.cityId})"
          + " AND (:#{#param.wardId} IS NULL OR c.wardId = :#{#param.wardId})"
          + " AND (:#{#param.mappingStoreId} IS NULL OR c.mappingStoreId = :#{#param.mappingStoreId})"
          + " AND (:#{#param.zaloId} IS NULL OR lower(c.zaloId) LIKE lower(concat('%',CONCAT(:#{#param.zaloId},'%'))))"
          + " AND (:#{#param.textSearch} IS NULL OR lower(c.tenkhachHang) LIKE lower(concat('%',CONCAT(:#{#param.textSearch},'%'))))"
          + " AND (:#{#param.textSearch} IS NULL OR lower(c.code) LIKE lower(concat('%',CONCAT(:#{#param.textSearch},'%'))))"
          + " AND (:#{#param.textSearch} IS NULL OR lower(c.barcode) LIKE lower(concat('%',CONCAT(:#{#param.textSearch},'%'))))"
          + " AND (:#{#param.textSearch} IS NULL OR lower(c.diaChi) LIKE lower(concat('%',CONCAT(:#{#param.textSearch},'%'))))"
          + " AND (:#{#param.textSearch} IS NULL OR lower(c.soDienThoai) LIKE lower(concat('%',CONCAT(:#{#param.textSearch},'%'))))"
          + " ORDER BY c.created desc",
          nativeQuery = true
  )
  Page<Tuple> searchCustomerManagementPage(@Param("param") KhachHangsReq param, Pageable pageable);
  //tìm khách hàng theo số điện thoại
  @Query(value = "SELECT * FROM KhachHangs c WHERE c.soDienThoai = :phoneNumber AND c.maNhaThuoc = :drugStoreCode", nativeQuery = true)
  List<KhachHangs> findCustomerByPhoneNumber( @Param("phoneNumber") String phoneNumber,@Param("drugStoreCode") String drugStoreCode);
  //tìm khách hàng theo mã khách hàng
  @Query(value = "SELECT * FROM KhachHangs c WHERE c.soDienThoai = :code AND c.maNhaThuoc = :drugStoreCode", nativeQuery = true)
  List<KhachHangs> findCustomerByCode( @Param("code") String code,@Param("drugStoreCode") String drugStoreCode);
  //tìm khách hàng theo barcode
  @Query(value = "SELECT * FROM KhachHangs c WHERE c.soDienThoai = :barcode AND c.maNhaThuoc = :drugStoreCode", nativeQuery = true)
  List<KhachHangs> findCustomerByBarcode( @Param("barcode") String barcode,@Param("drugStoreCode") String drugStoreCode);
  //lấy nhóm khách hàng mặc định
  @Query(value = "SELECT * FROM NhomKhachHangs c WHERE c.groupTypeId = 1 AND c.nhaThuoc_maNhaThuoc = :drugStoreCode", nativeQuery = true)
  List<NhomKhachHangs> findGroupCustomerDefault(@Param("drugStoreCode") String drugStoreCode);
}
