package br.com.helpit.usuario.transformer;

import br.com.helpit.usuario.Usuario;
import br.com.helpit.usuario.dto.response.UsernameUsuarioResponseDTO;
import br.com.helpit.usuario.dto.response.UsuarioResponseDTO;
import br.com.helpit.usuario.service.CodigoConfirmacaoService;
import org.springframework.stereotype.Component;

@Component
public class UsuarioResponseTransformer {

    private final CodigoConfirmacaoService codigoConfirmacaoService;

    public UsuarioResponseTransformer(CodigoConfirmacaoService codigoConfirmacaoService) {
        this.codigoConfirmacaoService = codigoConfirmacaoService;
    }

    public UsuarioResponseDTO transformarUsuarioEmResponseUsuarioDTO(Usuario usuario, String codigoConfirmacao) {
        return UsuarioResponseDTO
                .builder()
                    .login(usuario.getLogin())
                    .email(usuario.getEmail())
                    .statusAtividade(usuario.getStatusAtividade())
                    .codigoConfirmacao(codigoConfirmacao)
                .build();
    }

    public UsernameUsuarioResponseDTO transformarUsuarioEmUsernameUsuarioReponse(Usuario usuario) {
        return UsernameUsuarioResponseDTO
                .builder()
                  .idUsuario(usuario.getId())
                  .login(usuario.getLogin())
                .build();
    }

}
