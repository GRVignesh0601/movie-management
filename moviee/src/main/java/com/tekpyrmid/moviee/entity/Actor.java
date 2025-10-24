package com.tekpyrmid.moviee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "actors")
public class Actor {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer actorId;
    @Column(name = "actor_name")
    private String name;
    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @ManyToMany(mappedBy = "actors",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Movie> movies;
}
