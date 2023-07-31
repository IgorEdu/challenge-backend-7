package br.com.jornada.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import br.com.jornada.api.domain.Depoimento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastroDepoimentoDTO(
    @NotBlank(message = "campo 'nome' obrigatório") @Size(min = 1, max = 120) String nome,
    @NotBlank(message = "campo 'depoimento' obrigatório") @Size(min = 1, max = 500) String depoimento,
    @JsonAlias(value = "nomeImagem") @Size(min = 1, max = 255) String nomeImagem) {
  public Depoimento toModel() {
    return new Depoimento(nome(), depoimento(), nomeImagem());
  }
}
