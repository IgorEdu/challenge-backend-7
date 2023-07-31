package br.com.jornada.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.jornada.api.domain.DepoimentoRepository;
import br.com.jornada.api.domain.dto.DadosListagemDepoimento;
import br.com.jornada.api.domain.dto.DepoimentoDTO;
import br.com.jornada.api.service.DepoimentoService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/depoimentos")
public class DepoimentoController {

  @Autowired
  private DepoimentoRepository repository;

  @Autowired
  private DepoimentoService service;

  @GetMapping
  public ResponseEntity<Page<DadosListagemDepoimento>> listar(@PageableDefault(size = 10) Pageable paginacao) {
    return ResponseEntity.ok(repository.findAllByAtivoTrue(paginacao).map(DadosListagemDepoimento::new));
  }

  @GetMapping("/{id}")
  public ResponseEntity<DadosListagemDepoimento> detalhar(
      @PathVariable Long id) {
    DadosListagemDepoimento dados = service.detalhar(id);
    return ResponseEntity.ok(dados);

  }

  @PostMapping
  @Transactional
  public ResponseEntity<DepoimentoDTO> cadastrar(@RequestParam("dados") String dados,
      UriComponentsBuilder uriComponentsBuilder) {

    DepoimentoDTO depoimento = service.salvar(dados);
    var uri = uriComponentsBuilder.path("/depoimentos/{id}").buildAndExpand(depoimento.id()).toUri();
    return ResponseEntity.created(uri).body(depoimento);
  }

  @PutMapping
  @Transactional
  public ResponseEntity<DepoimentoDTO> atualizar(@RequestParam("dados") String dados) {
    DepoimentoDTO dtoResposta = service.atualizar(dados);
    return ResponseEntity.ok(dtoResposta);
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<Object> excluir(@PathVariable Long id) {
    var depoimento = repository.getReferenceById(id);
    depoimento.excluir();

    return ResponseEntity.noContent().build();
  }
}
