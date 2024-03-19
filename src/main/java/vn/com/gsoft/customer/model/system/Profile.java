package vn.com.gsoft.customer.model.system;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import vn.com.gsoft.customer.entity.Department;
import vn.com.gsoft.customer.entity.Role;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class Profile extends User {
    private Long id;

    private String fullName;

    private Long entityTypeId;

    private Department department;

    private List<Role> roles;

    private List<Department> departments;

    public Profile(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public Profile(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public Profile(Long id, String fullName, Long entityTypeId, Department department, List<Role> roles, List<Department> departments, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.fullName = fullName;
        this.entityTypeId = entityTypeId;
        this.department = department;
        this.roles = roles;
        this.departments = departments;
    }
}
