package br.com.helpit.empresa.dto.response;

import br.com.helpit.artigo.Artigo;
import br.com.helpit.artigo.dto.response.ArtigoResponseDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record EmpresaResponseDTO(
        Long idEmpresa,
        String nome,
        String sufixo,
        String administrador,
        List<ArtigoResponseDTO> artigos) { }
