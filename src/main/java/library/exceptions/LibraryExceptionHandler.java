package library.exceptions;

import jakarta.servlet.http.HttpServletResponse;
import library.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@Slf4j
@RestControllerAdvice
public class LibraryExceptionHandler {

    @ExceptionHandler(LibraryConflictException.class)
    public ResponseDto libraryConflictExceptionHandler(
            LibraryConflictException exception,
            HttpServletResponse response
    ) {
        response.setStatus(HttpStatus.CONFLICT.value());
        return ResponseDto.builder()
                .httpCode(HttpStatus.CONFLICT.value())
                .httpStatus(HttpStatus.CONFLICT)
                .message(exception.getMessage())
                .timestamp(new Date())
                .build();
    }

    @ExceptionHandler(LibraryNotFoundException.class)
    public ResponseDto libraryNotFoundExceptionHandler(
            LibraryNotFoundException exception,
            HttpServletResponse response
    ) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return ResponseDto.builder()
                .httpCode(HttpStatus.NOT_FOUND.value())
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .timestamp(new Date())
                .build();
    }
}
