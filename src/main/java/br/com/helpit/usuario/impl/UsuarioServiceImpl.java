package br.com.helpit.usuario.impl;

import br.com.helpit.security.PasswordConfiguration;
import br.com.helpit.usuario.CargoUsuario;
import br.com.helpit.usuario.Usuario;
import br.com.helpit.usuario.dto.request.RegistrarUsuarioRequestDTO;
import br.com.helpit.usuario.enums.CargoInterno;
import br.com.helpit.usuario.enums.StatusAtividade;
import br.com.helpit.usuario.exception.PassaporteNaoEncontradoException;
import br.com.helpit.usuario.repository.UsuarioRepository;
import br.com.helpit.usuario.service.CargoUsuarioService;
import br.com.helpit.usuario.service.UsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final CargoUsuarioService cargoUsuarioService;

    private final PasswordConfiguration passwordConfiguration;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                              CargoUsuarioService cargoUsuarioService,
                              PasswordConfiguration passwordConfiguration)
    {
        this.usuarioRepository = usuarioRepository;
        this.cargoUsuarioService = cargoUsuarioService;
        this.passwordConfiguration = passwordConfiguration;
    }

    @Override
    @Transactional
    public Usuario registrarUsuario(RegistrarUsuarioRequestDTO registrarUsuarioDTO) {

        Usuario novoUsuario = Usuario
                .builder()
                    .login(registrarUsuarioDTO.login())
                    .email(registrarUsuarioDTO.email())
                    .dataCriacao(LocalDateTime.now())
                    .statusAtividade(StatusAtividade.AGUARDANDO_CONFIRMACAO)
                    .senha(passwordConfiguration
                            .getEnconder()
                            .encode(registrarUsuarioDTO.senha()))
                .build();

        novoUsuario = usuarioRepository.save(novoUsuario);

        CargoUsuario novoCargoUsuario = cargoUsuarioService
                .associarCargoAUsuario(CargoInterno.USUARIO, novoUsuario);

        novoUsuario.setCargosInternos(List.of(novoCargoUsuario));

        return novoUsuario;

    }

    @Override
    public Usuario obterUsuarioPorLoginOuEmail(String passaporte) {
        return usuarioRepository
                .findByLoginOrEmail(passaporte)
                .orElseThrow(() -> new PassaporteNaoEncontradoException(passaporte));
    }

    @Override
    public Usuario atualizarStatusAtividade(Usuario usuario, StatusAtividade novoStatusAtividade) {
        usuario.setStatusAtividade(novoStatusAtividade);
        return usuarioRepository.save(usuario);
    }

}
