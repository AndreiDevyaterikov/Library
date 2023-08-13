package library.dto;

import lombok.Data;

import java.util.List;

@Data
public class NewBookDto {
    private String title;
    private Integer count;
    private List<String> authors;
    private List<String> genres;
}
