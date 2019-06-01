package com.internhub.backend.applications;

import com.internhub.backend.auth.UserRepository;
import com.internhub.backend.companies.CompanyRepository;
import com.internhub.backend.errors.exceptions.ApplicationAccessDeniedException;
import com.internhub.backend.errors.exceptions.ApplicationConflictException;
import com.internhub.backend.errors.exceptions.ApplicationMalformedException;
import com.internhub.backend.errors.exceptions.ApplicationNotFoundException;
import com.internhub.backend.models.Application;
import com.internhub.backend.models.Company;
import com.internhub.backend.models.Position;
import com.internhub.backend.errors.exceptions.PositionNotFoundException;
import com.internhub.backend.models.temporary.Identification;
import com.internhub.backend.positions.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApplicationController {
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private PositionRepository positionRepository;

    @GetMapping("/applications")
    @ResponseBody
    List<Application> getApplications(
            @RequestParam(name = "coname", required = false) String companyName,
            @RequestParam(name = "posid", required = false) Long positionId,
            Principal principal
    ) {
        String username = principal.getName();
        if (positionId != null) {
            return applicationRepository.findByUserUsernameAndPositionId(username, positionId);
        }
        if (companyName != null) {
            /*
             * TODO:
             *  This may not work because of the double nesting
             *  If it doesn't, find all positions associated with the company and then
             *  use the findByUsernameAndPositionId() method instead
             */
            return applicationRepository.findByUserUsernameAndPositionCompanyName(username, companyName);
        }
        return applicationRepository.findByUserUsername(username);
    }

    @GetMapping("/applications/{id}")
    @ResponseBody
    Application getApplication(@PathVariable Long id, Principal principal) {
        String username = principal.getName();
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(id));
        if (!application.getUser().getUsername().equals(username))
            throw new ApplicationAccessDeniedException(id);
        return application;
    }

    @PostMapping("/applications")
    Identification createApplication(@RequestBody Application application, Principal principal) {
        String username = principal.getName();

        // Application must include required fields
        if (application.getPosition() == null)
            throw new ApplicationMalformedException();

        // Application has optional fields filled in with default values
        if (application.getNotes() == null)
            application.setNotes("");
        if (application.isApplied() == null)
            application.setApplied(false);
        if (application.isBroken() == null)
            application.setBroken(false);

        // Check that the application references a valid position
        Position position = application.getPosition();
        Long positionId = position.getId();
        if (!positionRepository.existsById(positionId)) {
            throw new PositionNotFoundException(positionId);
        }

        // Check that the given user does not have any applications corresponding to
        // this position's application (applications should be unique to (user, position))
        if (!applicationRepository.findByUserUsernameAndPositionId(username, positionId).isEmpty()) {
            throw new ApplicationConflictException(username, positionId);
        }

        // Increment the target company's popularity if on creation, we have applied to that company
        Company company = positionRepository.findById(positionId).get().getCompany();
        if (application.isApplied()) {
            company.setPopularity(company.getPopularity() + 1);
            companyRepository.save(company);
        }

        application.setUser(userRepository.findByUsername(username));
        applicationRepository.save(application);

        Identification applicationId = new Identification();
        applicationId.setId(application.getId());
        return applicationId;
    }

    @PutMapping("/applications/{id}")
    void updateApplication(@RequestBody Application applicationUpdates, @PathVariable Long id, Principal principal) {
        String username = principal.getName();
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(id));
        if (!application.getUser().getUsername().equals(username))
            throw new ApplicationAccessDeniedException(id);

        // Increment or decrement the target company's popularity if
        // we changing the application's applied status
        Company company = application.getPosition().getCompany();
        if (applicationUpdates.isApplied() != null && application.isApplied() != applicationUpdates.isApplied()) {
            if (applicationUpdates.isApplied())
                company.setPopularity(company.getPopularity() + 1);
            else
                company.setPopularity(company.getPopularity() - 1);
            companyRepository.save(company);
        }

        // NOTE: The user and position fields of an application are immutable
        if (applicationUpdates.getNotes() != null)
            application.setNotes(applicationUpdates.getNotes());
        if (applicationUpdates.isApplied() != null)
            application.setApplied(applicationUpdates.isApplied());
        if (applicationUpdates.isBroken() != null)
            application.setBroken(applicationUpdates.isBroken());
        applicationRepository.save(application);
    }

    @DeleteMapping("/applications/{id}")
    void deleteApplication(@PathVariable Long id, Principal principal) {
        String username = principal.getName();
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(id));
        if (!application.getUser().getUsername().equals(username))
            throw new ApplicationAccessDeniedException(id);

        // Decrement the target's company popularity if deleted application
        // was marked as having applied
        Company company = application.getPosition().getCompany();
        if (application.isApplied()) {
            company.setPopularity(company.getPopularity() - 1);
            companyRepository.save(company);
        }

        applicationRepository.deleteById(id);
    }
}
