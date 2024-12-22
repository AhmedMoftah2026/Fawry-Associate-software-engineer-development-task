package com.ahmed.move.controller;

import com.ahmed.move.dto.movie.OMDB_MovieResponseDto;
import com.ahmed.move.dto.GeneralResponseDto;
import com.ahmed.move.dto.movie.MovieRequestDTO;
import com.ahmed.move.dto.movie.MovieResponseDTO;
import com.ahmed.move.service.IMovieService;
import com.ahmed.move.service.impl.OmdbClient;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private OmdbClient omdbClient;

    @Autowired
    private IMovieService movieService;

    @GetMapping ("/search-in-OMDB")
    @Operation(summary = "Search for specific movie in OMDB server [Amin]")
    public ResponseEntity<GeneralResponseDto> search (@RequestParam String title) {
        OMDB_MovieResponseDto movieDetails = omdbClient.getMovieDetails(title);
        return ResponseEntity.ok(GeneralResponseDto.builder()
                .time(LocalDateTime.now())
                .data(Map.of(
                        "message" , "Movie fetched successfully!",
                        "data" , movieDetails
                ))
                .build());

    }

    @PostMapping ("/search-in-OMDB-And-AddtoDB")
    @Operation(summary = "Search for specific movie in OMDB server And Add to DB[Amin]")
    public ResponseEntity<GeneralResponseDto> searchAndAddMovie(@RequestParam String title) {
        OMDB_MovieResponseDto movieDetails = omdbClient.getMovieDetails(title);
        MovieResponseDTO response = movieService.saveFromOmdb(movieDetails);
        return ResponseEntity.ok(GeneralResponseDto.builder()
                .time(LocalDateTime.now())
                .data(Map.of(
                        "message" , "Movie added successfully!",
                        "data" , response
                ))
                .build());
    }


    @PostMapping("/add")
    @Operation(summary = "Add a new movie to app database Manually [Amin]")
    public ResponseEntity<GeneralResponseDto> addMovie(@RequestBody MovieRequestDTO movieRequestDTO) {
        MovieResponseDTO response = movieService.save(movieRequestDTO);
        return ResponseEntity.ok(GeneralResponseDto.builder()
                .time(LocalDateTime.now())
                .data(Map.of(
                        "message" , "Movie added successfully!",
                        "data" , response
                ))
                .build());

    }


    @DeleteMapping("/delete/{id}")
    @Operation(summary = "delete an existing movie from app database [Amin]")

    public  ResponseEntity<GeneralResponseDto> deleteMovie(@PathVariable Long id) {
        movieService.delete(id);
        return ResponseEntity.ok(GeneralResponseDto.builder()
                .time(LocalDateTime.now())
                .data(Map.of(
                        "message" , "Movie deleted successfully!",
                        "data" , true
                ))
                .build());
    }


    @GetMapping("/fetch-all")
    @Operation(summary = "Fetch all movies [id , title , poster] from app database [User]")
    public ResponseEntity<GeneralResponseDto> getAllMovies() {
        return ResponseEntity.ok(GeneralResponseDto.builder()
                .time(LocalDateTime.now())
                .data(Map.of(
                        "message" , "Movie list fetched successfully!",
                        "data" , movieService.findAll()
                ))
                .build());

    }

    @GetMapping("/fetchbytitle")
    @Operation(summary = "Fetch Movie details by Title from db  [User]")
    public ResponseEntity<GeneralResponseDto> getbytitlefromdb(@RequestParam String title) {
        return ResponseEntity.ok(GeneralResponseDto.builder()
                .time(LocalDateTime.now())
                .data(Map.of(
                        "message" , "Movie list fetched successfully!",
                        "data" , movieService.findByTitle(title)
                ))
                .build());

    }
}

