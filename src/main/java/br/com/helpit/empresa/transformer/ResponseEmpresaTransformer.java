package br.com.helpit.empresa.transformer;

import br.com.helpit.empresa.CargoEmpresa;
import br.com.helpit.empresa.Empresa;
import br.com.helpit.empresa.dto.response.ResponseEmpresaDTO;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
public class ResponseEmpresaTransformer {

    public <E extends Collection<ResponseEmpresaDTO>> E transformarListaEmpresaEmResponseEmpresaDTO(Collection<Empresa> empresa,
                                                                                                    Supplier<E> collectionSupplier)
    {
        return empresa.stream()
                .map(this::transformarEmpresaEmResponseEmpresaDTO)
                .collect(Collectors.toCollection(collectionSupplier));
    }

    public ResponseEmpresaDTO transformarEmpresaEmResponseEmpresaDTO(Empresa empresa) {
        final String passaporteDono = obterPassaporteDonoDeEmpresa(empresa);
        return ResponseEmpresaDTO
                .builder()
                    .idEmpresa(empresa.getId())
                    .sufixo(empresa.getSufixo())
                    .nome(empresa.getNome())
                    .artigos(empresa.getArtigos())
                    .passaporteDono(passaporteDono)
                .build();
    }

    private String obterPassaporteDonoDeEmpresa(Empresa empresa) {
        return empresa.getUsuariosEmpresa()
                .stream()
                    .filter(usuarioEmpresa -> usuarioEmpresa.getCargoEmpresa() == CargoEmpresa.DONO)
                    .findFirst()
                    .orElseThrow(IllegalStateException::new)
                    .getUsuario()
                        .getLogin();
    }

}
