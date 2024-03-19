package vn.com.gsoft.customer.service;

import vn.com.gsoft.customer.model.system.Profile;

public interface BaseService {
    Profile getLoggedUser() throws Exception;

}
