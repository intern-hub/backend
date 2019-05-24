package com.internhub.backend.positions;

import com.internhub.backend.models.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PositionController {
	@Autowired
	private PositionRepository repository;

    @GetMapping("/positions")
	public @ResponseBody Iterable<Position> getAllCompanies() {
		// This returns a JSON or XML with the companies
		return repository.findAll();
	}

    @PostMapping("/positions")
    public @ResponseBody String newPosition(@RequestBody Position newPosition) {
        repository.save(newPosition);
        return "good job you saved";
    }

    @GetMapping("/positions/{id}")
    Position one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new PositionNotFoundException(id));
    }

    @PutMapping("/positions/{id}")
    Position replacePosition(@RequestBody Position newPosition, @PathVariable Long id) {

        return repository.findById(id)
            .map(position -> {
                position.setLink(newPosition.getLink());
                position.setCompany(newPosition.getCompany());
                position.setTitle(newPosition.getTitle());
                position.setSeason(newPosition.getSeason());
                position.setYear(newPosition.getYear());
                position.setDegree(newPosition.getDegree());
                position.setLocation(newPosition.getLocation());
                return repository.save(position);
            })
            .orElseGet(() -> {
                newPosition.setId(id);
                return repository.save(newPosition);
            });
    }

    @DeleteMapping("/positions/{id}")
    void deleteCompany(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
