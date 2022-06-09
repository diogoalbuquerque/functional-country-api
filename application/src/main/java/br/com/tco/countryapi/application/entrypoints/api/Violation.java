package br.com.tco.countryapi.application.entrypoints.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Violation {
  private String name;
  private String message;
}
