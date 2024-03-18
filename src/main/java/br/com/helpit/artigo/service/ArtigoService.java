package br.com.helpit.artigo.service;

import br.com.helpit.artigo.Artigo;
import br.com.helpit.artigo.dto.request.RegistrarArtigoRequestDTO;

import java.util.List;

public interface ArtigoService {

    Artigo criarArtigo(RegistrarArtigoRequestDTO registrarArtigoDTO, Long idEmpresa);

    List<Artigo> obterArtigos();

}
