package vn.com.gsoft.customer.exception;

import java.io.IOException;

public class RestTemplateException extends IOException {
    public RestTemplateException(String message) {
        super(message);
    }
}
