package com.internhub.backend.suggestions;

import com.internhub.backend.auth.UserRepository;
import com.internhub.backend.models.Suggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SuggestionController {
    @Autowired
    private SuggestionRepository suggestionRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/suggestions")
    @ResponseBody
    List<Suggestion> getSuggestions(Principal principal) {
        String username = principal.getName();
        return suggestionRepository.findByUserUsername(username);
    }

    @GetMapping("/suggestions/{id}")
    @ResponseBody
    Suggestion getSuggestion(@PathVariable Long id, Principal principal) {
        String username = principal.getName();
        Suggestion suggestion = suggestionRepository.findById(id)
                .orElseThrow(() -> new SuggestionNotFoundException(id));
        if (!suggestion.getUser().getUsername().equals(username)) {
            throw new SuggestionAccessDeniedException(id);
        }
        return suggestion;
    }

    @PostMapping("/suggestions")
    void createSuggestion(@RequestBody Suggestion suggestion, Principal principal) {
        String username = principal.getName();
        suggestion.setUser(userRepository.findByUsername(username));
        suggestionRepository.save(suggestion);
    }

    /*
    @PutMapping("/positions/{id}")
    Position replacePosition(@RequestBody Position newPosition, @PathVariable Long id) {

        return repository.findById(id)
            .map(position -> {
                position.setLink(newPosition.getLink());
                position.setCompany(newPosition.getCompany());
                position.setTitle(newPosition.getTitle());
                position.setSeason(newPosition.getSeason());
                position.setYear(newPosition.getYear());
                position.setDegree(newPosition.getDegree());
                position.setLocation(newPosition.getLocation());
                return repository.save(position);
            })
            .orElseGet(() -> {
                newPosition.setId(id);
                return repository.save(newPosition);
            });
    }
     */

    @DeleteMapping("/suggestions/{id}")
    void deleteSuggestion(@PathVariable Long id, Principal principal) {
        String username = principal.getName();
        Suggestion application = suggestionRepository.findById(id)
                .orElseThrow(() -> new SuggestionNotFoundException(id));
        if (!application.getUser().getUsername().equals(username)) {
            throw new SuggestionAccessDeniedException(id);
        }
        suggestionRepository.deleteById(id);
    }
}
