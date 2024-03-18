package br.com.helpit.artigo.dto.request;

import lombok.Builder;

@Builder
public record AdicionarTagRequestDTO(String nomeTag) { }
