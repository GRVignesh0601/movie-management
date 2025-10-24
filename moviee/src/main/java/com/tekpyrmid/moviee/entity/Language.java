package com.tekpyrmid.moviee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "languages")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "language_id")
    private int languageId;
    @Column(name = "language_name")
    private String name;

    @OneToMany(mappedBy = "language",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Movie> movieList;



}
