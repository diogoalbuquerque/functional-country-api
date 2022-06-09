package br.com.tco.countryapi.application.entrypoints.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ValidationErrorResponse {
  private Integer status;
  private String message;
  private Integer code;
  private LocalDateTime eventTime;
  private List<Violation> violations;

  public ValidationErrorResponse(
      Integer status, String message, Integer code, LocalDateTime eventTime) {
    this.status = status;
    this.message = message;
    this.code = code;
    this.eventTime = eventTime;
  }
}
