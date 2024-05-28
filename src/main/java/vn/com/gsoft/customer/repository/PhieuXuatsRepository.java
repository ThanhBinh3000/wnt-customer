package vn.com.gsoft.customer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.customer.entity.NhaThuocs;
import vn.com.gsoft.customer.entity.PhieuXuats;

@Repository
public interface PhieuXuatsRepository extends CrudRepository<PhieuXuats, Long> {
    PhieuXuats findByNhaThuocMaNhaThuocAndKhachHangMaKhachHangAndMaLoaiXuatNhapAndRecordStatusId(
            String nhaThuocMaNhaThuoc,
            Long khachHangMaKhachHang,
            Integer maLoaiXuatNhap,
            Integer recordStatusId);
}
