package br.com.helpit.usuario.service;

import br.com.helpit.usuario.CargoUsuario;
import br.com.helpit.usuario.Usuario;
import br.com.helpit.usuario.enums.CargoInterno;

public interface CargoUsuarioService {

    CargoUsuario associarCargoAUsuario( CargoInterno cargoInterno, Usuario usuario );

}
