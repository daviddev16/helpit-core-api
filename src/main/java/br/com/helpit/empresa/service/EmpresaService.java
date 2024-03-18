package br.com.helpit.empresa.service;

import br.com.helpit.empresa.Empresa;
import br.com.helpit.empresa.dto.request.RegistrarEmpresaRequestDTO;

public interface EmpresaService {

    Empresa criarEmpresa(RegistrarEmpresaRequestDTO registrarEmpresaDTO);

    Empresa obterEmpresaPorId(Long idEmpresa);

}
