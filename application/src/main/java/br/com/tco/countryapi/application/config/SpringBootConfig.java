package br.com.tco.countryapi.application.config;

import br.com.tco.countryapi.application.dataproviders.mongo.CountryMongoReactiveRepository;
import br.com.tco.countryapi.application.dataproviders.mongo.CountryReactiveRepository;
import br.com.tco.countryapi.domain.country.usecase.FindCountry;
import br.com.tco.countryapi.domain.country.usecase.SaveCountry;
import br.com.tco.countryapi.domain.country.usecase.port.CountryRepository;
import br.com.tco.countryapi.domain.country.usecase.validator.CountryValidatorImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SpringBootConfig {

  final CountryReactiveRepository countryReactiveRepository;

  @Bean
  public CountryRepository repository() {
    return new CountryMongoReactiveRepository(countryReactiveRepository);
  }

  @Bean
  public FindCountry createFindCountry() {
    return new FindCountry(this.repository());
  }

  @Bean
  public SaveCountry createSaveCountry() {
    return new SaveCountry(new CountryValidatorImpl(), this.repository());
  }
}
