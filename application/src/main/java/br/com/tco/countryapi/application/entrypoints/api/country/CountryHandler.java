package br.com.tco.countryapi.application.entrypoints.api.country;

import br.com.tco.countryapi.application.entrypoints.api.AbstractValidationHandler;
import br.com.tco.countryapi.application.entrypoints.api.country.dto.CountryRequest;
import br.com.tco.countryapi.application.entrypoints.api.country.dto.CountryResponse;
import br.com.tco.countryapi.domain.country.usecase.FindCountry;
import br.com.tco.countryapi.domain.country.usecase.SaveCountry;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class CountryHandler extends AbstractValidationHandler<CountryRequest, Validator> {
  final FindCountry findCountry;
  final SaveCountry saveCountry;

  public CountryHandler(
      Validator validator,
      MessageSource messageSource,
      FindCountry findCountry,
      SaveCountry saveCountry) {
    super(CountryRequest.class, validator, messageSource);
    this.findCountry = findCountry;
    this.saveCountry = saveCountry;
  }

  public Mono<ServerResponse> get(@NonNull ServerRequest request) {
    return findCountry
        .findById(request.pathVariable("id"))
        .flatMap(
            country ->
                ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(new CountryResponse(country)), CountryResponse.class))
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> getAll(ServerRequest request) {
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(findCountry.findAll().map(CountryResponse::new), CountryResponse.class);
  }

  @Override
  protected Mono<ServerResponse> processPost(
      @NonNull CountryRequest body, @NonNull ServerRequest request) {
    return saveCountry
        .createCountry(body.toCountry())
        .flatMap(
            country ->
                ServerResponse.created(URI.create(request.path() + "/" + country.id()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(new CountryResponse(country)), CountryResponse.class));
  }
}
