package br.com.jornada.api.domain.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonAlias;

import br.com.jornada.api.domain.Destino;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastroDestinoDTO(
        @NotBlank(message = "campo 'nome' obrigatório") @Size(min = 1, max = 120) String nome,

        @NotBlank(message = "campo 'preco' obrigatório") @Size(min = 1, max = 500) BigDecimal preco,

        @JsonAlias(value = "url_imagem") @Size(min = 1, max = 255) String url_imagem) {

    public Destino toModel() {
        return new Destino(nome(), preco(), url_imagem());
    }
}
