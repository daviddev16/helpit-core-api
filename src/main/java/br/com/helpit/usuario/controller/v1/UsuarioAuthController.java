package br.com.helpit.usuario.controller.v1;

import br.com.helpit.usuario.Usuario;
import br.com.helpit.usuario.dto.request.RequestRegistrarUsuarioDTO;
import br.com.helpit.usuario.dto.response.ResponseUsuarioDTO;
import br.com.helpit.usuario.service.CodigoConfirmacaoService;
import br.com.helpit.usuario.service.UsuarioService;
import br.com.helpit.usuario.transformer.ResponseUsuarioTransformer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/auth")
public class UsuarioAuthController {

    private final UsuarioService usuarioService;

    private final CodigoConfirmacaoService codigoConfirmacaoService;

    private final ResponseUsuarioTransformer responseUsuarioTransformer;

    public UsuarioAuthController(
            UsuarioService usuarioService,
            CodigoConfirmacaoService codigoConfirmacaoService,
            ResponseUsuarioTransformer responseUsuarioTransformer)
    {
        this.codigoConfirmacaoService = codigoConfirmacaoService;
        this.responseUsuarioTransformer = responseUsuarioTransformer;
        this.usuarioService = usuarioService;
    }

    @PostMapping(value = "/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseUsuarioDTO registarUsuario(@RequestBody RequestRegistrarUsuarioDTO registrarUsuarioDTO)
    {
        Usuario criadoNovoUsuario = usuarioService.registrarUsuario(registrarUsuarioDTO);

        String codigoConfirmacao = codigoConfirmacaoService
                .obterCodigoConfirmacaoDeUsuario(criadoNovoUsuario);

        return responseUsuarioTransformer
                .transformarUsuarioEmResponseUsuarioDTO( criadoNovoUsuario, codigoConfirmacao );
    }

    @GetMapping("/confirmaCadastro")
    public String confirmarCadastramentoDeUsuario(@RequestParam("codigo") String codigoConfirmacao)
    {
        codigoConfirmacaoService.confirmarCadastramentoDeUsuarioAutenticado(codigoConfirmacao);
        return "Cadastro do usuário foi confirmado com sucesso!";
    }

}
