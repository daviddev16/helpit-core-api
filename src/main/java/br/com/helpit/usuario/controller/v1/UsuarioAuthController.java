package br.com.helpit.usuario.controller.v1;

import br.com.helpit.usuario.Usuario;
import br.com.helpit.usuario.dto.request.RegistrarUsuarioRequestDTO;
import br.com.helpit.usuario.dto.response.UsuarioResponseDTO;
import br.com.helpit.usuario.service.CodigoConfirmacaoService;
import br.com.helpit.usuario.service.UsuarioService;
import br.com.helpit.usuario.transformer.UsuarioResponseTransformer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/auth")
public class UsuarioAuthController {

    private final UsuarioService usuarioService;

    private final CodigoConfirmacaoService codigoConfirmacaoService;

    private final UsuarioResponseTransformer usuarioResponseTransformer;

    public UsuarioAuthController(
            UsuarioService usuarioService,
            CodigoConfirmacaoService codigoConfirmacaoService,
            UsuarioResponseTransformer usuarioResponseTransformer)
    {
        this.codigoConfirmacaoService = codigoConfirmacaoService;
        this.usuarioResponseTransformer = usuarioResponseTransformer;
        this.usuarioService = usuarioService;
    }

    @PostMapping(value = "/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponseDTO registarUsuario(@RequestBody RegistrarUsuarioRequestDTO registrarUsuarioDTO)
    {
        Usuario criadoNovoUsuario = usuarioService.registrarUsuario(registrarUsuarioDTO);

        String codigoConfirmacao = codigoConfirmacaoService
                .obterCodigoConfirmacaoDeUsuario(criadoNovoUsuario);

        return usuarioResponseTransformer
                .transformarUsuarioEmResponseUsuarioDTO( criadoNovoUsuario, codigoConfirmacao );
    }

    @GetMapping("/confirmaCadastro")
    public String confirmarCadastramentoDeUsuario(@RequestParam("codigo") String codigoConfirmacao)
    {
        codigoConfirmacaoService.confirmarCadastramentoDeUsuarioAutenticado(codigoConfirmacao);
        return "Cadastro do usuário foi confirmado com sucesso!";
    }

}
