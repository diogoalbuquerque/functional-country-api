package br.com.tco.countryapi.application.dataproviders.mongo.entity;

import br.com.tco.countryapi.application.ObjectTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CountryEntityTest extends ObjectTest {

  @Test
  void whenCreateCountryEntityFromCountry_returnCountryEntity() {
    final var country = createCountryTest();
    final var countryEntity = new CountryEntity(country);

    assertNotNull(countryEntity, "Country entity could not be null");
    assertEquals(country.id(), countryEntity.getId(), "This value should be equal");
    assertEquals(country.name(), countryEntity.getName(), "This value should be equal");
    assertEquals(
        country.abbreviation(), countryEntity.getAbbreviation(), "This value should be equal");
  }

  @Test
  void whenTransformCountryEntityToCountry_returnCountry() {
    final var countryEntity = createCountryEntityTest();
    final var country = countryEntity.toCountry();

    assertNotNull(country, "Country entity could not be null");
    assertEquals(countryEntity.getId(), country.id(), "This value should be equal");
    assertEquals(countryEntity.getName(), country.name(), "This value should be equal");
    assertEquals(
        countryEntity.getAbbreviation(), country.abbreviation(), "This value should be equal");
  }
}
