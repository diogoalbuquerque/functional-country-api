package br.com.tco.countryapi.application.entrypoints.api.country.dto;

import br.com.tco.countryapi.application.ObjectTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CountryRequestTest extends ObjectTest {

  @Test
  void whenTransformCountryRequestToCountry_returnCountry() {
    final var countryRequest = createCountryRequestTest();
    final var country = countryRequest.toCountry();

    assertNotNull(country, "Country could not be null");
    assertEquals(countryRequest.getName(), country.name(), "This value should be equal");
    assertEquals(
        countryRequest.getAbbreviation(), country.abbreviation(), "This value should be equal");
  }
}
