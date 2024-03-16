package br.com.helpit.artigo.dto.response;

import br.com.helpit.usuario.dto.response.UsernameUsuarioResponseDTO;
import lombok.Builder;

@Builder
public record ArtigoResponseDTO(
        Long idArtigo,
        String titulo,
        String corpo,
        UsernameUsuarioResponseDTO usuario) { }
