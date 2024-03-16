package br.com.helpit.usuario.dto.response;

import lombok.Builder;

@Builder
public record UsernameUsuarioResponseDTO(Long idUsuario, String login) { }
