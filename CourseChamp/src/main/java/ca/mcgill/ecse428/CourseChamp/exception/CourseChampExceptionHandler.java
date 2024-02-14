package ca.mcgill.ecse428.CourseChamp.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CourseChampExceptionHandler {
    @ExceptionHandler(CourseChampException.class)
    public ResponseEntity<String> handleCourseChampException(CourseChampException e) {
        return new ResponseEntity<String>(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException e) {
        String message = "";
        for (FieldError fe : e.getBindingResult().getFieldErrors()) {
            message += fe.getDefaultMessage() + "\n";
        }
        return new ResponseEntity<String>(message, e.getStatusCode());
    }

    // @ExceptionHandler(PersistenceException.class)
    // public ResponseEntity<String> handlePersistenceException(PersistenceException e) {
    //     return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    // }
}
