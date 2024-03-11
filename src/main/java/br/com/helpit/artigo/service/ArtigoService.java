package br.com.helpit.artigo.service;

import br.com.helpit.artigo.Artigo;
import br.com.helpit.artigo.dto.request.RequestRegistrarArtigoDTO;

public interface ArtigoService {

    Artigo criarArtigo( RequestRegistrarArtigoDTO registrarArtigoDTO, Long empresaId );

}
