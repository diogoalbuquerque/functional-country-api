package br.com.tco.countryapi.application;

import br.com.tco.countryapi.application.dataproviders.mongo.entity.CountryEntity;
import br.com.tco.countryapi.application.entrypoints.api.country.dto.CountryRequest;
import br.com.tco.countryapi.domain.country.entities.Country;

public class ObjectTest {
  protected final Country createCountryTest() {
    return new Country("A1", "Brazil", "BR");
  }

  protected final Country createAnotherCountryTest() {
    return new Country("A2", "United States of America", "US");
  }

  protected final CountryEntity createCountryEntityTest() {
    final var countryEntity = new CountryEntity();
    countryEntity.setId("A1");
    countryEntity.setName("Brazil");
    countryEntity.setAbbreviation("BR");
    return countryEntity;
  }

  protected final CountryEntity createAnotherCountryEntityTest() {
    final var countryEntity = new CountryEntity();
    countryEntity.setId("A2");
    countryEntity.setName("United States of America");
    countryEntity.setAbbreviation("US");
    return countryEntity;
  }

  protected final CountryRequest createCountryRequestTest() {
    return new CountryRequest("Brazil", "BR");
  }
}
