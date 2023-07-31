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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.jornada.api.domain.DestinoRepository;
import br.com.jornada.api.domain.dto.CadastroDestinoDTO;
import br.com.jornada.api.domain.dto.DestinoDTO;
import br.com.jornada.api.service.DestinoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/destinos")
public class DestinoController {

  @Autowired
  private DestinoRepository repository;

  @Autowired
  private DestinoService service;

  @GetMapping
  public ResponseEntity<Page<Object>> listar(@PageableDefault(size = 6) Pageable paginacao) {
    return ResponseEntity.ok(repository.findAllByAtivoTrue(paginacao).map(DestinoDTO::new));
  }

  @PostMapping
  @Transactional
  public ResponseEntity<DestinoDTO> cadastrar(@RequestBody @Valid CadastroDestinoDTO dados,
      UriComponentsBuilder uriComponentsBuilder) {

    DestinoDTO destino = service.salvar(dados);
    var uri = uriComponentsBuilder.path("/destino/{id}").buildAndExpand(destino.id()).toUri();
    return ResponseEntity.created(uri).body(destino);
  }

  @PutMapping("/{id}")
  @Transactional
  public ResponseEntity<DestinoDTO> atualizar(@PathVariable Long id, DestinoDTO dados) {
    DestinoDTO dtoResposta = service.atualizar(dados);
    return ResponseEntity.ok(dtoResposta);
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<Object> excluir(@PathVariable Long id) {
    var destino = repository.getReferenceById(id);
    destino.excluir();

    return ResponseEntity.noContent().build();
  }
}
