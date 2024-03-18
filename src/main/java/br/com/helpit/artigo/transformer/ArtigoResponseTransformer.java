package br.com.helpit.artigo.transformer;

import br.com.helpit.artigo.Artigo;
import br.com.helpit.artigo.dto.response.ArtigoResponseDTO;
import br.com.helpit.empresa.Empresa;
import br.com.helpit.empresa.dto.response.EmpresaResponseDTO;
import br.com.helpit.usuario.dto.response.UsernameUsuarioResponseDTO;
import br.com.helpit.usuario.transformer.UsuarioResponseTransformer;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
public class ArtigoResponseTransformer {

    private final UsuarioResponseTransformer usuarioResponseTransformer;

    public ArtigoResponseTransformer(UsuarioResponseTransformer usuarioResponseTransformer) {
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

        return ArtigoResponseDTO
                .builder()
                    .idArtigo(artigo.getId())
                    .corpo(artigo.getCorpo())
                    .titulo(artigo.getTitulo())
                    .dataCriacao(artigo.getDataCriacao())
                    .usuario(usuarioResponseDTO)
                .build();
    }

}
