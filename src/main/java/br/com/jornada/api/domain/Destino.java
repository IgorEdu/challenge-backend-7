package br.com.jornada.api.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.jornada.api.domain.dto.CadastroDestinoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "destinos")
@Entity(name = "Destino")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Destino {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  private Boolean ativo;

  @Column(nullable = false)
  @Setter
  @Size(min = 1, max = 120, message = "campo 'nome' deve ter de 1 a 120 caracteres")
  String nome;

  @Column(name = "url_imagem", nullable = true)
  @Setter
  @Size(min = 1, max = 255, message = "campo 'url_imagem' deve ter de 1 a 255 caracteres")
  String url_imagem;

  @Column(nullable = false)
  @Setter
  BigDecimal preco;

  @CreationTimestamp
  @Column(name = "created_at")
  private Date createdOn;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private Date lastUpdatedOn;

  public Destino(CadastroDestinoDTO cadastroDestinoDTO) {
    this.ativo = true;
    this.nome = cadastroDestinoDTO.nome();
    this.preco = cadastroDestinoDTO.preco();
    this.url_imagem = cadastroDestinoDTO.url_imagem();
  }

  public void excluir() {
    this.ativo = false;
  }

  public Destino(@NotBlank(message = "campo 'nome' obrigatório") @Size(min = 1, max = 120) String nome,
      @NotBlank(message = "campo 'preco' obrigatório") @Size(min = 1, max = 500) BigDecimal preco,
      @Size(min = 1, max = 255) String url_imagem) {
  }
}
