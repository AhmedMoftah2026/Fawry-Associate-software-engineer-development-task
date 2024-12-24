



                                               First, note that 
                                               Run the app docu on swagger by this link {http://localhost:8080/swagger-ui/index.html?continu}
                                               /*
                                                * We have two users
                                                * admin with password 1111
                                                * user with password 2222
                                                * */












Project Structure Overview
backend/
├── config/                          # Configuration files for application setup
│   └── AppConfig.java               # General application configuration
├── controller/                      # Controllers to handle incoming HTTP requests
│   └── MovieController.java         # Handles movie-related API requests
├── dto/                             # Data Transfer Objects (DTOs)
│   ├── movie/                       # Movie-related DTOs
│   │   ├── MovieRequestDTO.java     # DTO for receiving movie data
│   │   ├── MovieResponseDTO.java    # DTO for responding with movie data
│   └── OMDB_MovieResponseDto.java   # DTO for parsing responses from OMDB API
│   └── GeneralResponseDto.java      # General response DTO for all API responses
├── enumeration/                     # Enum definitions
│   └── Role.java                    # Enum for different user roles (Admin, User)
├── mapper/                          # Mappers to convert between DTOs and entities
│   └── MovieMapper.java             # Maps Movie entities to DTOs and vice versa
├── model/                           # Domain models/entities for the database
│   ├── Movie.java                   # Movie entity model
│   └── User.java                    # User entity model
├── repository/                      # Repositories for accessing the database
│   ├── MovieRepository.java         # Movie repository for CRUD operations on Movie entity
│   └── UserRepository.java          # User repository for CRUD operations on User entity
├── security/                        # Security-related components
│   ├── CustomUserDetails.java       # Custom user details class for authentication
│   ├── CustomUserDetailsService.java# Service for loading user details during authentication
│   └── SecurityConfig.java         # Security configuration class
├── service/                         # Service layer to handle business logic
│   ├── impl/                        # Implementation classes for services
│   │   └── MovieServiceImpl.java    # Implements the logic for movie-related actions
│   └── OmdbClient.java              # Service for interacting with OMDB API
│   └── IMovieService.java           # Interface for movie-related services
├── MovieManagementAppApplication.java  # Main class to run the Spring Boot application
└── pom.xml                          # Maven build configuration file
Directory and File Descriptions
config/

AppConfig.java: This file contains the general application configuration, like beans setup or any required global configurations for the application.
controller/

MovieController.java: Handles HTTP requests related to movie operations (e.g., adding, deleting, or fetching movies).
dto/

MovieRequestDTO.java: Defines the structure for the request body when a new movie is added.
MovieResponseDTO.java: Defines the structure for the response when returning movie data.
OMDB_MovieResponseDto.java: Maps the response from the OMDB API to a usable structure.
GeneralResponseDto.java: A generic DTO used for sending responses from the server.
enumeration/

Role.java: An enum that defines user roles (Admin, User). It is used for role-based access control.
mapper/

MovieMapper.java: Maps between Movie entities (models) and their corresponding DTOs. This helps convert data between layers.
model/

Movie.java: The Movie entity model that maps to the movie table in the database.
User.java: The User entity model that maps to the user table in the database, representing the users of the system.
repository/

MovieRepository.java: Interface that extends JpaRepository, providing CRUD operations for the Movie entity.
UserRepository.java: Interface that extends JpaRepository, providing CRUD operations for the User entity.
security/

CustomUserDetails.java: Custom implementation of UserDetails for Spring Security, used for storing and accessing user-specific details.
CustomUserDetailsService.java: Service class responsible for loading user details during authentication.
SecurityConfig.java: Configures Spring Security settings, including authentication, authorization, and role-based access control.
service/

MovieServiceImpl.java: Implements the IMovieService interface and contains the business logic for movie-related operations.
OmdbClient.java: Service responsible for interacting with the OMDB API to fetch movie data.
IMovieService.java: Interface that defines the contract for movie services, ensuring consistency in the logic layer.
MovieManagementAppApplication.java: The main entry point for the Spring Boot application, annotated with @SpringBootApplication to bootstrap the application.
Through this file, the application is run in addition to the ready-made documentation for the API using Swagger.
############################################################################################
###################################################################
# Movie Management Backend

This is the backend service for the Movie Management Application. It is built using Spring Boot and provides a REST API for managing movies. The backend integrates with the OMDB API to fetch movie data and stores the movies in a MySQL database.

---

## **Features**
- Fetch movie data from the OMDB API and store it in a MySQL database.
- Admin functionality to add or remove movies from the database.
- Provide movie details through REST API endpoints for both admin and regular users.
- Basic authentication for admin users.
- Regular users can view movie details and search movies.
--------------------------------------------------------------------------------------
package com.ahmed.move.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

#####################
package com.ahmed.move.service.impl;

import com.ahmed.move.dto.movie.OMDB_MovieResponseDto;
import com.ahmed.move.mapper.MovieMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Data
@Service
public class OmdbClient {

    private static final String OMDB_API_URL = "http://www.omdbapi.com/";
    private static final String API_KEY = "7dd49044";

    private final RestTemplate restTemplate;
    private final MovieMapper movieMapper;

    @Autowired
    public OmdbClient(RestTemplate restTemplate, MovieMapper movieMapper) {
        this.restTemplate = restTemplate;
        this.movieMapper = movieMapper;
    }

    public OMDB_MovieResponseDto getMovieDetails(String title) {
        String url = String.format("%s?t=%s&apikey=%s", OMDB_API_URL, title, API_KEY);
       return restTemplate.getForObject(url, OMDB_MovieResponseDto.class);

    }


}

-------------------------------------------------------------------------------------------------------------

## **Technologies Used**
- **Framework**: Spring Boot
- **Programming Language**: Java 8+
- **Database**: MySQL
- **External API**: OMDB API (https://www.omdbapi.com/)
- **Authentication**: Basic Authentication (can be expanded to JWT or OAuth if needed)

-----------------------------------------------------------
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
    @Operation(summary = "Search for specific movie in OMDB server And Add to Local-DB[Amin]")
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


    @PostMapping("/addoutsideOMDB")
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


--------------------------------------------------------------------------------

## **Setup Instructions**

### **1. Prerequisites**
Make sure the following tools are installed on your local machine:
- Java 8+ (for the backend)
- Maven (for building and running the backend)
- MySQL (for the database)

-----------------------------------------------------------------------

---------------------------------------------------------------------------------
