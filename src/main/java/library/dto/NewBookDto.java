package library.dto;

import lombok.Data;

import java.util.List;

@Data
public class NewBookDto {
    private String title;
    private List<String> authors;
    private Integer count;
}
