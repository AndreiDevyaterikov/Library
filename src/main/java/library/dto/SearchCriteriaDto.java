package library.dto;

import library.enums.Operations;
import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class SearchCriteriaDto {
    private String field;
    private String operation;
    private String value;
    private Sort.Direction direction;
}
