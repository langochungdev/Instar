package com.instar.common.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoPermissionException extends ResponseStatusException {
    public NoPermissionException() {
        super(HttpStatus.FORBIDDEN, "Không có quyền thực hiện thao tác này");
    }
    public NoPermissionException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
