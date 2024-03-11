package br.com.helpit.empresa.controller.v1;

import br.com.helpit.artigo.Artigo;
import br.com.helpit.artigo.dto.request.RequestRegistrarArtigoDTO;
import br.com.helpit.artigo.service.ArtigoService;
import br.com.helpit.empresa.Empresa;
import br.com.helpit.empresa.dto.request.RequestRegistrarEmpresaDTO;
import br.com.helpit.empresa.dto.response.ResponseEmpresaDTO;
import br.com.helpit.empresa.service.EmpresaService;
import br.com.helpit.empresa.service.UsuarioEmpresaService;
import br.com.helpit.empresa.transformer.ResponseEmpresaTransformer;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/v1/empresa")
public class EmpresaController {

    private final EmpresaService empresaService;
    private final UsuarioEmpresaService usuarioEmpresaService;
    private final ArtigoService artigoService;

    private final ResponseEmpresaTransformer responseEmpresaTransformer;

    public EmpresaController(EmpresaService empresaService,
                             UsuarioEmpresaService usuarioEmpresaService,
                             ArtigoService artigoService,
                             ResponseEmpresaTransformer responseEmpresaTransformer)
    {
        this.empresaService = empresaService;
        this.usuarioEmpresaService = usuarioEmpresaService;
        this.artigoService = artigoService;
        this.responseEmpresaTransformer = responseEmpresaTransformer;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEmpresaDTO criarEmpresa(@RequestBody RequestRegistrarEmpresaDTO registrarEmpresaDTO)
    {
        Empresa criadaNovaEmpresa = empresaService.criarEmpresa(registrarEmpresaDTO);

        return responseEmpresaTransformer
                .transformarEmpresaEmResponseEmpresaDTO(criadaNovaEmpresa);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<ResponseEmpresaDTO> obterEmpresasDeUsuario()
    {
        Set<Empresa> empresasDeUsuarioAutenticado = usuarioEmpresaService
                .obterEmpresasDeUsuarioAutenticado();

        return responseEmpresaTransformer
                .transformarListaEmpresaEmResponseEmpresaDTO(empresasDeUsuarioAutenticado, HashSet::new);
    }

    @PostMapping(value = "/{empresaId}/artigo")
    @ResponseStatus(HttpStatus.CREATED)
    public Artigo criarArtigo(@PathVariable Long empresaId,
                              @RequestBody RequestRegistrarArtigoDTO registrarArtigoDTO)
    {
        Artigo criadoNovoArtigo = artigoService.criarArtigo(registrarArtigoDTO, empresaId);
        return criadoNovoArtigo;
    }

}
