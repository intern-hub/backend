package internhub;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

interface CompanyRepository extends CrudRepository<Company, Long> {

}
