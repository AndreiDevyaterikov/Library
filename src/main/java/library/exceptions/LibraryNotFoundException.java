package library.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LibraryNotFoundException extends RuntimeException {
    private String message;
}
