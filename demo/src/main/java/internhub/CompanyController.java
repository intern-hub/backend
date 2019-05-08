package internhub;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyController {

    private final CompanyRepository repository;

    CompanyController(CompanyRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/companies")
    List<Company> all() {
        return repository.findAll();
    }

    @PostMapping("/companies")
    Company newCompany(@RequestBody Company newCompany) {
        return repository.save(newCompany);
    }

    @GetMapping("/companies/{id}")
    Company one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(id));
    }

    @PutMapping("/companies/{id}")
    Company replaceCompany(@RequestBody Company newCompany, @PathVariable Long id) {

        return repository.findById(id)
                .map(employee -> {
                    employee.setName(newCompany.getName());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newCompany.setId(id);
                    return repository.save(newCompany);
                });
    }

    @DeleteMapping("/companies/{id}")
    void deleteCompany(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
