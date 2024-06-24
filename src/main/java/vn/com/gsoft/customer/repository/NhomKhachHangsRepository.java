package vn.com.gsoft.customer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.customer.entity.NhomKhachHangs;

import java.util.Optional;

@Repository
public interface NhomKhachHangsRepository extends CrudRepository<NhomKhachHangs, Long> {
    Optional<NhomKhachHangs> findByNhaThuocMaNhaThuocAndTenNhomKhachHang(String maNhaThuoc, String tenNhomKhachHang);
}
