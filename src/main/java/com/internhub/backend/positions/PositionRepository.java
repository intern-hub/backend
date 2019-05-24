package com.internhub.backend.positions;

import com.internhub.backend.models.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PositionRepository extends JpaRepository<Position, Long> {
  List<Position> findByCompanyName(String companyName);
}
