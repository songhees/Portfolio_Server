package pofo_server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001","사용자를 찾을 수 없습니다."),
    FRIENDSHIP_NOT_FOUND(HttpStatus.NOT_FOUND, "P001","글을 찾을 수 없습니다."),
    TO_MANY_REQUEST(HttpStatus.TOO_MANY_REQUESTS, "P005", "요청이 너무 많습니다. 잠시 후 다시 시도해 주세요."),
    FRIEND_LIMIT_EXCEEDED(HttpStatus.CONFLICT, "P006",  "친구 수 한도를 초과했습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "E001",  "요청 값이 유효하지 않습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
