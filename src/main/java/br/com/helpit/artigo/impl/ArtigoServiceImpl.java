package br.com.helpit.artigo.impl;

import br.com.helpit.artigo.Artigo;
import br.com.helpit.artigo.dto.request.RequestRegistrarArtigoDTO;
import br.com.helpit.artigo.repository.ArtigoRepository;
import br.com.helpit.artigo.service.ArtigoService;
import br.com.helpit.artigo.transformer.RequestArtigoTransformer;
import br.com.helpit.core.AuthenticatedService;
import br.com.helpit.core.ServiceException;
import br.com.helpit.empresa.Empresa;
import br.com.helpit.empresa.service.EmpresaService;
import br.com.helpit.empresa.service.UsuarioEmpresaService;
import br.com.helpit.usuario.Usuario;
import br.com.helpit.usuario.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArtigoServiceImpl implements ArtigoService, AuthenticatedService {

    private final ArtigoRepository artigoRepository;

    private final EmpresaService empresaService;

    private final UsuarioEmpresaService usuarioEmpresaService;
    private final UsuarioService usuarioService;

    private final RequestArtigoTransformer requestArtigoTransformer;

    public ArtigoServiceImpl(ArtigoRepository artigoRepository,
                             EmpresaService empresaService,
                             UsuarioEmpresaService usuarioEmpresaService,
                             UsuarioService usuarioService,
                             RequestArtigoTransformer requestArtigoTransformer) {

        this.artigoRepository = artigoRepository;
        this.empresaService = empresaService;
        this.usuarioEmpresaService = usuarioEmpresaService;
        this.usuarioService = usuarioService;
        this.requestArtigoTransformer = requestArtigoTransformer;
    }

    @Override
    @Transactional
    public Artigo criarArtigo(RequestRegistrarArtigoDTO registrarArtigoDTO, Long empresaId) {

        final Authentication authentication = getAuthentication();

        Usuario usuarioAutenticado = usuarioService
                .obterUsuarioPorLoginOuEmail(authentication.getName());

        Empresa empresa = empresaService.obterEmpresaPorId(empresaId);

        if (!usuarioEmpresaService.verificarAutorizacaoUsuarioEmpresa(usuarioAutenticado, empresa))
            throw new ServiceException(String.format("%s não possui acesso a empresa %d.",
                    usuarioAutenticado.getLogin(), empresaId));

        final Artigo novoArtigo = requestArtigoTransformer
                .transformarRegistrarArtigoDTOEmArtigo(registrarArtigoDTO);

        novoArtigo.setUsuario(usuarioAutenticado);
        novoArtigo.setEmpresa(empresa);

        return artigoRepository.save(novoArtigo);
    }
}
