package edu.san.country.controller;

import edu.san.country.models.Country;
import edu.san.country.repo.CountryRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/org/v1/country")
@AllArgsConstructor
public class CountryController {

  private final CountryRepository repo;

  @GetMapping
  public List<Country> getAll() {
    return repo.getAll();
  }
}
