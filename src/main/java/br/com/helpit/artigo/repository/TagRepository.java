package br.com.helpit.artigo.repository;

import br.com.helpit.artigo.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query(value = "SELECT t FROM Tag t WHERE t.nome = :paramNomeTag AND t.empresa.id = :paramIdEmpresa")
    Optional<Tag> findByNomeAndIdEmpresa(@Param("paramNomeTag") String nomeTag,
                                         @Param("paramIdEmpresa") Long idEmpresa);

}
