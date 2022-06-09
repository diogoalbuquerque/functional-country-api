package br.com.tco.countryapi.application.entrypoints.api.country.dto;

import br.com.tco.countryapi.application.ObjectTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CountryResponseTest extends ObjectTest {
  @Test
  void whenCreateCountryResponseFromCountry_returnCountryResponse() {
    final var country = createCountryTest();
    final var countryResponse = new CountryResponse(country);

    assertNotNull(countryResponse, "Country response could not be null");
    assertEquals(country.id(), countryResponse.id(), "This value should be equal");
    assertEquals(country.name(), countryResponse.name(), "This value should be equal");
    assertEquals(
        country.abbreviation(), countryResponse.abbreviation(), "This value should be equal");
  }
}
