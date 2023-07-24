package br.com.jornada.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jornada.api.domain.dto.DadosListagemDepoimento;
import br.com.jornada.api.service.DepoimentoService;

@RestController
@RequestMapping("/depoimentos-home")
public class RandomDepoimentoController {

  @Autowired
  private DepoimentoService service;

  @GetMapping
  public ResponseEntity<List<DadosListagemDepoimento>> listar() {

    List<DadosListagemDepoimento> depoimentosHome = service.listarTresDepoimentosAleatorios();
    return ResponseEntity.ok(depoimentosHome);
  }
}