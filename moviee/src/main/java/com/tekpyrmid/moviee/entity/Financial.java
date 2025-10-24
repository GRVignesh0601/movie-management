package com.tekpyrmid.moviee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tekpyrmid.moviee.entity.Movie;
import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "financials")
public class Financial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "financial_id")
    private int financialId;
    private double budget;
    private double revenue;
    private String unit;
    private String currency;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id")
    @JsonIgnore
    private Movie movie;
}
