package com.internhub.backend.positions;

import com.internhub.backend.models.Season;
import com.internhub.backend.models.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PositionRepository extends JpaRepository<Position, Long> {
  List<Position> findByTitle(String title);
  List<Position> findBySeason(Season season);
  List<Position> findByYear(int year);
  List<Position> findByDegree(String degree);
  List<Position> findByLocation(String location);
}
