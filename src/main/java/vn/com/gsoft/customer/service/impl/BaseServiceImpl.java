package vn.com.gsoft.customer.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.com.gsoft.customer.config.MessageTemplate;
import vn.com.gsoft.customer.model.system.Profile;
import vn.com.gsoft.customer.service.BaseService;

@Service
@Slf4j
public class BaseServiceImpl implements BaseService {
    @Autowired
    private MessageTemplate messageTemplate;

    @Override
    public Profile getLoggedUser() throws Exception {
        try {
            return (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception ex) {
            throw new Exception(messageTemplate.message("error.token.invalid"));
        }
    }
}
