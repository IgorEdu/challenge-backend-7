package br.com.jornada.api.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.com.jornada.api.domain.Depoimento;
import br.com.jornada.api.domain.DepoimentoRepository;
import br.com.jornada.api.service.DepoimentoService;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class RandomDepoimentoControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @SpyBean
        private DepoimentoService service;

        @MockBean
        private DepoimentoRepository repository;

  @DisplayName("Listagem de depoimentos para repositório preenchido")
  @Test
  public void testCenario1() throws Exception {
    //Arrange
    when(repository.encontrarTresDepoimentosAleatoriamente()).thenReturn(
      List.of(
        new Depoimento("Nome 1", "Depoimento 1", "imagem1"),
        new Depoimento("Nome 2", "Depoimento 2", "imagem2"),
        new Depoimento("Nome 3", "Depoimento 3", "imagem3")
      )
    );

    //Act
    this.mockMvc.perform(get("/depoimentos-home"))

    //Assert
    .andExpect(jsonPath("$",
            Matchers.hasSize(3)))
    .andExpect(jsonPath("$[0].nome",
            Matchers.is("Nome 1")))
    .andExpect(jsonPath("$[0].depoimento",
            Matchers.is("Depoimento 1")))
    .andExpect(jsonPath("$[0].nomeImagem",
            Matchers.is("imagem1")))
    .andExpect(jsonPath("$[1].nome",
            Matchers.is("Nome 2")))
    .andExpect(jsonPath("$[1].depoimento",
            Matchers.is("Depoimento 2")))
    .andExpect(jsonPath("$[1].nomeImagem",
            Matchers.is("imagem2")))
    .andExpect(jsonPath("$[2].nome",
            Matchers.is("Nome 3")))
    .andExpect(jsonPath("$[2].depoimento",
            Matchers.is("Depoimento 3")))
    .andExpect(jsonPath("$[2].nomeImagem",
          Matchers.is("imagem3")))
;
      
  }

  @DisplayName("Listagem de depoimentos para repositório vazio")
  @Test
  public void testCenario2() throws Exception {
    // Arrange
    when(repository.encontrarTresDepoimentosAleatoriamente()).thenReturn(
      List.of()
    );

    // Act
    this.mockMvc.perform(get("/depoimentos-home"))
    // Assert
    .andExpect(status().isOk())
    .andExpect(jsonPath("$",
      Matchers.hasSize(0)))
      ;
  }

}
