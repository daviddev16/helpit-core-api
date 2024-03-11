package br.com.helpit.empresa.service;

import br.com.helpit.empresa.Empresa;
import br.com.helpit.empresa.dto.request.RequestRegistrarEmpresaDTO;
import br.com.helpit.usuario.Usuario;

public interface EmpresaService {

    Empresa criarEmpresa(RequestRegistrarEmpresaDTO registrarEmpresaDTO);

    Empresa obterEmpresaPorId(Long empresaId);

}
