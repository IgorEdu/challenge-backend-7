package br.com.jornada.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.jornada.api.domain.Depoimento;
import br.com.jornada.api.domain.DepoimentoRepository;
import br.com.jornada.api.domain.dto.DepoimentoDTO;
import br.com.jornada.api.domain.dto.DadosListagemDepoimento;

@Service
public class DepoimentoService {

  @Autowired
  private DepoimentoRepository repository;

  @Autowired
  private FileStorageService fileStorageService;

  @Autowired
  private ObjectMapper objectMapper;

  public DepoimentoDTO salvar(String dados, MultipartFile arquivo) {
    DepoimentoDTO depoimento;
    try {
      depoimento = objectMapper.readValue(dados, DepoimentoDTO.class);
      String fileName = fileStorageService.storeFile(arquivo, depoimento.nome());
      ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();

      Depoimento dadosSalvo = repository.save(new Depoimento(depoimento, fileName));
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

  public DepoimentoDTO atualizar(String dados, MultipartFile arquivo) {
    DepoimentoDTO depoimento;
    try {
      depoimento = objectMapper.readValue(dados, DepoimentoDTO.class);
      String fileName = fileStorageService.storeFile(arquivo, depoimento.nome());
      ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
      Depoimento depoimentoAAtualizar = repository.getReferenceById(depoimento.id());

      depoimentoAAtualizar.atualizarInformacoes(depoimento, fileName);
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
