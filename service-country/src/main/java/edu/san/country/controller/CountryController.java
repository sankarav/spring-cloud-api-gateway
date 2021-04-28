package edu.san.country.controller;

import edu.san.country.models.Country;
import edu.san.country.repo.CountryRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CountryController {

  private final CountryRepository repo;

  @GetMapping("/org/v1/country")
  public List<Country> getAll() {
    return repo.getAll();
  }

  @GetMapping("/old/hello")
  public String trustedOldService() {
    System.out.println("Executing Current Hello Service");
    return "Hello from trusted Old Service";
  }

  @GetMapping("/new/hello")
  public String unknownNewService() {
    System.out.println("Executing TOBE Hello Service");
    return "Hello from unknown but cool new Service";
  }
}
