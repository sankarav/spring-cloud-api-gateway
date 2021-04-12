package edu.san.servercountry.controller;

import edu.san.servercountry.models.Country;
import edu.san.servercountry.repo.CountryRepository;
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
