package br.com.helpit.artigo.repository;

import br.com.helpit.artigo.Artigo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtigoRepository extends JpaRepository<Artigo, Long> { }
