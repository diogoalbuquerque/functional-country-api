package br.com.tco.countryapi.application;

import br.com.tco.countryapi.application.entrypoints.api.country.CountryHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ApplicationTests {

  @Autowired CountryHandler countryHandler;

  @Test
  void contextLoads() {
    assertNotNull(countryHandler);
  }
}
