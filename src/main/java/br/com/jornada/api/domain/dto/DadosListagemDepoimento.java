package br.com.jornada.api.domain.dto;

import br.com.jornada.api.domain.Depoimento;

public record DadosListagemDepoimento(Long id, String nome, String depoimento, String nomeImagem) {
  public DadosListagemDepoimento(Depoimento depoimento) {
    this(depoimento.getId(), depoimento.getNome(), depoimento.getDepoimento(), depoimento.getNomeImagem());
  }
}
