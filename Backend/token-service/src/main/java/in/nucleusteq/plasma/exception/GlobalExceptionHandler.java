package in.nucleusteq.plasma.exception;

import in.nucleusteq.plasma.dto.ErrorResponce;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RequestTimeOutException.class)
    public ResponseEntity<ErrorResponce> resourceNotFoundExceptionHandler(
            final RequestTimeOutException requestTimeOutException) {
        String message = requestTimeOutException.getMessage();
        ErrorResponce response = ErrorResponce.builder().success(false).message(message).build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
