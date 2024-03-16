package br.com.helpit.artigo.service;

import br.com.helpit.artigo.Artigo;
import br.com.helpit.artigo.dto.request.RegistrarArtigoRequestDTO;

public interface ArtigoService {

    Artigo criarArtigo(RegistrarArtigoRequestDTO registrarArtigoDTO, Long empresaId );

}
