package br.com.helpit.artigo.impl;

import br.com.helpit.artigo.Tag;
import br.com.helpit.artigo.exception.TagNaoEncontradaException;
import br.com.helpit.artigo.repository.TagRepository;
import br.com.helpit.artigo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {

    private @Autowired TagRepository tagRepository;

    public TagServiceImpl() {}

    @Override
    public Tag obterTagPorNome(String nomeTag, Long idEmpresa) {
        return tagRepository
                .findByNomeAndIdEmpresa(nomeTag, idEmpresa)
                .orElseThrow(() -> new TagNaoEncontradaException(nomeTag, idEmpresa));
    }
}
