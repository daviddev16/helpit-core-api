package br.com.helpit.usuario.repository;

import br.com.helpit.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT DISTINCT u FROM Usuario u WHERE u.email = :paramPassaporte OR u.login = :paramPassaporte ")
    Optional<Usuario> findByLoginOrEmail( @Param("paramPassaporte") String passaporte );

}
