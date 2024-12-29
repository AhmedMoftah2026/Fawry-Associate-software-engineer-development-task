package com.ahmed.move.repository;

import com.ahmed.move.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
public Optional<Movie> findByTitle(String title);
}
