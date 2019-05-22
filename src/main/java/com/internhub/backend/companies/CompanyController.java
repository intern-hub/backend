package com.internhub.backend.companies;

import com.internhub.backend.models.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api") // Use /api as our base root; don't worry about versioning
public class CompanyController {
	@Autowired
	private CompanyRepository repository;

    @GetMapping("/companies")
	public @ResponseBody Iterable<Company> getAllCompanies() {
		// This returns a JSON or XML with the companies
		return repository.findAll();
	}

    @PostMapping("/companies")
    public @ResponseBody String newCompany(@RequestBody Company newCompany) {
        repository.save(newCompany);
        return "good job you saved";
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
                    employee.setWebsite(newCompany.getWebsite());
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
