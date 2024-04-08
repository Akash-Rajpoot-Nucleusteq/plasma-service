package in.nucleusteq.plasma.handler;

import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import in.nucleusteq.plasma.exception.DuplicateException;
import in.nucleusteq.plasma.exception.NotFoundException;
import in.nucleusteq.plasma.exception.responce.APIErrorResponse;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import in.nucleusteq.plasma.exception.ResourceNotFoundException;
import in.nucleusteq.plasma.exception.UnauthorizedAccessException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<APIErrorResponse> handleNotFoundException(NotFoundException ex) {
		APIErrorResponse apiError = new APIErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DuplicateException.class)
	public ResponseEntity<APIErrorResponse> handleDuplicateException(DuplicateException ex) {
		APIErrorResponse apiError = new APIErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), ex);
		return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);

	}

	@ExceptionHandler(UnauthorizedAccessException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public final ResponseEntity<APIErrorResponse> handleUnauthorizedAccessException(
			final UnauthorizedAccessException exception) {
		APIErrorResponse errorResponse = new APIErrorResponse(exception.getMessage(),
				exception.getStackTrace().toString());
		return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Handles ResourceNotFoundException by returning a response with a "Not Found"
	 * status.
	 * 
	 * @param exception The ResourceNotFoundException to handle.
	 * @return A ResponseEntity with a "Not Found" status and an error message.
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public final ResponseEntity<APIErrorResponse> handleResourceNotFound(final ResourceNotFoundException exception) {
		APIErrorResponse errorResponse = new APIErrorResponse("Not Found", exception.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(SpelEvaluationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public final ResponseEntity<APIErrorResponse> spelEvaluationException(final SpelEvaluationException exception) {
		APIErrorResponse errorResponse = new APIErrorResponse("Access denied",
				"Insufficient privilage to access this resource");
		return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(MalformedJwtException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public final ResponseEntity<APIErrorResponse> malformedJwtExceptionException(
			final MalformedJwtException exception) {
		APIErrorResponse errorResponse = new APIErrorResponse("Access denied", "Tempered token");
		return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(ExpiredJwtException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public final ResponseEntity<APIErrorResponse> expiredJwtException(final ExpiredJwtException exception) {
		APIErrorResponse errorResponse = new APIErrorResponse("Access denied", "token is expired.");
		return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
	}

//	@ExceptionHandler(AccessDeniedException.class)
//	public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
//		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to access this resource.");
//	}
	@ExceptionHandler(AccessDeniedException.class)
	public final ResponseEntity<Object> handleAccessDeniedException(final AccessDeniedException exception) {
		APIErrorResponse errorResponse = new APIErrorResponse("Access denied",
				"Insufficient privilage to access this resource");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
	}
	
//	@ExceptionHandler(AccessDeniedException.class)
//	public ResponseEntity<APIErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
//		APIErrorResponse errorResponse = new APIErrorResponse("Forbidden", "You do not have permission to access this resource.");
//	    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
//	}

}
