package library.dto;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class OrderDto {
    private Sort.Direction direction;
    private String field;
}
