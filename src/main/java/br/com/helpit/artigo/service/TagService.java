package br.com.helpit.artigo.service;

import br.com.helpit.artigo.Tag;

public interface TagService {

    Tag obterTagPorNome(String nomeTag, Long idEmpresa);

}
