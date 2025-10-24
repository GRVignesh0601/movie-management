package com.tekpyrmid.moviee.repository;

import com.tekpyrmid.moviee.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Integer> {
    Optional<Language> findByName(String name);
}
