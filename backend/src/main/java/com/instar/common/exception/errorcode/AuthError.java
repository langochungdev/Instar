package com.instar.common.exception.errorcode;
import com.instar.common.exception.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthError implements BaseErrorCode {
    MISSING_TOKEN(401, "MISSING_TOKEN", "Cookie không có token"),
    INVALID_TOKEN(401, "INVALID_TOKEN", "Token sai hoặc đã hết hạn"),
    BLACKLISTED_TOKEN(401, "BLACKLISTED_TOKEN", "Token đã bị vô hiệu hóa (blacklist)"),
    USER_NOT_FOUND(404, "USER_NOT_FOUND", "User không tồn tại"),
    PASSWORD_NOT_MATCH(401, "PASSWORD_NOT_MATCH", "Sai Mật Khẩu");

    private final int status;
    private final String code;
    private final String message;

    @Override
    public String formatMessage(Object... args) {
        return String.format(message, args);
    }
}
