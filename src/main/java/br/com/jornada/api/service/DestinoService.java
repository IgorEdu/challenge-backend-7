package br.com.jornada.api.service;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.jornada.api.domain.Destino;
import br.com.jornada.api.domain.DestinoRepository;
import br.com.jornada.api.domain.dto.CadastroDestinoDTO;
import br.com.jornada.api.domain.dto.DestinoDTO;

public class DestinoService {
  @Autowired
  private DestinoRepository repository;

  public DestinoDTO salvar(CadastroDestinoDTO dados) {
    Destino destino = dados.toModel();
    Destino destinoSalvo = repository.save(destino);
    return new DestinoDTO(destinoSalvo);
  }

  public DestinoDTO atualizar(DestinoDTO dados) {
    return null;
  }

  public void excluir(Long id) {
  }
}
