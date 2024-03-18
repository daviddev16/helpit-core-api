package br.com.helpit.util;

import br.com.helpit.core.ServiceException;

import static java.lang.String.*;

public final class SQLUtil {

    public static String verificaTipoOrdenacao(String tipoOrdenacao) {
        if (tipoOrdenacao.equalsIgnoreCase("ASC") || tipoOrdenacao.equalsIgnoreCase("DESC")) {
            return tipoOrdenacao;
        }
        throw new ServiceException(format("\"%s\" não é um tipo de " +
                "ordenação válido [ASC ou DESC].", tipoOrdenacao));
    }

    public static String nullWhenBlank(String str) {
        if (str != null && str.isBlank()) {
            return null;
        }
        return str;
    }

}
