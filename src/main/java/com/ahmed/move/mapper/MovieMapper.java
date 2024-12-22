package com.ahmed.move.mapper;

import com.ahmed.move.dto.movie.MovieRequestDTO;
import com.ahmed.move.dto.movie.MovieResponseDTO;
import com.ahmed.move.model.Movie;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    Movie toEntity (MovieRequestDTO movieRequestDTO);

    MovieResponseDTO toResponseDTO(Movie movie);

    List<MovieResponseDTO> toResponseDTOList(List<Movie> movie);

}
