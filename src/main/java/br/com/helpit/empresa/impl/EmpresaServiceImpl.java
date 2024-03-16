package br.com.helpit.empresa.impl;

import br.com.helpit.core.AuthenticatedService;
import br.com.helpit.empresa.CargoEmpresa;
import br.com.helpit.empresa.Empresa;
import br.com.helpit.empresa.UsuarioEmpresa;
import br.com.helpit.empresa.dto.request.RegistrarEmpresaRequestDTO;
import br.com.helpit.empresa.exception.EmpresaNaoEncontradaException;
import br.com.helpit.empresa.exception.UsuarioNaoElegivelException;
import br.com.helpit.empresa.repository.EmpresaRepository;
import br.com.helpit.empresa.service.EmpresaService;
import br.com.helpit.empresa.service.UsuarioEmpresaService;
import br.com.helpit.empresa.transformer.RequestEmpresaTransfomer;
import br.com.helpit.global.AplicacaoSettings;
import br.com.helpit.usuario.Usuario;
import br.com.helpit.usuario.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.String.*;

@Service
public class EmpresaServiceImpl implements EmpresaService, AuthenticatedService {

    private final AplicacaoSettings aplicacaoSettings;
    private final EmpresaRepository empresaRepository;
    private final RequestEmpresaTransfomer requestEmpresaTransfomer;
    private final UsuarioEmpresaService usuarioEmpresaService;
    private final UsuarioService usuarioService;

    public EmpresaServiceImpl(AplicacaoSettings aplicacaoSettings,
                              EmpresaRepository empresaRepository,
                              RequestEmpresaTransfomer requestEmpresaTransfomer,
                              UsuarioEmpresaService usuarioEmpresaService,
                              UsuarioService usuarioService)
    {
        this.aplicacaoSettings = aplicacaoSettings;
        this.empresaRepository = empresaRepository;
        this.requestEmpresaTransfomer = requestEmpresaTransfomer;
        this.usuarioEmpresaService = usuarioEmpresaService;
        this.usuarioService = usuarioService;
    }

    @Override
    @Transactional
    public Empresa criarEmpresa(RegistrarEmpresaRequestDTO registrarEmpresaDTO) {

        final Authentication authentication = getAuthentication();

        Usuario usuarioAutenticado = usuarioService
                .obterUsuarioPorLoginOuEmail(authentication.getName());

        verificarEligibilidadeNovaEmpresaDe(usuarioAutenticado);

        Empresa novaEmpresa = requestEmpresaTransfomer
                .transformarRegistrarEmpresaDTOEmEmpresa(registrarEmpresaDTO);

        novaEmpresa = empresaRepository.save(novaEmpresa);

        UsuarioEmpresa donoUsuarioEmpresa = usuarioEmpresaService
                .criarUsuarioEmpresa(usuarioAutenticado, novaEmpresa, CargoEmpresa.DONO);

        novaEmpresa.setUsuariosEmpresa(List.of(donoUsuarioEmpresa));

        novaEmpresa.setArtigos(Collections.emptyList());

        return novaEmpresa;
    }

    private void verificarEligibilidadeNovaEmpresaDe(Usuario usuario) {

        Integer quantidadeDeEmpresasDoUsuario = usuarioEmpresaService
                .obterQuantidadeDeEmpresasDeUsuario(usuario);

        Integer numeroMaximoDeEmpresas = aplicacaoSettings
                .obterNumeroMaximoDeEmpresaPara(usuario);

        if (quantidadeDeEmpresasDoUsuario >= numeroMaximoDeEmpresas)
            throw new UsuarioNaoElegivelException(format("Usuário não habilitado " +
                    "para criar mais de %d empresas.", numeroMaximoDeEmpresas));

    }

    @Override
    public Empresa obterEmpresaPorId(Long empresaId) {
        return empresaRepository
                .findById(empresaId)
                .orElseThrow(() -> new EmpresaNaoEncontradaException(empresaId));
    }

}
