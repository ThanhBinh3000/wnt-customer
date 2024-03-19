package vn.com.gsoft.customer.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.com.gsoft.customer.constant.CachingConstant;
import vn.com.gsoft.customer.constant.UserStatus;
import vn.com.gsoft.customer.entity.Department;
import vn.com.gsoft.customer.entity.User;
import vn.com.gsoft.customer.model.system.Profile;
import vn.com.gsoft.customer.repository.DepartmentRepository;
import vn.com.gsoft.customer.repository.RoleRepository;
import vn.com.gsoft.customer.repository.UserRepository;
import vn.com.gsoft.customer.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserServiceImpl extends BaseServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Cacheable(value = CachingConstant.USER)
    public Optional<Profile> findUserByToken(String token) {
        log.warn("Cache findUserByToken missing: user");
        return Optional.empty();
    }

    @Override
    public Optional<Profile> findUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new BadCredentialsException("Không tìm thấy username!");
        }
        Long entityTypeId = user.get().getEntityTypeId();
        Set<SimpleGrantedAuthority> privileges = new HashSet<>();
        List<Department> departments = departmentRepository.findByUserId(user.get().getId());
        return Optional.of(new Profile(
                user.get().getId(),
                user.get().getFullName(),
                entityTypeId,
                null,
                null,
                departments,
                user.get().getUsername(),
                user.get().getPassword(),
                user.get().getStatus() == UserStatus.ACTIVE,
                true,
                true,
                true,
                privileges
        ));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
