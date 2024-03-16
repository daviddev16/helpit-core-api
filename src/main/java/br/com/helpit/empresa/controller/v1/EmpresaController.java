package br.com.helpit.empresa.controller.v1;

import br.com.helpit.artigo.Artigo;
import br.com.helpit.artigo.dto.request.RegistrarArtigoRequestDTO;
import br.com.helpit.artigo.dto.response.ArtigoResponseDTO;
import br.com.helpit.artigo.service.ArtigoService;
import br.com.helpit.artigo.transformer.ArtigoResponseTransformer;
import br.com.helpit.empresa.Empresa;
import br.com.helpit.empresa.dto.request.RegistrarEmpresaRequestDTO;
import br.com.helpit.empresa.dto.response.EmpresaResponseDTO;
import br.com.helpit.empresa.service.EmpresaService;
import br.com.helpit.empresa.service.UsuarioEmpresaService;
import br.com.helpit.empresa.transformer.EmpresaResponseTransformer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/v1/empresa")
public class EmpresaController {

    private final EmpresaService empresaService;
    private final UsuarioEmpresaService usuarioEmpresaService;
    private final ArtigoService artigoService;

    private final EmpresaResponseTransformer empresaResponseTransformer;
    private final ArtigoResponseTransformer artigoResponseTransformer;

    public EmpresaController(
                            /* inicialização serviços */
                             EmpresaService empresaService,
                             UsuarioEmpresaService usuarioEmpresaService,
                             ArtigoService artigoService,
                             /* inicialização transformers */
                             EmpresaResponseTransformer empresaResponseTransformer,
                             ArtigoResponseTransformer artigoResponseTransformer)
    {
        this.empresaService = empresaService;
        this.usuarioEmpresaService = usuarioEmpresaService;
        this.artigoService = artigoService;

        this.empresaResponseTransformer = empresaResponseTransformer;
        this.artigoResponseTransformer = artigoResponseTransformer;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmpresaResponseDTO criarEmpresa(@RequestBody RegistrarEmpresaRequestDTO registrarEmpresaDTO)
    {
        Empresa criadaNovaEmpresa = empresaService.criarEmpresa(registrarEmpresaDTO);

        return empresaResponseTransformer
                .transformarEmpresaEmEmpresaResponseDTO(criadaNovaEmpresa);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<EmpresaResponseDTO> obterEmpresasDeUsuario()
    {
        Set<Empresa> empresasDeUsuarioAutenticado = usuarioEmpresaService
                .obterEmpresasDeUsuarioAutenticado();

        return empresaResponseTransformer
                .transformarListaEmpresaEmEmpresaResponseDTO(empresasDeUsuarioAutenticado, HashSet::new);
    }

    @PostMapping(value = "/{empresaId}/artigo")
    @ResponseStatus(HttpStatus.CREATED)
    public ArtigoResponseDTO criarArtigo(@PathVariable Long empresaId,
                                         @RequestBody RegistrarArtigoRequestDTO registrarArtigoDTO)
    {
        Artigo novoArtigo = artigoService.criarArtigo(registrarArtigoDTO, empresaId);
        return artigoResponseTransformer.transformarArtigoEmArtigoResponseDTO(novoArtigo);
    }

}
