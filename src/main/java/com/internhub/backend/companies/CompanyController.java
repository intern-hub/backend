package com.internhub.backend.companies;

import com.internhub.backend.models.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CompanyController {
	@Autowired
	private CompanyRepository repository;

    @GetMapping("/companies")
	public @ResponseBody
    Iterable<Company> getCompanies(
	        @RequestParam(name = "name", required = false) String name
    ) {
        if (name != null) {
            return repository.findByName(name);
        }
		return repository.findAll();
	}

    @GetMapping("/companies/{id}")
    Company getCompany(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new CompanyNotFoundException(id));
    }
}
