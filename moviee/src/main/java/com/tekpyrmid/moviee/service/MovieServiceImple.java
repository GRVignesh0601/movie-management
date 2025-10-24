package com.tekpyrmid.moviee.service;

import com.tekpyrmid.moviee.entity.Actor;
import com.tekpyrmid.moviee.entity.Financial;
import com.tekpyrmid.moviee.entity.Language;
import com.tekpyrmid.moviee.entity.Movie;
import com.tekpyrmid.moviee.repository.ActorRepository;
import com.tekpyrmid.moviee.repository.FinancialRepository;
import com.tekpyrmid.moviee.repository.LanguageRepository;
import com.tekpyrmid.moviee.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImple implements MovieService {
    private final MovieRepository movieRepository;
    private final LanguageRepository languageRepository;
    private final ActorRepository actorRepository;
    private final FinancialRepository financialRepository;

    @Override
    public String save(Movie movie) {

        Language inputLanguage = movie.getLanguage();

        Optional<Language> existingLang = languageRepository.findByName(inputLanguage.getName());
        if (existingLang.isPresent()) {

            movie.setLanguage(existingLang.get());
        }

        Financial financial = movie.getFinancial();
        financial.setMovie(movie);

        List<Actor> actors = movie.getActors();

        for (Actor actor : actors) {
            List<Movie> moviesList = actor.getMovies();
            if (moviesList != null) {
                moviesList.add(movie);
            } else {
                actor.setMovies(new ArrayList<>(List.of(movie)));
            }
            actor.setMovies(moviesList);
        }
        int movieId = movieRepository.save(movie).getMovieId();

        return "Movie saved successfully!" + movieId;
    }

    @Override
    public String update(Movie movie, int moviesId) {


        Optional<Movie> movie2 = movieRepository.findById(moviesId);

        if(movie2.isPresent()){
            Movie movie1 = movie2.get();
            movie1.setMovieTitle(movie.getMovieTitle());
            movie1.setIndustry(movie.getIndustry());
            movie1.setReleaseYear(movie.getReleaseYear());
            movie1.setImdbRating(movie.getImdbRating());
            movie1.setStudio(movie.getStudio());
            movieRepository.save(movie1);

        } else {
            throw new RuntimeException("id not found");
        }
        return "Movie updated successfully! ID: "+ movie.getMovieId();
    }

    @Override
    public String updateAll(Movie movie, int movieId) {
        // 1. Fetch existing movie
        Movie movie1 = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie id not found: " + movieId));

        // 2. Update basic fields
        movie1.setMovieTitle(movie.getMovieTitle());
        movie1.setIndustry(movie.getIndustry());
        movie1.setReleaseYear(movie.getReleaseYear());
        movie1.setImdbRating(movie.getImdbRating());
        movie1.setStudio(movie.getStudio());

        // 3. Update Language
        if (movie.getLanguage() != null) {
            Language inputLang = movie.getLanguage();
            Language language = null;

            if (inputLang.getLanguageId() != 0) {
                language = languageRepository.findById(inputLang.getLanguageId()).orElse(null);
            }

            if (language == null && inputLang.getName() != null) {
                language = languageRepository.findByName(inputLang.getName()).orElse(null);
            }

            if (language == null && inputLang.getName() != null) {
                language = Language.builder()
                        .name(inputLang.getName())
                        .build();
            }

            movie1.setLanguage(language);
        }

        // 4. Update Financial
        if (movie.getFinancial() != null) {
            Financial fd = movie.getFinancial();
            Financial financial = movie1.getFinancial() != null ? movie1.getFinancial() : new Financial();

            financial.setBudget(fd.getBudget());
            financial.setRevenue(fd.getRevenue());
            financial.setUnit(fd.getUnit());
            financial.setCurrency(fd.getCurrency());
            financial.setMovie(movie1);

            movie1.setFinancial(financial);
        }

        // 5. Update Actors
        if (movie.getActors() != null) {
            List<Actor> updatedActors = new ArrayList<>();

            for (Actor aDto : movie.getActors()) {
                Actor actor = null;

                if (aDto.getActorId() != null) {
                    actor = actorRepository.findById(aDto.getActorId()).orElse(null);
                }

                List<Actor> actors = actorRepository.findByName(aDto.getName());
                if (!actors.isEmpty()) {
                    actor = actors.get(0); // pick the first actor if multiple exist
                } else {
                    actor = Actor.builder()
                            .name(aDto.getName())
                            .build();
                }

                if (actor == null) {
                    actor = Actor.builder().name(aDto.getName()).build();
                }

                if (aDto.getDateOfBirth() != null) {
                    try {
                        actor.setDateOfBirth(aDto.getDateOfBirth());
                    } catch (DateTimeParseException ex) {
                        throw new RuntimeException("Invalid date format for actor: " +
                                aDto.getName() + ". Expected yyyy-MM-dd");
                    }
                }

                if (actor.getMovies() == null) actor.setMovies(new ArrayList<>());
                if (!actor.getMovies().contains(movie1)) actor.getMovies().add(movie1);

                updatedActors.add(actor);
            }

            movie1.setActors(updatedActors);
        }

        // 6. Save updated movie
        Movie saved = movieRepository.save(movie1);

        return "Movie updated successfully! ID: " + saved.getMovieId();
    }
    @Override
    public String deleteMovieById(int movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + movieId));


        Language language = movie.getLanguage();
        if (language != null) {
            movie.setLanguage(null);


            List<Movie> moviesWithLang = movieRepository.findAll()
                    .stream()
                    .filter(m -> m.getLanguage() != null && m.getLanguage().getLanguageId() == language.getLanguageId())
                    .toList();

            if (moviesWithLang.isEmpty()) {
                languageRepository.delete(language);
            }
        }


        if (movie.getActors() != null) {
            for (Actor actor : movie.getActors()) {
                List<Movie> actorMovies = actor.getMovies();
                actorMovies.remove(movie);


                if (actorMovies.isEmpty()) {
                    actorRepository.delete(actor);
                }
            }
            movie.getActors().clear();
        }


        Financial financial = movie.getFinancial();
        if (financial != null) {
            financial.setMovie(null);
            movie.setFinancial(null);
            financialRepository.delete(financial);
        }


        movieRepository.delete(movie);

        return "Movie deleted successfully with id: " + movieId;

    }

    @Override
    public List<Movie> searchMoviesByMovieTitle(String title) {
        return movieRepository.findByMovieTitleContainingIgnoreCase(title);
    }


}

