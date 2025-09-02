package com.instar.common.exception.errorcode;
import com.instar.common.exception.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserError implements BaseErrorCode {
    USER_NOT_FOUND(404, "USER_NOT_FOUND", "Không tìm thấy user với id = %s"),
    USER_ALREADY_EXISTS(400, "USER_ALREADY_EXISTS", "User đã tồn tại");

    private final int status;
    private final String code;
    private final String message;

    @Override
    public String formatMessage(Object... args) {
        return String.format(message, args);
    }
}
