package br.com.helpit.empresa.transformer;

import br.com.helpit.artigo.dto.response.ArtigoResponseDTO;
import br.com.helpit.artigo.transformer.ArtigoResponseTransformer;
import br.com.helpit.empresa.CargoEmpresa;
import br.com.helpit.empresa.Empresa;
import br.com.helpit.empresa.dto.response.EmpresaResponseDTO;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
public class EmpresaResponseTransformer {

    private final ArtigoResponseTransformer artigoResponseTransformer;

    public EmpresaResponseTransformer(ArtigoResponseTransformer artigoResponseTransformer) {
        this.artigoResponseTransformer = artigoResponseTransformer;
    }

    public <E extends Collection<EmpresaResponseDTO>> E transformarListaEmpresaEmEmpresaResponseDTO(Collection<Empresa> empresa,
                                                                                                    Supplier<E> collectionSupplier)
    {
        return empresa.stream()
                .map(this::transformarEmpresaEmEmpresaResponseDTO)
                .collect(Collectors.toCollection(collectionSupplier));
    }

    public EmpresaResponseDTO transformarEmpresaEmEmpresaResponseDTO(Empresa empresa) {
        final String passaporteAdministrador = obterPassaporteDonoDeEmpresa(empresa);
        return EmpresaResponseDTO
                .builder()
                    .idEmpresa(empresa.getId())
                    .sufixo(empresa.getSufixo())
                    .nome(empresa.getNome())
                    .administrador(passaporteAdministrador)
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
