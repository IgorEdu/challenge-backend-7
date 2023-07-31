package br.com.jornada.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.com.jornada.api.domain.Depoimento;
import br.com.jornada.api.domain.DepoimentoRepository;
import br.com.jornada.api.service.DepoimentoService;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class DepoimentoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @SpyBean
  private DepoimentoService service;

  @MockBean
  private DepoimentoRepository repository;

  @DisplayName("Deve cadastrar com sucesso se dados informados validos")
  @Test
  public void testCenario1() throws Exception {
    // Arrange
    when(repository.save(any(Depoimento.class))).thenReturn(
    new Depoimento(
      "Nome de depoente",
      "Meu depoimento",
      "https://www.imagemdodepoente.com"
      )
    );
    // Act
    this.mockMvc.perform(
      post( "/depoimentos")
      .contentType(MediaType.APPLICATION_JSON)
      .content(
        "{\"nome\": \"Nome de depoente\", " +
        "\"depoimento\": \"Meu depoimento\"," +
        " \"nomeImagem\": \"https://www.imagemdodepoente.com\"}" )
    )
    // Assert           
    .andExpect(status().isCreated());
  }

  @DisplayName("Não deve permitir requisição sem informar depoente")
  @Test
  public void testCenario2() throws Exception {
    // Act
    this.mockMvc.perform(
        post("/depoimentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\"depoimento\": \"Meu depoimento\"," +
                    " \"nomeImagem\": \"https://www.minhaimagem.com\"}"))
        // Assert
        .andExpect(status().isBadRequest());
  }

  @DisplayName("Não deve permitir requisição sem informar depoimento")
  @Test
  public void testCenario3() throws Exception {
    // Act
    this.mockMvc.perform(
        post("/depoimentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\"nome\": \"Meu nome\"," +
                    " \"nomeImagem\": \"https://www.minhaimagem.com\"}"))
        // Assert
        .andExpect(status().isBadRequest());
  }

}
