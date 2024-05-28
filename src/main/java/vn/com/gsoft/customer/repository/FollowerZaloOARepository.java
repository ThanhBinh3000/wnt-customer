package vn.com.gsoft.customer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.customer.entity.FollowerZaloOA;
import vn.com.gsoft.customer.entity.NhomKhachHangs;

@Repository
public interface FollowerZaloOARepository extends CrudRepository<FollowerZaloOA, Long> {
    FollowerZaloOA findByUserId(String userId);
}
