package br.com.helpit.artigo.impl;

import br.com.helpit.artigo.Artigo;
import br.com.helpit.artigo.ArtigoTag;
import br.com.helpit.artigo.Tag;
import br.com.helpit.artigo.dto.request.AdicionarTagRequestDTO;
import br.com.helpit.artigo.dto.request.RegistrarArtigoRequestDTO;
import br.com.helpit.artigo.exception.ArtigoNaoEncontradoException;
import br.com.helpit.artigo.repository.ArtigoRepository;
import br.com.helpit.artigo.repository.ArtigoTagRepository;
import br.com.helpit.artigo.service.ArtigoService;
import br.com.helpit.artigo.service.TagService;
import br.com.helpit.artigo.transformer.ArtigoRequestTransformer;
import br.com.helpit.core.AuthenticatedService;
import br.com.helpit.core.ServiceException;
import br.com.helpit.empresa.Empresa;
import br.com.helpit.empresa.service.EmpresaService;
import br.com.helpit.empresa.service.UsuarioEmpresaService;
import br.com.helpit.global.annotation.WithAutheticationFacade;
import br.com.helpit.usuario.Usuario;
import br.com.helpit.usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArtigoServiceImpl implements ArtigoService, AuthenticatedService {

    private @Autowired ArtigoRepository    artigoRepository;
    private @Autowired ArtigoTagRepository artigoTagRepository;

    private @Autowired EmpresaService        empresaService;
    private @Autowired UsuarioEmpresaService usuarioEmpresaService;
    private @Autowired UsuarioService        usuarioService;
    private @Autowired TagService            tagService;

    private @Autowired ArtigoRequestTransformer artigoRequestTransformer;

    @Override
    @Transactional
    public Artigo criarArtigo(RegistrarArtigoRequestDTO registrarArtigoDTO, Long idEmpresa) {

        Empresa empresa = empresaService.obterEmpresaPorId(idEmpresa);
        Usuario usuarioAutenticado = obterUsuarioAutenticadoValidadoPorEmpresa(empresa);

        Artigo novoArtigo = artigoRequestTransformer
                .transformarArtigoRequestEmArtigo(registrarArtigoDTO);

        novoArtigo.setUsuario(usuarioAutenticado);
        novoArtigo.setEmpresa(empresa);

        return artigoRepository.save(novoArtigo);
    }

    @Override
    @Transactional
    public Artigo adicionarTagsAoArtigo(List<AdicionarTagRequestDTO> adicionarTagRequestDTOs, Long idEmpresa, Long idArtigo) {

        Empresa empresa = empresaService.obterEmpresaPorId(idEmpresa);
        Usuario usuarioAutenticado = obterUsuarioAutenticadoValidadoPorEmpresa(empresa);

        Artigo artigo = obterArtigoPorId(idArtigo);

        if (artigo.getEmpresa().getId() != idEmpresa) {
            throw new ServiceException(String.format("Para a empresa \"%d\", não" +
                    " existe um artigo com id \"%d\".", idEmpresa, idArtigo));
        }

        List<Tag> tags = adicionarTagRequestDTOs.stream()
                .map(adicionarTagRequestDTO -> tagService
                        .obterTagPorNome(adicionarTagRequestDTO.nomeTag(), empresa.getId()))
                .toList();

        tags.stream()
                .filter(tag ->  !verificarArtigoTagExistentePorTag(artigo.getArtigoTags(), tag))
                .forEach(tag ->
                {
                    ArtigoTag artigoTag = new ArtigoTag();
                    artigoTag.setTag(tag);
                    artigoTag.setArtigo(artigo);

                    artigoTag = artigoTagRepository.save(artigoTag);

                    artigo.getArtigoTags().add(artigoTag);
                });

        return artigoRepository.save(artigo);

    }

    private boolean verificarArtigoTagExistentePorTag(Set<ArtigoTag> artigosTags, Tag tag) {
        return artigosTags.stream()
                .anyMatch(artigoTag -> artigoTag.getTag().equals(tag));
    }

    @WithAutheticationFacade
    private Usuario obterUsuarioAutenticadoValidadoPorEmpresa(Empresa empresa) {

        final Authentication authentication = getAuthentication();

        Usuario usuarioAutenticado = usuarioService
                .obterUsuarioPorLoginOuEmail(authentication.getName());

        if (!usuarioEmpresaService.verificarAutorizacaoUsuarioEmpresa(usuarioAutenticado, empresa))
            throw new ServiceException(String.format("%s não possui acesso a empresa %d.",
                    usuarioAutenticado.getLogin(), empresa.getId()));

        return usuarioAutenticado;

    }

    @Override
    public Artigo obterArtigoPorId(Long idArtigo) {
        return artigoRepository
                .findById(idArtigo)
                .orElseThrow(() -> new ArtigoNaoEncontradoException(idArtigo));
    }

    @Override
    public List<Artigo> obterArtigos() {
        return null;
    }
}
