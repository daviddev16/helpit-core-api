package br.com.helpit.empresa.controller.v1;

import br.com.helpit.artigo.Artigo;
import br.com.helpit.artigo.ArtigoSearchDefinition;
import br.com.helpit.artigo.dto.request.RegistrarArtigoRequestDTO;
import br.com.helpit.artigo.dto.response.ArtigoResponseDTO;
import br.com.helpit.artigo.repository.ArtigoEntityManagerRepository;
import br.com.helpit.artigo.service.ArtigoService;
import br.com.helpit.artigo.transformer.ArtigoResponseTransformer;
import br.com.helpit.empresa.Empresa;
import br.com.helpit.empresa.dto.request.RegistrarEmpresaRequestDTO;
import br.com.helpit.empresa.dto.response.EmpresaResponseDTO;
import br.com.helpit.empresa.service.EmpresaService;
import br.com.helpit.empresa.service.UsuarioEmpresaService;
import br.com.helpit.empresa.transformer.EmpresaResponseTransformer;
import br.com.helpit.util.SQLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/v1/empresa")
public class EmpresaController {

    private @Autowired EmpresaService empresaService;
    private @Autowired UsuarioEmpresaService usuarioEmpresaService;
    private @Autowired ArtigoService artigoService;

    private @Autowired EmpresaResponseTransformer empresaResponseTransformer;
    private @Autowired ArtigoResponseTransformer artigoResponseTransformer;

    private @Autowired ArtigoEntityManagerRepository artigoEntityManagerRepository;

    public EmpresaController() {}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmpresaResponseDTO criarEmpresa(@RequestBody RegistrarEmpresaRequestDTO registrarEmpresaDTO)
    {
        final Empresa criadaNovaEmpresa = empresaService.criarEmpresa(registrarEmpresaDTO);
        return empresaResponseTransformer
                .transformarEmpresaEmEmpresaResponseDTO(criadaNovaEmpresa);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<EmpresaResponseDTO> obterEmpresasDeUsuario()
    {
        final Set<Empresa> empresasDeUsuarioAutenticado = usuarioEmpresaService
                .obterEmpresasDeUsuarioAutenticado();

        return empresaResponseTransformer
                .transformarListaEmpresaEmEmpresaResponseDTO(empresasDeUsuarioAutenticado, HashSet::new);
    }

    @PostMapping(value = "/{idEmpresa}/artigo")
    @ResponseStatus(HttpStatus.CREATED)
    public ArtigoResponseDTO criarArtigo(@PathVariable Long idEmpresa,
                                         @RequestBody RegistrarArtigoRequestDTO registrarArtigoDTO)
    {
        final Artigo novoArtigo = artigoService.criarArtigo(registrarArtigoDTO, idEmpresa);
        return artigoResponseTransformer.transformarArtigoEmArtigoResponseDTO(novoArtigo);
    }

    @GetMapping(value = "/{idEmpresa}/artigo")
    @ResponseStatus(HttpStatus.OK)
    public List<ArtigoResponseDTO> buscaArtigosAvancado(@PathVariable Long idEmpresa,
                                                        @RequestParam(required = false) String textoPesquisaTitulo,
                                                        @RequestParam(required = false) String textoPesquisaCorpo,
                                                        @RequestParam(required = false, defaultValue = "ASC") String tipoOrdenacao)
    {
        ArtigoSearchDefinition artigoSearchDefinition = ArtigoSearchDefinition
                .builder()
                    .textoPesquisaCorpo(textoPesquisaCorpo)
                    .textoPesquisaTitulo(textoPesquisaTitulo)
                    .tipoOrdenacao(tipoOrdenacao)
                .build();

        List<Artigo> artigosEncontrados = artigoEntityManagerRepository
                .buscaArtigosComSearchDefinition(artigoSearchDefinition, idEmpresa);

        return artigoResponseTransformer.transformarListaArtigoEmArtigoResponseDTO(artigosEncontrados);
    }

}
