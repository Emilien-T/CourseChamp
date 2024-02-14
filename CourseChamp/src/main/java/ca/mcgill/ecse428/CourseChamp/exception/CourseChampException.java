package ca.mcgill.ecse428.CourseChamp.exception;
import org.springframework.http.HttpStatus;

public class CourseChampException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private HttpStatus status;

    public CourseChampException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
