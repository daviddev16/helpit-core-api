package br.com.helpit.usuario.service;

import br.com.helpit.usuario.Usuario;

public interface CodigoConfirmacaoService {

    String obterCodigoConfirmacaoDeUsuario(Usuario usuario);

    void confirmarCadastramentoDeUsuarioAutenticado(String codigoConfirmacao);
}
