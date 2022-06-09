package br.com.tco.countryapi.application.config;

import br.com.tco.countryapi.application.entrypoints.api.country.CountryHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

  protected static final String V1_COUNTRY_PATH = "/v1/country";

  @Bean
  @Primary
  public Validator springValidator() {
    return new LocalValidatorFactoryBean();
  }

  @Bean
  RouterFunction<ServerResponse> routes(CountryHandler countryHandler) {
    return route(GET(V1_COUNTRY_PATH).and(accept(APPLICATION_JSON)), countryHandler::getAll)
        .andRoute(GET(V1_COUNTRY_PATH + "/{id}").and(accept(APPLICATION_JSON)), countryHandler::get)
        .andRoute(
            POST(V1_COUNTRY_PATH).and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)),
            countryHandler::handlePostRequest);
  }
}
