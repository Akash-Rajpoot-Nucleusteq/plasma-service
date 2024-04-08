package in.nucleusteq.plasma.exception.responce;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class APIErrorResponse {
    private HttpStatus status;
    private String timestamp;
    private String message;
    private String debugMessage;
    private int statusNumber;

    public APIErrorResponse(String message, String details) {
        this.timestamp = LocalDateTime.now().toString();
        this.message = message;
        
    }

    public APIErrorResponse(HttpStatus status) {
        this.status = status;
    }

    public APIErrorResponse(HttpStatus status, Throwable ex) {
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public APIErrorResponse(HttpStatus status, String message, Throwable ex) {
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }
}
