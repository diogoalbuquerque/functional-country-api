package br.com.tco.countryapi.application.entrypoints.api;

import br.com.tco.countryapi.domain.exception.BaseDomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.NonNull;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RequiredArgsConstructor
public abstract class AbstractValidationHandler<T, U extends Validator> {

  protected static final int NON_MAPPED_ERROR_CODE = 0;
  protected static final int METHOD_ARGUMENT_ERROR_CODE = 99;
  protected static final String VALIDATION_FAILED_MESSAGE = "Validation failed";
  final Class<T> validationClass;
  final U validator;
  final MessageSource messageSource;

  protected abstract Mono<ServerResponse> processPost(T body, final ServerRequest request);

  public final Mono<ServerResponse> handlePostRequest(final ServerRequest request) {
    return request
        .bodyToMono(this.validationClass)
        .flatMap(
            body -> {
              Errors errors = new BeanPropertyBindingResult(body, this.validationClass.getName());
              this.validator.validate(body, errors);

              if (errors.getAllErrors().isEmpty()) {
                return processPost(body, request)
                    .onErrorResume(
                        ex -> {
                          if (BaseDomainException.class.isAssignableFrom(ex.getClass())) {
                            return onBaseBusinessException((BaseDomainException) ex);
                          }
                          return onNonMappedException(ex);
                        });
              } else {
                return onValidationErrors(errors);
              }
            });
  }

  private Mono<ServerResponse> onBaseBusinessException(@NonNull final BaseDomainException ex) {
    final var validationErrorResponse =
        new ValidationErrorResponse(
            BAD_REQUEST.value(), ex.getMessage(), ex.getErrorCode(), LocalDateTime.now());

    return ServerResponse.badRequest()
        .contentType(APPLICATION_JSON)
        .body(Mono.just(validationErrorResponse), ValidationErrorResponse.class);
  }

  private Mono<ServerResponse> onNonMappedException(@NonNull final Throwable ex) {
    final var validationErrorResponse =
        new ValidationErrorResponse(
            INTERNAL_SERVER_ERROR.value(),
            ex.getMessage(),
            NON_MAPPED_ERROR_CODE,
            LocalDateTime.now());
    return ServerResponse.status(INTERNAL_SERVER_ERROR)
        .contentType(APPLICATION_JSON)
        .body(Mono.just(validationErrorResponse), ValidationErrorResponse.class);
  }

  private Mono<ServerResponse> onValidationErrors(@NonNull final Errors errors) {
    final var violations =
        errors.getAllErrors().stream()
            .map(
                error -> {
                  var name = ((FieldError) error).getField();
                  var message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
                  return new Violation(name, message);
                })
            .collect(toList());

    final var validationErrorResponse =
        new ValidationErrorResponse(
            BAD_REQUEST.value(),
            VALIDATION_FAILED_MESSAGE,
            METHOD_ARGUMENT_ERROR_CODE,
            LocalDateTime.now(),
            violations);

    return ServerResponse.badRequest()
        .contentType(APPLICATION_JSON)
        .body(Mono.just(validationErrorResponse), ValidationErrorResponse.class);
  }
}
