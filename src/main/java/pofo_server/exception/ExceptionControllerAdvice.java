package pofo_server.exception;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleUserException(ApiException e) {
        ErrorStatus error = e.getErrorStatus();
        return ResponseEntity.status(error.getStatus())
                .body(ErrorResponse.builder()
                        .statusCode(error.getStatus().value())
                        .message(error.getMessage()).build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorStatus error = ErrorStatus.valueOf("BAD_REQUEST");
        return ResponseEntity.status(error.getStatus())
                .body(ErrorResponse.builder()
                        .statusCode(error.getStatus().value())
                        .message(error.getMessage()).build());
    }
}
