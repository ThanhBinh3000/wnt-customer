package vn.com.gsoft.customer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.customer.entity.NhaThuocs;
import vn.com.gsoft.customer.entity.NhomKhachHangs;

@Repository
public interface NhaThuocsRepository extends CrudRepository<NhaThuocs, Long> {
    NhaThuocs findByIdAndHoatDong(Long id, Boolean hoatDong);
}
