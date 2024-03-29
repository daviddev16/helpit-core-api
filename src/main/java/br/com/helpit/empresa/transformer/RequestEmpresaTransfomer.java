package br.com.helpit.empresa.transformer;

import br.com.helpit.empresa.Empresa;
import br.com.helpit.empresa.dto.request.RegistrarEmpresaRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class RequestEmpresaTransfomer {

    public Empresa transformarRegistrarEmpresaDTOEmEmpresa(RegistrarEmpresaRequestDTO registrarEmpresaDTO) {
        return Empresa
                .builder()
                    .nome(registrarEmpresaDTO.nome())
                    .sufixo(registrarEmpresaDTO.sufixo())
                .build();
    }

}
