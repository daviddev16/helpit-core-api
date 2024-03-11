package br.com.helpit.usuario.transformer;

import br.com.helpit.usuario.Usuario;
import br.com.helpit.usuario.dto.response.ResponseUsuarioDTO;
import br.com.helpit.usuario.service.CodigoConfirmacaoService;
import org.springframework.stereotype.Component;

@Component
public class ResponseUsuarioTransformer {

    private final CodigoConfirmacaoService codigoConfirmacaoService;

    public ResponseUsuarioTransformer(CodigoConfirmacaoService codigoConfirmacaoService) {
        this.codigoConfirmacaoService = codigoConfirmacaoService;
    }

    public ResponseUsuarioDTO transformarUsuarioEmResponseUsuarioDTO(Usuario usuario, String codigoConfirmacao) {
        return new ResponseUsuarioDTO(
                usuario.getLogin(),
                usuario.getEmail(),
                usuario.getStatusAtividade(),
                codigoConfirmacao);
    }

}
