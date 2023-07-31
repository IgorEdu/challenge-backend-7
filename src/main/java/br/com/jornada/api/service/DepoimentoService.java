package br.com.jornada.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import br.com.jornada.api.domain.Depoimento;
import br.com.jornada.api.domain.DepoimentoRepository;
import br.com.jornada.api.domain.dto.CadastroDepoimentoDTO;
import br.com.jornada.api.domain.dto.DepoimentoDTO;

@Service
public class DepoimentoService {

  @Autowired
  private DepoimentoRepository repository;

  public DepoimentoDTO salvar(CadastroDepoimentoDTO dados) {
    Depoimento depoimento = dados.toModel();
    Depoimento depoimentoSalvo = repository.save(depoimento);
    return new DepoimentoDTO(depoimentoSalvo);
  }

  public DepoimentoDTO detalhar(Long id) {
    Depoimento depoimento = repository.getReferenceById(id);
    return new DepoimentoDTO(depoimento);
  }

  public List<DepoimentoDTO> listar(DepoimentoDTO paramPesquisa) {
    Depoimento depoimento = paramPesquisa.toModel();
    Example<Depoimento> exemplo = Example.of(depoimento);
    List<Depoimento> depoimentos = repository.findAll(exemplo);
    return depoimentos.stream().map(
        DepoimentoDTO::new).toList();
  }

  public List<DepoimentoDTO> listarTresDepoimentosAleatorios() {
    List<Depoimento> depoimentos = repository.encontrarTresDepoimentosAleatoriamente();
    return depoimentos.stream().map(
        DepoimentoDTO::new).toList();
  }

  public void excluir(Long id) {
    this.repository.deleteById(id);
  }

  public DepoimentoDTO atualizar(DepoimentoDTO dados) {
    Depoimento depoimentoAAtualizar = repository.getReferenceById(dados.id());
    depoimentoAAtualizar.setDepoimento(dados.depoimento());
    depoimentoAAtualizar.setNomeImagem(dados.nomeImagem());
    repository.save(depoimentoAAtualizar);
    return new DepoimentoDTO(depoimentoAAtualizar);
  }

}
