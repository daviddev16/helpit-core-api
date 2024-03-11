package br.com.helpit.usuario.enums;

public enum StatusAtividade {

    INVALIDO   (false),
    DESATIVADO (false),

    ATIVADO  (true),
    FANTASMA (true),

    AGUARDANDO_CONFIRMACAO (true);

    private final boolean statusUtilizacao;

    StatusAtividade(boolean statusUtilizacao) {
        this.statusUtilizacao = statusUtilizacao;
    }

    public boolean ehHabilitadoParaUtilizacao() {
        return statusUtilizacao;
    }

}
