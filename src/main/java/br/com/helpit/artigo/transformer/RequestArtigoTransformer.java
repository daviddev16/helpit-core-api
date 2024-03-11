package br.com.helpit.artigo.transformer;

import br.com.helpit.artigo.Artigo;
import br.com.helpit.artigo.dto.request.RequestRegistrarArtigoDTO;
import org.springframework.stereotype.Component;

@Component
public class RequestArtigoTransformer {

    public Artigo transformarRegistrarArtigoDTOEmArtigo(RequestRegistrarArtigoDTO registrarArtigoDTO) {
        return Artigo
                .builder()
                    .titulo(registrarArtigoDTO.titulo())
                    .corpo(registrarArtigoDTO.corpo())
                .build();
    }

}
