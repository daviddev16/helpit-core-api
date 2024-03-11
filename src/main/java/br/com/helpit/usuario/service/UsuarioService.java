package br.com.helpit.usuario.service;

import br.com.helpit.usuario.Usuario;
import br.com.helpit.usuario.dto.request.RequestRegistrarUsuarioDTO;
import br.com.helpit.usuario.enums.StatusAtividade;

public interface UsuarioService {

    Usuario registrarUsuario( RequestRegistrarUsuarioDTO registrarUsuarioDTO );

    /* Onde passaporte é igual a e-mail ou login */
    Usuario obterUsuarioPorLoginOuEmail(String passaporte);

    Usuario atualizarStatusAtividade(Usuario usuario, StatusAtividade novoStatusAtividade);

}
