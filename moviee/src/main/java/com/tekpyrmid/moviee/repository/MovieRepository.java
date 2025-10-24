package com.tekpyrmid.moviee.repository;

import com.tekpyrmid.moviee.entity.Actor;
import com.tekpyrmid.moviee.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findByMovieTitleContainingIgnoreCase(String title);


}
