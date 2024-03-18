package br.com.helpit.artigo.repository;

import br.com.helpit.artigo.Artigo;
import br.com.helpit.artigo.ArtigoSearchDefinition;
import br.com.helpit.core.ServiceException;
import br.com.helpit.empresa.Empresa;
import br.com.helpit.empresa.service.EmpresaService;
import br.com.helpit.usuario.Usuario;
import br.com.helpit.util.SQLUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.StringJoiner;

import static br.com.helpit.util.SQLUtil.*;
import static java.lang.String.*;

@Repository
public class ArtigoEntityManagerRepository {

    private @Autowired EntityManager entityManager;
    private @Autowired EmpresaService empresaService;

    private final String schemaTabelaArtigo;

    public ArtigoEntityManagerRepository()
    {
        Table tableAnnArtigo = Artigo.class.getAnnotation(Table.class);
        if (tableAnnArtigo == null) {
            throw new ServiceException("Não foi possível identificar " +
                    "a definição de tabela para " + Artigo.class.getName());
        }
        schemaTabelaArtigo = String.format("%s.%s", tableAnnArtigo.schema(), tableAnnArtigo.name());
    }

    /**
     * Definições do full-text search no PostgreSQL
     * talvez utilizar flyway futuramente
     *
     * ALTER TABLE helpit.artigo ADD COLUMN tsvc_corpo tsvector
     * 	GENERATED ALWAYS AS (to_tsvector('portuguese', corpo)) STORED;
     *
     * ALTER TABLE helpit.artigo ADD COLUMN tsvc_titulo tsvector
     * 	GENERATED ALWAYS AS (to_tsvector('portuguese', titulo)) STORED;
     *
     * CREATE INDEX tsvc_corpo_idx ON helpit.artigo USING GIN (tsvc_corpo);
     *
     * CREATE INDEX tsvc_titulo_idx ON helpit.artigo USING GIN (tsvc_titulo);
     *
     * */
    @SuppressWarnings("unchecked")
    public List<Artigo> buscaArtigosComSearchDefinition(ArtigoSearchDefinition artigoSearchDefinition, Long idEmpresa) {

        final boolean flagHasTextoPesquisaTitulo = nullWhenBlank(artigoSearchDefinition.getTextoPesquisaTitulo()) != null;
        final boolean flagHasTextoPesquisaCorpo  = nullWhenBlank(artigoSearchDefinition.getTextoPesquisaCorpo()) != null;
        final boolean flagHasTipoOrdenacao       = nullWhenBlank(artigoSearchDefinition.getTipoOrdenacao()) != null;

        final boolean flagHasWhereAnd         = (flagHasTextoPesquisaTitulo | flagHasTextoPesquisaCorpo);

        StringBuilder pgSqlBuilder = new StringBuilder()
                .append("SELECT")
                .append(" *")
                .append(" FROM ")
                .append(schemaTabelaArtigo)
                .append(" WHERE")
                .append(" idempresa = :paramIdEmpresa ");

        if (flagHasWhereAnd)
            pgSqlBuilder.append(" AND ");

        StringJoiner criteriaOrJoiner = new StringJoiner(" OR ");

        if (flagHasTextoPesquisaCorpo)
            criteriaOrJoiner.add(criteriaFullTextSearchParaColuna("tsvc_corpo", ":paramTextoPesquisaCorpo"));

        if (flagHasTextoPesquisaTitulo)
            criteriaOrJoiner.add(criteriaFullTextSearchParaColuna("tsvc_titulo", ":paramTextoPesquisaTitulo"));

        pgSqlBuilder.append(criteriaOrJoiner.toString());

        String tipoOrdenacaoValidado = verificaTipoOrdenacao(artigoSearchDefinition.getTipoOrdenacao());

        if (flagHasTipoOrdenacao)
            pgSqlBuilder
                    .append(" ORDER BY ")
                    .append(artigoSearchDefinition.getColunaParaOrdenacao()).append(" ")
                    .append(tipoOrdenacaoValidado);

        final Query pesquisaArtigosQuery = entityManager.createNativeQuery(pgSqlBuilder.toString(), Artigo.class);

        pesquisaArtigosQuery.setParameter("paramIdEmpresa", idEmpresa);

        if (flagHasTextoPesquisaCorpo)
            pesquisaArtigosQuery.setParameter("paramTextoPesquisaCorpo",
                    artigoSearchDefinition.getTextoPesquisaCorpo());

        if (flagHasTextoPesquisaTitulo)
            pesquisaArtigosQuery.setParameter("paramTextoPesquisaTitulo",
                    artigoSearchDefinition.getTextoPesquisaTitulo());

        final List<Artigo> resultListArtigos = pesquisaArtigosQuery.getResultList();

        Empresa empresaArtigo = empresaService.obterEmpresaPorId(idEmpresa);
        resultListArtigos.forEach(artigo -> artigo.setEmpresa(empresaArtigo));

        return  resultListArtigos;
    }

    private String criteriaFullTextSearchParaColuna(String colunaTsVector, String parametroTexto) {
        return format("%s @@ phraseto_tsquery('portuguese', %s)", colunaTsVector, parametroTexto);
    }

}
