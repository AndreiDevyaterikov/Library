package library.dto;


import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@Builder
public class ResponseDto {
    private Integer httpCode;
    private HttpStatus httpStatus;
    private String message;
    private Date timestamp;
}
