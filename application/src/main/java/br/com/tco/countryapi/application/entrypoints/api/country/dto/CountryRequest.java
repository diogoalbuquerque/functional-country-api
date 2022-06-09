package br.com.tco.countryapi.application.entrypoints.api.country.dto;

import br.com.tco.countryapi.domain.country.entities.Country;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CountryRequest {
  @NotBlank private String name;

  @NotBlank
  @Length(min = 2, max = 2)
  private String abbreviation;

  public Country toCountry() {
    return new Country(null, this.name, this.abbreviation);
  }
}
