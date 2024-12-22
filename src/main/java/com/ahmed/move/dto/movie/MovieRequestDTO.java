package com.ahmed.move.dto.movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieRequestDTO {
    private String title;
    private String year;
    private String genre;
    private String director;
    private String actors;
    private String plot;
    private String poster;
}
