package com.tekpyrmid.moviee.controller;

import com.tekpyrmid.moviee.entity.Movie;
import com.tekpyrmid.moviee.response.SuccessResponse;
import com.tekpyrmid.moviee.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping("/save-movie")
    public ResponseEntity<SuccessResponse> save(@RequestBody Movie movies) {
        String save = movieService.save(movies);
        SuccessResponse successResponse = new SuccessResponse();

        successResponse.setMessage(save);
        successResponse.setHttpStatus(HttpStatus.ACCEPTED);
        successResponse.setData(null);
        successResponse.setError(false);
        return ResponseEntity.status(HttpStatus.ACCEPTED.value()).body(successResponse);
    }

    @PutMapping("/update-movie/{id}")
    public  ResponseEntity<SuccessResponse> update(@RequestBody Movie movie,@PathVariable("id") int movieId){
        String update =movieService.update(movie,movieId);

        SuccessResponse successResponse = new SuccessResponse();

        successResponse.setMessage(update);
        successResponse.setHttpStatus(HttpStatus.ACCEPTED);
        successResponse.setData(null);
        successResponse.setError(false);
        return ResponseEntity.status(HttpStatus.ACCEPTED.value()).body(successResponse);
    }

    @PutMapping("/update-all/{id}")
    public ResponseEntity<SuccessResponse> updateAll(
            @RequestBody Movie movie,
            @PathVariable("id") int movieId) {

        String msg = movieService.updateAll(movie, movieId);

        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setMessage(msg);
        successResponse.setHttpStatus(HttpStatus.ACCEPTED);
        successResponse.setData(null);
        successResponse.setError(false);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(successResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMovieById(@PathVariable int id){

        String message = movieService.deleteMovieById(id);
        SuccessResponse successResponse=new SuccessResponse();
        successResponse.setHttpStatus(HttpStatus.OK);
        successResponse.setError(false);
        successResponse.setMessage(message);
        successResponse.setData(null);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("/fetch/search")
    public List<Movie> searchMovies(@RequestParam("title") String title) {
        return movieService.searchMoviesByMovieTitle(title);
    }
}
