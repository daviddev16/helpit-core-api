package br.com.helpit.global;

import br.com.helpit.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalSettingsConfiguration implements AplicacaoSettings {

    @Value("${helpit.configuracao-global.max-empresas-usuario-padrao}")
    private Integer maxEmpresasUsuarioPadrao;

    @Override
    public Integer obterNumeroMaximoDeEmpresaPara(Usuario usuario) {
        /*TODO: Verificar qual o plano do usuário quando essa lógica for implementada */
        return maxEmpresasUsuarioPadrao;
    }


}
