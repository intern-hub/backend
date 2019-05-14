package internhub;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

interface CompanyRepository extends CrudRepository<Company, Long> {
  // List<Company> OrderBynameAsc(String name);
}
