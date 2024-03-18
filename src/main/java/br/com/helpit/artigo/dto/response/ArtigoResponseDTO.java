package br.com.helpit.artigo.dto.response;

import br.com.helpit.usuario.dto.response.UsernameUsuarioResponseDTO;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ArtigoResponseDTO(
        Long idArtigo,
        String titulo,
        String corpo,
        UsernameUsuarioResponseDTO usuario,
        LocalDateTime dataCriacao) { }
