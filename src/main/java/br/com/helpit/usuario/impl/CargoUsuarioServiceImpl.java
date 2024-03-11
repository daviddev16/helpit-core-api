package br.com.helpit.usuario.impl;

import br.com.helpit.usuario.CargoUsuario;
import br.com.helpit.usuario.Usuario;
import br.com.helpit.usuario.enums.CargoInterno;
import br.com.helpit.usuario.repository.CargoUsuarioRepository;
import br.com.helpit.usuario.service.CargoUsuarioService;
import org.springframework.stereotype.Service;

@Service
public class CargoUsuarioServiceImpl implements CargoUsuarioService {

    private final CargoUsuarioRepository cargoUsuarioRepository;

    public CargoUsuarioServiceImpl(CargoUsuarioRepository cargoUsuarioRepository) {
        this.cargoUsuarioRepository = cargoUsuarioRepository;
    }

    @Override
    public CargoUsuario associarCargoAUsuario(CargoInterno cargoInterno, Usuario usuario) {
        CargoUsuario cargoUsuario = new CargoUsuario();
        cargoUsuario.setUsuario(usuario);
        cargoUsuario.setCargoInterno(cargoInterno);
        return cargoUsuarioRepository.save(cargoUsuario);
    }
}
