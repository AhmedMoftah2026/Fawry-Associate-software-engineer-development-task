package com.ahmed.move.service.impl;

// Service for Movie Management

import com.ahmed.move.dto.movie.MovieRequestDTO;
import com.ahmed.move.dto.movie.MovieResponseDTO;
import com.ahmed.move.dto.movie.OMDB_MovieResponseDto;
import com.ahmed.move.error.exceptions.AbstractMovieException;
import com.ahmed.move.mapper.MovieMapper;
import com.ahmed.move.model.Movie;
import com.ahmed.move.repository.MovieRepository;
import com.ahmed.move.service.IMovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MovieServiceImpl implements IMovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieMapper movieMapper;

    @Override
    public MovieResponseDTO save(MovieRequestDTO requestDTO) {
        log.info("Attempting to save movie: {}", requestDTO);
        try {
            Movie movie = movieMapper.toEntity(requestDTO);
            movie = movieRepository.save(movie);
            log.info("Successfully saved movie with ID: {}", movie.getId());
            return movieMapper.toResponseDTO(movie);
        } catch (Exception e) {
            log.error("Error occurred while saving movie: {}", e.getMessage(), e);
            throw new IllegalArgumentException("Unable to save movie: " + e.getMessage(), e);
        }
    }

    @Override
    public MovieResponseDTO saveFromOmdb(OMDB_MovieResponseDto movieDetails) {
        log.info("Attempting to save movie from OMDB details: {}", movieDetails);
        try {
            Movie movie = new Movie(null,
                    movieDetails.getTitle(),
                    movieDetails.getYear(),
                    movieDetails.getGenre(),
                    movieDetails.getDirector(),
                    movieDetails.getActors(),
                    movieDetails.getPlot(),
                    movieDetails.getPoster(),
                    movieDetails.getImdbRating());
            movie = movieRepository.save(movie);
            log.info("Successfully saved movie from OMDB with ID: {}", movie.getId());
            return movieMapper.toResponseDTO(movie);
        } catch (Exception e) {
            log.error("Error occurred while saving movie from OMDB: {}", e.getMessage(), e);
            throw new IllegalArgumentException("Unable to save movie from OMDB: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id) {
        log.info("Attempting to delete movie with ID: {}", id);
        try {
            movieRepository.deleteById(id);
            log.info("Successfully deleted movie with ID: {}", id);
        } catch (Exception e) {
            log.error("Error occurred while deleting movie with ID {}: {}", id, e.getMessage(), e);
            throw new IllegalArgumentException("Unable to delete movie: " + e.getMessage(), e);
        }
    }

    @Override
    public List<MovieResponseDTO> findAll() {
        log.info("Fetching all movies from the database");
        try {
            List<MovieResponseDTO> movies =  movieMapper.toResponseDTOList( movieRepository.findAll());
            log.info("Successfully fetched {} movies", movies.size());
            return movies;
        } catch (Exception e) {
            log.error("Error occurred while fetching movies: {}", e.getMessage(), e);
            throw new IllegalArgumentException("Unable to fetch movies: " + e.getMessage(), e);
        }
    }

    @Override
    public MovieResponseDTO findByTitle(String title) {
        log.info("Attempting to find movie with Title: {}", title);
        try {
            Movie movie = movieRepository.findByTitle( title);
            if (movie!=null)
            return movieMapper.toResponseDTO(movie);
            throw new AbstractMovieException(" movie with Title: " + title+ " not found");
        } catch (Exception e) {
            log.error("Error occurred while fetching movie with Title {}: {}", title, e.getMessage(), e);
            throw new AbstractMovieException(e.getMessage());
//            throw new IllegalArgumentException("Unable to find movie: " + e.getMessage(), e);
        }
    }
}
// if (customerOpt.isPresent())
//        return customerMapper.toDto(customerOpt.get());
//        throw new AbstractTrustException(messagesUtil.getMessage("not.found"));
//        } catch (Exception e) {
//        throw new AbstractTrustException(e.getMessage());
//        }
