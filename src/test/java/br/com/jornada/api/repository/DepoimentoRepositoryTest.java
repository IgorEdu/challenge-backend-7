package br.com.jornada.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import br.com.jornada.api.domain.Depoimento;
import br.com.jornada.api.domain.DepoimentoRepository;
import br.com.jornada.api.domain.dto.CadastroDepoimentoDTO;
import jakarta.validation.ConstraintViolationException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class DepoimentoRepositoryTest {

  @Autowired
  private DepoimentoRepository repository;

  @Autowired
  private TestEntityManager em;

  @DisplayName("Se não houver depoimentos retorna lista vazia")
  @Test
  public void testCenario1() {
    // Arrange

    // Act
    List<Depoimento> depoimentos = repository.encontrarTresDepoimentosAleatoriamente();

    // Assert
    assertThat(depoimentos.size()).isEqualTo(0);
  }

  @Test
  public void testCenario2() {
    // Arrange
    Depoimento depoimentoUnico = cadastrarDepoiemento("José", "Depoimento do José", "http://www.fotodojose.com");
    // Act
    List<Depoimento> depoimentos = repository.encontrarTresDepoimentosAleatoriamente();
    // Assert
    assertThat(depoimentos.size()).isEqualTo(1);
    assertThat(depoimentos.contains(depoimentoUnico));
  }

  @DisplayName("Se possui 4 depoimentos retorna só 3")
  @Test
  public void testCenario3() {
    // Arrange
    var user1 = cadastrarDepoiemento("Usuário 1", "Meu Depoimento 1", "http://www.foto1.com");
    var user2 = cadastrarDepoiemento("Usuário 2", "Meu Depoimento 2", "http://www.foto2.com");
    var user3 = cadastrarDepoiemento("Usuário 3", "Meu Depoimento 3", "http://www.foto3.com");
    var user4 = cadastrarDepoiemento("Usuário 4", "Meu Depoimento 4", "http://www.foto4.com");

    // Act
    List<Depoimento> depoimentosCadastrados = new ArrayList<>();
    depoimentosCadastrados.add(user1);
    depoimentosCadastrados.add(user2);
    depoimentosCadastrados.add(user3);
    depoimentosCadastrados.add(user4);

    List<Depoimento> depoimentos = repository.encontrarTresDepoimentosAleatoriamente();
    // Assert
    assertThat(depoimentos.size()).isEqualTo(3);
    assertThat(depoimentosCadastrados).containsAll(depoimentos);
  }

  @DisplayName("Não é possível salvar depoimento sem depoente")
  @Test
  public void testCenario4() {
    // Arrange / Act / Assert
    assertThatThrownBy(
        () -> repository.save(
            new Depoimento(
                null,
                "Meu Depoimento 1", "Minha Imagem")))
        .isInstanceOf(DataIntegrityViolationException.class);
  }

  @DisplayName("Não é possível salvar depoimento sem depoimento")
  @Test
  public void testCenario5() {
    // Arrange / Act / Assert
    assertThatThrownBy(
        () -> repository.save(
            new Depoimento(
                "Meu nome",
                null, "Minha Imagem")))
        .isInstanceOf(DataIntegrityViolationException.class);
  }

  @DisplayName("Não é possível salvar depoimento com depoente com mais de 120 caracteres")
  @Test
  public void testCenario6() {
    // Arrange / Act / Assert
    assertThatThrownBy(
        () -> repository.save(
            new Depoimento(
                "a".repeat(121),
                "b".repeat(500), "Minha Imagem")))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @DisplayName("Não é possível salvar depoimento com depoimento com mais de 500 caracteres")
  @Test
  public void testCenario7() {
    // Arrange / Act / Assert
    assertThatThrownBy(
        () -> repository.save(
            new Depoimento(
                "a".repeat(120),
                "b".repeat(501), "Minha Imagem")))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @DisplayName("Não é possível salvar depoimento com nome de depoente vazio")
  @Test
  public void testCenario8() {
    // Arrange / Act / Assert
    assertThatThrownBy(
        () -> repository.save(
            new Depoimento(
                "",
                "b".repeat(500), "Minha Imagem")))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @DisplayName("Não é possível salvar depoimento com depoimento vazio")
  @Test
  public void testCenario9() {
    // Arrange / Act / Assert
    assertThatThrownBy(
        () -> repository.save(
            new Depoimento(
                "a".repeat(120),
                "", "Minha Imagem")))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @DisplayName("Não é possível salvar url de foto com mais de 255 caracteres")
  @Test
  public void testCenario10() {
    // Arrange / Act / Assert
    assertThatThrownBy(
        () -> repository.save(
            new Depoimento(
                "a".repeat(120),
                "b".repeat(500), "c".repeat(256))))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @DisplayName("Não é possível salvar url de foto vazia")
  @Test
  public void testCenario11() {
    // Arrange / Act / Assert
    assertThatThrownBy(
        () -> repository.save(
            new Depoimento(
                "a".repeat(120),
                "b".repeat(500), "")))
        .isInstanceOf(ConstraintViolationException.class);
  }

  private Depoimento cadastrarDepoiemento(String nome, String depoimento, String nomeImagem) {
    var depoimentoCadastrar = new Depoimento(dadosDepoimento(nome, depoimento, nomeImagem));
    em.persist(depoimentoCadastrar);
    return depoimentoCadastrar;
  }

  private CadastroDepoimentoDTO dadosDepoimento(String nome, String depoimento, String nomeImagem) {
    return new CadastroDepoimentoDTO(nome, depoimento, nomeImagem);
  }

}
