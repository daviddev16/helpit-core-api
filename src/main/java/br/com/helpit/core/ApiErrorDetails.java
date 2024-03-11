package br.com.helpit.core;

import java.util.List;

public class ApiErrorDetails {

    private final List<String> mensagens;

    public ApiErrorDetails(List<String> mensagens) {
        this.mensagens = mensagens;
    }

    public ApiErrorDetails(String mensagem) {
        this(List.of(mensagem));
    }

    public List<String> getMensagens() {
        return mensagens;
    }
}
