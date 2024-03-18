package br.com.helpit.artigo.dto.response;

import br.com.helpit.artigo.Tag;
import br.com.helpit.usuario.dto.response.UsernameUsuarioResponseDTO;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Builder
public record ArtigoResponseDTO(
        Long idArtigo,
        String titulo,
        String corpo,
        UsernameUsuarioResponseDTO usuario,
        Set<Tag> tags,
        LocalDateTime dataCriacao) { }
