package library.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class LibraryException extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;
}
