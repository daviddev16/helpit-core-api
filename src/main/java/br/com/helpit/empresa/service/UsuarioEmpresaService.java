package br.com.helpit.empresa.service;

import br.com.helpit.empresa.CargoEmpresa;
import br.com.helpit.empresa.Empresa;
import br.com.helpit.empresa.UsuarioEmpresa;
import br.com.helpit.usuario.Usuario;

import java.util.List;
import java.util.Set;

public interface UsuarioEmpresaService {

    boolean verificarAutorizacaoUsuarioEmpresa(Usuario usuario, Empresa empresa);

    Integer obterQuantidadeDeEmpresasDeUsuario(Usuario usuario);

    UsuarioEmpresa criarUsuarioEmpresa(Usuario usuario, Empresa empresa, CargoEmpresa cargoEmpresa);

    Set<Empresa> obterEmpresasDeUsuario(Usuario usuario);

    Set<Empresa> obterEmpresasDeUsuarioAutenticado();


}
