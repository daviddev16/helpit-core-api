package br.com.helpit.empresa.service;

import br.com.helpit.empresa.CargoEmpresa;
import br.com.helpit.empresa.Empresa;
import br.com.helpit.empresa.UsuarioEmpresa;
import br.com.helpit.usuario.Usuario;

import java.util.List;
import java.util.Set;

public interface UsuarioEmpresaService {

    UsuarioEmpresa criarUsuarioEmpresa( Usuario usuario, Empresa empresa, CargoEmpresa cargoEmpresa );

    Integer obterQuantidadeDeEmpresasDeUsuario( Usuario usuario );

    Set<Empresa> obterEmpresasDeUsuario( Usuario usuario );

    Set<Empresa> obterEmpresasDeUsuarioAutenticado();

    boolean verificarAutorizacaoUsuarioEmpresa(Usuario usuario, Empresa empresa);

}
