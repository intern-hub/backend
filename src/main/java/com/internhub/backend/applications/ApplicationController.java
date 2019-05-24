package com.internhub.backend.applications;

import com.internhub.backend.models.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class ApplicationController {
    @Autowired
    private ApplicationRepository repository;

    @GetMapping("/applications")
    public @ResponseBody Iterable<Application> getApplications(
            @RequestParam(name = "coname", required = false) String companyName,
            Principal principal
    ) {
        String username = principal.getName();
        if (companyName != null) {
            /*
             * TODO:
             *  This may not work because of the double nesting
             *  If it doesn't, find all positions associated with the company and then
             *  use the findByUsernameAndPositionId() method instead
             */
            return repository.findByUserUsernameAndPositionCompanyName(username, companyName);
        }
        return repository.findByUserUsername(username);
    }

    @GetMapping("/applications/{id}")
    Application getApplication(@PathVariable Long id, Principal principal) {
        String username = principal.getName();
        Application application = repository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(id));
        if (!application.getUser().getUsername().equals(username)) {
            throw new ApplicationAccessDeniedException(id);
        }
        return application;
    }

    /*
    @PostMapping("/applications")
    public @ResponseBody String newPosition(@RequestBody Position newPosition) {
        repository.save(newPosition);
        return "good job you saved";
    }

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

    @DeleteMapping("/positions/{id}")
    void deleteCompany(@PathVariable Long id) {
        repository.deleteById(id);
    }
    */
}
