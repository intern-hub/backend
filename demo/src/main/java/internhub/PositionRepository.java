package internhub;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

interface PositionRepository extends CrudRepository<Position, Long> {
  List<Position> findByTitle(String title);
  List<Position> findBySeason(Season season);
  List<Position> findByYear(int year);
  List<Position> findByDegree(String degree);
  List<Position> findByLocation(String location);
}
