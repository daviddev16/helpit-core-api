package br.com.helpit.artigo.repository;

import br.com.helpit.artigo.ArtigoTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ArtigoTagRepository extends JpaRepository<ArtigoTag, Long> {

    @Query(value = "SELECT at FROM ArtigoTag at WHERE at.artigo.id = :idArtigo")
    Set<ArtigoTag> findAllByIdArtigo(Long idArtigo);

}
