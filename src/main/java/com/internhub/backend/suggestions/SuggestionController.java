package com.internhub.backend.suggestions;

import com.internhub.backend.auth.UserRepository;
import com.internhub.backend.errors.exceptions.SuggestionAccessDeniedException;
import com.internhub.backend.errors.exceptions.SuggestionMalformedException;
import com.internhub.backend.errors.exceptions.SuggestionNotFoundException;
import com.internhub.backend.models.Suggestion;
import com.internhub.backend.models.temporary.Identification;
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
    Identification createSuggestion(@RequestBody Suggestion suggestion, Principal principal) {
        if (suggestion.getContent() == null) {
            throw new SuggestionMalformedException();
        }
        String username = principal.getName();
        suggestion.setUser(userRepository.findByUsername(username));
        suggestionRepository.save(suggestion);
        Identification suggestionId = new Identification();
        suggestionId.setId(suggestion.getId());
        return suggestionId;
    }

    @PutMapping("/suggestions/{id}")
    void updateSuggestion(@RequestBody Suggestion suggestionUpdates, @PathVariable Long id, Principal principal) {
        String username = principal.getName();
        Suggestion suggestion = suggestionRepository.findById(id)
                .orElseThrow(() -> new SuggestionNotFoundException(id));
        if (!suggestion.getUser().getUsername().equals(username)) {
            throw new SuggestionAccessDeniedException(id);
        }
        // NOTE: The user field of an suggestion is immutable
        if (suggestionUpdates.getContent() != null)
            suggestion.setContent(suggestionUpdates.getContent());
        suggestionRepository.save(suggestion);
    }

    @DeleteMapping("/suggestions/{id}")
    void deleteSuggestion(@PathVariable Long id, Principal principal) {
        String username = principal.getName();
        Suggestion suggestion = suggestionRepository.findById(id)
                .orElseThrow(() -> new SuggestionNotFoundException(id));
        if (!suggestion.getUser().getUsername().equals(username)) {
            throw new SuggestionAccessDeniedException(id);
        }
        suggestionRepository.deleteById(id);
    }
}
