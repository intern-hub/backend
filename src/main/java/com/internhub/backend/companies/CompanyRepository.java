package com.internhub.backend.companies;

//import org.springframework.data.jpa.repository.JpaRepository;
import com.internhub.backend.models.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, Long> {
  // List<Company> OrderBynameAsc(String name);
}
