package com.tekpyrmid.moviee.repository;

import com.tekpyrmid.moviee.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
    List<Actor> findByName(String name);
}
