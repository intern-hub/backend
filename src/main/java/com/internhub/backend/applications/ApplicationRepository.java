package com.internhub.backend.applications;

import com.internhub.backend.models.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByUserUsername(String username);
    List<Application> findByUserUsernameAndPositionId(String username, Long id);
    List<Application> findByUserUsernameAndPositionCompanyName(String username, String name);
}
