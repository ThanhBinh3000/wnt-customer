package vn.com.gsoft.customer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.customer.entity.Role;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    List<Role> findByUserId(Long id);

    List<Role> findByUserIdAndDepartmentId(Long userId, Long departmentId);
}
