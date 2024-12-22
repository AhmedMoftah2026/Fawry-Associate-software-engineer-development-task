package com.ahmed.move.repository;

import com.ahmed.move.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
public Movie findByTitle(String title);
}
