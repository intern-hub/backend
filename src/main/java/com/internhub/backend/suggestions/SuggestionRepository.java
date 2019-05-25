package com.internhub.backend.suggestions;

import com.internhub.backend.models.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
    List<Suggestion> findByUserUsername(String username);
}
