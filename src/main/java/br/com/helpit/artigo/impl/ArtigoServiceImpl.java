package br.com.helpit.artigo.impl;

import br.com.helpit.artigo.Artigo;
import br.com.helpit.artigo.dto.request.RegistrarArtigoRequestDTO;
import br.com.helpit.artigo.repository.ArtigoRepository;
import br.com.helpit.artigo.service.ArtigoService;
import br.com.helpit.artigo.transformer.ArtigoRequestTransformer;
import br.com.helpit.core.AuthenticatedService;
import br.com.helpit.core.ServiceException;
import br.com.helpit.empresa.Empresa;
import br.com.helpit.empresa.service.EmpresaService;
import br.com.helpit.empresa.service.UsuarioEmpresaService;
import br.com.helpit.usuario.Usuario;
import br.com.helpit.usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArtigoServiceImpl implements ArtigoService, AuthenticatedService {

    private @Autowired ArtigoRepository artigoRepository;

    private @Autowired EmpresaService        empresaService;
    private @Autowired UsuarioEmpresaService usuarioEmpresaService;
    private @Autowired UsuarioService        usuarioService;

    private @Autowired ArtigoRequestTransformer artigoRequestTransformer;

    @Override
    @Transactional
    public Artigo criarArtigo(RegistrarArtigoRequestDTO registrarArtigoDTO, Long idEmpresa) {

        final Authentication authentication = getAuthentication();

        Usuario usuarioAutenticado = usuarioService
                .obterUsuarioPorLoginOuEmail(authentication.getName());

        Empresa empresa = empresaService.obterEmpresaPorId(idEmpresa);

        if (!usuarioEmpresaService.verificarAutorizacaoUsuarioEmpresa(usuarioAutenticado, empresa))
            throw new ServiceException(String.format("%s não possui acesso a empresa %d.",
                    usuarioAutenticado.getLogin(), idEmpresa));

        final Artigo novoArtigo = artigoRequestTransformer
                .transformarArtigoRequestEmArtigo(registrarArtigoDTO);

        novoArtigo.setUsuario(usuarioAutenticado);
        novoArtigo.setEmpresa(empresa);

        return artigoRepository.save(novoArtigo);
    }

    @Override
    public List<Artigo> obterArtigos() {
        return null;
    }
}
