package br.com.helpit.empresa.impl;

import br.com.helpit.core.AuthenticatedService;
import br.com.helpit.empresa.CargoEmpresa;
import br.com.helpit.empresa.Empresa;
import br.com.helpit.empresa.UsuarioEmpresa;
import br.com.helpit.empresa.repository.UsuarioEmpresaRepository;
import br.com.helpit.empresa.service.UsuarioEmpresaService;
import br.com.helpit.usuario.Usuario;
import br.com.helpit.usuario.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioEmpresaServiceImpl implements UsuarioEmpresaService, AuthenticatedService {

    private final UsuarioService usuarioService;
    private final UsuarioEmpresaRepository usuarioEmpresaRepository;

    public UsuarioEmpresaServiceImpl(UsuarioService usuarioService,
                                     UsuarioEmpresaRepository usuarioEmpresaRepository) {

        this.usuarioService = usuarioService;
        this.usuarioEmpresaRepository = usuarioEmpresaRepository;

    }

    @Override
    public UsuarioEmpresa criarUsuarioEmpresa(Usuario usuario, Empresa empresa, CargoEmpresa cargoEmpresa) {

        UsuarioEmpresa usuarioEmpresa = UsuarioEmpresa
                .builder()
                    .cargoEmpresa(cargoEmpresa)
                    .usuario(usuario)
                    .empresa(empresa)
                .build();

        return usuarioEmpresaRepository.save(usuarioEmpresa);
    }

    @Override
    public Set<Empresa> obterEmpresasDeUsuarioAutenticado() {

        final Authentication authentication = getAuthentication();

        Usuario usuarioAutenticado = usuarioService
                .obterUsuarioPorLoginOuEmail(authentication.getName());

        return obterEmpresasDeUsuario(usuarioAutenticado);
    }

    @Override
    public Set<Empresa> obterEmpresasDeUsuario(Usuario usuario) {
        return usuarioEmpresaRepository
                .findAllUsuarioEmpresaByUsuarioId(usuario.getId())
                    .stream()
                        .map(UsuarioEmpresa::getEmpresa)
                        .collect(Collectors.toSet());
    }

    @Override
    public Integer obterQuantidadeDeEmpresasDeUsuario(Usuario usuario) {
        return usuarioEmpresaRepository
                .countEmpresasByUsuarioId(usuario.getId());
    }

    @Override
    public boolean verificarAutorizacaoUsuarioEmpresa(Usuario usuario, Empresa empresa) {
        Boolean contemUsuarioNaEmpresa = usuarioEmpresaRepository
                .existsUsuarioEmpresaWith(usuario, empresa);

        return contemUsuarioNaEmpresa != null && contemUsuarioNaEmpresa;
    }

}
