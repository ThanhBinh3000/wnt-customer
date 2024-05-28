package vn.com.gsoft.customer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.customer.entity.NhomKhachHangs;

@Repository
public interface NhomKhachHangsRepository extends CrudRepository<NhomKhachHangs, Long> {
}
