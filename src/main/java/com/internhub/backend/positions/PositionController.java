package com.internhub.backend.positions;

import com.internhub.backend.models.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PositionController {
	@Autowired
	private PositionRepository repository;

    @GetMapping("/positions")
	public @ResponseBody
    List<Position> getPositions(
            @RequestParam(name = "coname", required = false) String companyName
    ) {
        if (companyName != null) {
            return repository.findByCompanyName(companyName);
        }
		return repository.findAll();
	}

    @GetMapping("/positions/{id}")
    Position getPosition(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new PositionNotFoundException(id));
    }
}
