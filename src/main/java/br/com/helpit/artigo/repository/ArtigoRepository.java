package br.com.helpit.artigo.repository;

import br.com.helpit.artigo.Artigo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtigoRepository extends JpaRepository<Artigo, Long> { }
