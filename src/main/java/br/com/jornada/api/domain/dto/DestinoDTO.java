package br.com.jornada.api.domain.dto;

import java.math.BigDecimal;

import br.com.jornada.api.domain.Destino;
import jakarta.validation.constraints.NotBlank;

public record DestinoDTO(
    Long id,

    @NotBlank String nome,

    @NotBlank String url_imagem,

    @NotBlank BigDecimal preco) {

  public DestinoDTO(Destino dadosDestino) {
    this(dadosDestino.getId(), dadosDestino.getNome(), dadosDestino.getUrl_imagem(), dadosDestino.getPreco());
  }
}