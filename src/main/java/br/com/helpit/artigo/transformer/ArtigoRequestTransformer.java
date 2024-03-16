package br.com.helpit.artigo.transformer;

import br.com.helpit.artigo.Artigo;
import br.com.helpit.artigo.dto.request.RegistrarArtigoRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class ArtigoRequestTransformer {

    public Artigo transformarArtigoRequestEmArtigo(RegistrarArtigoRequestDTO registrarArtigoDTO) {
        return Artigo
                .builder()
                    .titulo(registrarArtigoDTO.titulo())
                    .corpo(registrarArtigoDTO.corpo())
                .build();
    }

}
