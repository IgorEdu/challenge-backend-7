package br.com.jornada.api.domain;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.jornada.api.domain.dto.DepoimentoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
  @Setter
  private String nome;
  @Setter
  private String depoimento;

  @Column(name = "nome_imagem")
  @Setter
  private String nomeImagem;

  private Boolean ativo;

  @CreationTimestamp
  @Column(name = "created_at")
  private Date createdOn;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private Date lastUpdatedOn;

  public Depoimento(DepoimentoDTO dados) {
    this.ativo = true;
    this.nome = dados.nome();
    this.depoimento = dados.depoimento();
    this.nomeImagem = dados.nomeImagem();
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
