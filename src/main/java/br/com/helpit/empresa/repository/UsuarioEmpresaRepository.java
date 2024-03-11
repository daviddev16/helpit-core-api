package br.com.helpit.empresa.repository;

import br.com.helpit.empresa.Empresa;
import br.com.helpit.empresa.UsuarioEmpresa;
import br.com.helpit.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioEmpresaRepository extends JpaRepository<UsuarioEmpresa, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT count(DISTINCT idempresa) FROM helpit.empusuario WHERE idusuario = :paramUsuarioId"
    )
    Integer countEmpresasByUsuarioId(@Param("paramUsuarioId") Long usuarioId);


    @Query(
            value = "SELECT usEmp FROM UsuarioEmpresa usEmp WHERE usEmp.usuario.id = :paramUsuarioId"
    )
    List<UsuarioEmpresa> findAllUsuarioEmpresaByUsuarioId(@Param("paramUsuarioId") Long usuarioId);


    @Query(
            value = "SELECT true FROM UsuarioEmpresa usEmp WHERE usEmp.usuario.id = :paramUsuarioId AND usEmp.empresa.id = :paramEmpresaId"
    )
    Boolean existsUsuarioEmpresaWith(@Param("paramUsuarioId") Long usuarioId,
                                     @Param("paramEmpresaId") Long empresaId);


    default Boolean existsUsuarioEmpresaWith(Usuario usuario, Empresa empresa) {
        final Long empresaId = empresa.getId();
        final Long usuarioId = usuario.getId();
        return existsUsuarioEmpresaWith(usuarioId, empresaId);
    }


}
