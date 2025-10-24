package com.tekpyrmid.moviee.service;

import com.tekpyrmid.moviee.entity.Movie;

import java.util.List;

public interface MovieService {
    String save(Movie movies);

    String update(Movie movie, int movieId);

    String updateAll(Movie movie, int movieId);

    String deleteMovieById(int id);

    List<Movie> searchMoviesByMovieTitle(String title);
}
