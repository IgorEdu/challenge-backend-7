package br.com.jornada.api.domain.dto;

import br.com.jornada.api.domain.Depoimento;
import jakarta.validation.constraints.NotBlank;

public record DepoimentoDTO(
    Long id,

    @NotBlank String nome,

    @NotBlank String depoimento,

    @NotBlank String nomeImagem) {

  public DepoimentoDTO(Depoimento dadosSalvo) {
    this(dadosSalvo.getId(), dadosSalvo.getNome(), dadosSalvo.getDepoimento(), dadosSalvo.getNomeImagem());
  }
}