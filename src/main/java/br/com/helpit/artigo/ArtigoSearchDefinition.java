package br.com.helpit.artigo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtigoSearchDefinition {

    private String textoPesquisaCorpo;
    private String textoPesquisaTitulo;
    private String tipoOrdenacao;

    /* possível feature futura, porém, será dtcriacao inicialmente */
    public String getColunaParaOrdenacao() {
        return "dtcriacao";
    }

}