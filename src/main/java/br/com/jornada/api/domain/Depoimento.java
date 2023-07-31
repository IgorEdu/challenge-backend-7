package br.com.jornada.api.domain;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.jornada.api.domain.dto.CadastroDepoimentoDTO;
import br.com.jornada.api.domain.dto.DepoimentoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "depoimentos")
@Entity(name = "Depoimento")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Depoimento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @Size(min = 1, max = 120, message = "campo 'nome' deve ter de 1 a 120 caracteres")
  private String nome;

  @Column(nullable = false)
  @Setter
  @Size(min = 1, max = 500, message = "campo 'depoimento' deve ter de 1 a 500 caracteres")
  private String depoimento;

  @Column(name = "nome_imagem", nullable = true)
  @Setter
  @Size(min = 1, max = 255, message = "campo 'nome_imagem' deve ter de 1 a 255 caracteres")
  private String nomeImagem;

  private Boolean ativo;

  @CreationTimestamp
  @Column(name = "created_at")
  private Date createdOn;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private Date lastUpdatedOn;

  public Depoimento(CadastroDepoimentoDTO cadastroDepoimentoDTO) {
    this.ativo = true;
    this.nome = cadastroDepoimentoDTO.nome();
    this.depoimento = cadastroDepoimentoDTO.depoimento();
    this.nomeImagem = cadastroDepoimentoDTO.nomeImagem();
  }

  public void atualizarInformacoes(DepoimentoDTO dados) {
    if (dados.nome() != null) {
      this.nome = dados.nome();
    }
    if (dados.depoimento() != null) {
      this.depoimento = dados.depoimento();
    }
    if (dados.nomeImagem() != null) {
      this.nomeImagem = dados.nomeImagem();
    }
  }

  public void excluir() {
    this.ativo = false;
  }

  public Depoimento(String nome, String depoimento, String nomeImagem) {
    this.nome = nome;
    this.depoimento = depoimento;
    this.nomeImagem = nomeImagem;
  }

}
