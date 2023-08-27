package library.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LibraryConflictException extends RuntimeException {
    private String message;
}
