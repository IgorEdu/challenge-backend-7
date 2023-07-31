package br.com.jornada.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.jornada.api.domain.Depoimento;
import br.com.jornada.api.domain.DepoimentoRepository;
import br.com.jornada.api.domain.dto.DadosListagemDepoimento;
import br.com.jornada.api.domain.dto.DepoimentoDTO;

@Service
public class DepoimentoService {

  @Autowired
  private DepoimentoRepository repository;

  @Autowired
  private ObjectMapper objectMapper;

  public DepoimentoDTO salvar(String dados) {
    DepoimentoDTO depoimento;
    try {
      depoimento = objectMapper.readValue(dados, DepoimentoDTO.class);

      Depoimento dadosSalvo = repository.save(new Depoimento(depoimento));
      return new DepoimentoDTO(dadosSalvo);
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    } catch (IllegalStateException e) {
      e.printStackTrace();
    }
    return null;
  }

  public DadosListagemDepoimento detalhar(Long id) {
    Depoimento depoimento = repository.getReferenceById(id);
    return new DadosListagemDepoimento(depoimento);
  }

  public void excluir(Long id) {
    this.repository.deleteById(id);
  }

  public List<DadosListagemDepoimento> listarTresDepoimentosAleatorios() {
    List<Depoimento> depoimentos = repository.encontrarTresDepoimentosAleatoriamente();
    return depoimentos.stream().map(
        DadosListagemDepoimento::new).toList();
  }

  public DepoimentoDTO atualizar(String dados) {
    DepoimentoDTO depoimento;
    try {
      depoimento = objectMapper.readValue(dados, DepoimentoDTO.class);
      Depoimento depoimentoAAtualizar = repository.getReferenceById(depoimento.id());

      depoimentoAAtualizar.atualizarInformacoes(depoimento);
      repository.save(depoimentoAAtualizar);
      return new DepoimentoDTO(depoimentoAAtualizar);
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    } catch (IllegalStateException e) {
      e.printStackTrace();
    }

    return null;
  }

}
