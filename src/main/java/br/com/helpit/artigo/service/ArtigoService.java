package br.com.helpit.artigo.service;

import br.com.helpit.artigo.Artigo;
import br.com.helpit.artigo.dto.request.AdicionarTagRequestDTO;
import br.com.helpit.artigo.dto.request.RegistrarArtigoRequestDTO;

import java.util.List;

public interface ArtigoService {

    Artigo obterArtigoPorId(Long idArtigo);

    Artigo criarArtigo(RegistrarArtigoRequestDTO registrarArtigoDTO, Long idEmpresa);

    Artigo adicionarTagsAoArtigo(List<AdicionarTagRequestDTO> adicionarTagRequestDTOs, Long idEmpresa, Long idArtigo);

    List<Artigo> obterArtigos();

}
