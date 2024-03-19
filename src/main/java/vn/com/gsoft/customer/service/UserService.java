package vn.com.gsoft.customer.service;

import vn.com.gsoft.customer.model.system.Profile;

import java.util.Optional;

public interface UserService extends BaseService {
    Optional<Profile> findUserByToken(String token);

    Optional<Profile> findUserByUsername(String token);

}
