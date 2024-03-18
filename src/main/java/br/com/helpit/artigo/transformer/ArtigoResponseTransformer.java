package br.com.helpit.artigo.transformer;

import br.com.helpit.artigo.Artigo;
import br.com.helpit.artigo.ArtigoTag;
import br.com.helpit.artigo.Tag;
import br.com.helpit.artigo.dto.response.ArtigoResponseDTO;
import br.com.helpit.artigo.repository.ArtigoTagRepository;
import br.com.helpit.empresa.Empresa;
import br.com.helpit.empresa.dto.response.EmpresaResponseDTO;
import br.com.helpit.usuario.dto.response.UsernameUsuarioResponseDTO;
import br.com.helpit.usuario.transformer.UsuarioResponseTransformer;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
public class ArtigoResponseTransformer {

    private final ArtigoTagRepository artigoTagRepository;
    private final UsuarioResponseTransformer usuarioResponseTransformer;

    public ArtigoResponseTransformer(ArtigoTagRepository artigoTagRepository, UsuarioResponseTransformer usuarioResponseTransformer) {
        this.artigoTagRepository = artigoTagRepository;
        this.usuarioResponseTransformer = usuarioResponseTransformer;
    }

    public List<ArtigoResponseDTO> transformarListaArtigoEmArtigoResponseDTO(Collection<Artigo> artigos) {

        List<ArtigoResponseDTO> artigoResponseDTOS = new ArrayList<>(artigos.size());

        artigos.forEach(artigo ->
                artigoResponseDTOS.add(transformarArtigoEmArtigoResponseDTO(artigo)));

        return artigoResponseDTOS;
    }

    public ArtigoResponseDTO transformarArtigoEmArtigoResponseDTO(Artigo artigo) {

        final UsernameUsuarioResponseDTO usuarioResponseDTO = usuarioResponseTransformer
                .transformarUsuarioEmUsernameUsuarioReponse(artigo.getUsuario());

        Set<Tag> tags = artigo.getArtigoTags().stream().map(ArtigoTag::getTag).collect(Collectors.toSet());;

        return ArtigoResponseDTO
                .builder()
                    .idArtigo(artigo.getId())
                    .corpo(artigo.getCorpo())
                    .titulo(artigo.getTitulo())
                    .dataCriacao(artigo.getDataCriacao())
                    .tags(tags)
                    .usuario(usuarioResponseDTO)
                .build();
    }

}
