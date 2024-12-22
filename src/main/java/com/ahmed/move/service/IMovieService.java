package com.ahmed.move.service;

import com.ahmed.move.dto.movie.MovieRequestDTO;
import com.ahmed.move.dto.movie.MovieResponseDTO;
import com.ahmed.move.dto.movie.OMDB_MovieResponseDto;

import java.util.List;

public interface IMovieService {


    MovieResponseDTO save(MovieRequestDTO requestDTO);

    void delete(Long id);

    List<MovieResponseDTO> findAll();

    MovieResponseDTO findByTitle(String title);

    MovieResponseDTO saveFromOmdb(OMDB_MovieResponseDto movieDetails);
}
