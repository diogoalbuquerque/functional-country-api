package br.com.tco.countryapi.application.dataproviders.mongo.entity;

import br.com.tco.countryapi.domain.country.entities.Country;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "country")
public class CountryEntity {

  @Id private String id;

  private String name;

  private String abbreviation;

  public CountryEntity(Country country) {
    super();
    this.id = country.id();
    this.name = country.name();
    this.abbreviation = country.abbreviation();
  }

  public Country toCountry() {
    return new Country(this.id, this.name, this.abbreviation);
  }
}
