package br.com.helpit.empresa.dto.response;

import br.com.helpit.artigo.Artigo;
import lombok.Builder;

import java.util.List;

@Builder
public record ResponseEmpresaDTO(Long idEmpresa, String nome, String sufixo, String passaporteDono, List<Artigo> artigos) { }
