package br.com.jornada.api.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DepoimentoRepository extends JpaRepository<Depoimento, Long> {

  Page<Depoimento> findAllByAtivoTrue(Pageable paginacao);

  @Query(value = """
      select d from Depoimento d
      where
      d.ativo = true
      order by rand()
      limit 3""")
  List<Depoimento> encontrarTresDepoimentosAleatoriamente();
}
