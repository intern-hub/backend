package com.internhub.backend.companies;

import com.internhub.backend.models.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CompanyController {
	@Autowired
	private CompanyRepository repository;

    @GetMapping("/companies")
	@ResponseBody
    List<Company> getCompanies(
	        @RequestParam(name = "coname", required = false) String companyName
    ) {
        if (companyName != null) {
            return repository.findByName(companyName);
        }
		return repository.findAll();
	}

    @GetMapping("/companies/{id}")
    @ResponseBody
    Company getCompany(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new CompanyNotFoundException(id));
    }
}
