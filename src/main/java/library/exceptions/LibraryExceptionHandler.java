package library.exceptions;

import jakarta.servlet.http.HttpServletResponse;
import library.dto.ResponseDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class LibraryExceptionHandler {

    @ExceptionHandler(LibraryException.class)
    public ResponseDto libraryExceptionHandler(LibraryException exception, HttpServletResponse response) {
        response.setStatus(exception.getHttpStatus().value());
        return ResponseDto.builder()
                .httpCode(exception.getHttpStatus().value())
                .httpStatus(exception.getHttpStatus())
                .message(exception.getMessage())
                .timestamp(new Date())
                .build();
    }
}
