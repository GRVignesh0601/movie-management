package com.tekpyrmid.moviee.entity;

import com.tekpyrmid.moviee.entity.Language;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@Entity
@Data
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "movie_id")
    private int movieId;
    private String movieTitle;
    private String industry;
    @Column(name = "release_year")
    private String releaseYear;
    @Column(name = "imdb_rating")
    private Double imdbRating;
    private String studio;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "language_id")
    private Language language;

    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST},fetch = FetchType.EAGER)
    @JoinTable(
            name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )

    private List<Actor> actors;

    @OneToOne(mappedBy = "movie", cascade = CascadeType.ALL)
    private Financial financial;


}
