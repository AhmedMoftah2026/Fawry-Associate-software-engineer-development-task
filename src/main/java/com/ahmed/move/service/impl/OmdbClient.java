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
