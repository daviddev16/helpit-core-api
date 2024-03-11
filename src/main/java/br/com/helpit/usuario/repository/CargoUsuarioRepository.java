package br.com.helpit.usuario.repository;

import br.com.helpit.usuario.CargoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoUsuarioRepository extends JpaRepository<CargoUsuario, Integer> { }
