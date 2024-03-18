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
            value = "SELECT count(DISTINCT idempresa) FROM helpit.empusuario WHERE idusuario = :paramIdUsuario"
    )
    Integer countEmpresasByUsuarioId(@Param("paramIdUsuario") Long idUsuario);


    @Query(
            value = "SELECT usEmp FROM UsuarioEmpresa usEmp WHERE usEmp.usuario.id = :paramUsuarioId"
    )
    List<UsuarioEmpresa> findAllUsuarioEmpresaByUsuarioId(@Param("paramUsuarioId") Long idUsuario);


    @Query(
            value = "SELECT true FROM UsuarioEmpresa usEmp WHERE usEmp.usuario.id = :paramIdUsuario AND usEmp.empresa.id = :paramIdEmpresa"
    )
    Boolean existsUsuarioEmpresaWith(@Param("paramIdUsuario") Long idUsuario,
                                     @Param("paramIdEmpresa") Long idEmpresa);


    default Boolean existsUsuarioEmpresaWith(Usuario usuario, Empresa empresa) {
        final Long empresaId = empresa.getId();
        final Long usuarioId = usuario.getId();

        return existsUsuarioEmpresaWith(usuarioId, empresaId);
    }


}
