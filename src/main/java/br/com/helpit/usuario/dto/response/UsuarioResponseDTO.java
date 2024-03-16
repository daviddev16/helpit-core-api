package br.com.helpit.usuario.dto.response;

import br.com.helpit.usuario.enums.StatusAtividade;
import lombok.Builder;

@Builder
public record UsuarioResponseDTO(String login, String email,
                                 StatusAtividade statusAtividade, String codigoConfirmacao) { }
