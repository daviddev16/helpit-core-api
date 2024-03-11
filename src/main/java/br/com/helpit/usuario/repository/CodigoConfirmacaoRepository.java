package br.com.helpit.usuario.repository;

import br.com.helpit.usuario.CodigoConfirmacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodigoConfirmacaoRepository extends JpaRepository<CodigoConfirmacao, Integer> {

    Optional<CodigoConfirmacao> findByCodigoConfirmacao(String codigoConfirmacao);

}
