package br.com.helpit.usuario.impl;

import br.com.helpit.core.AuthenticatedService;
import br.com.helpit.core.IlegalServiceStateException;
import br.com.helpit.usuario.CodigoConfirmacao;
import br.com.helpit.usuario.Usuario;
import br.com.helpit.usuario.enums.StatusAtividade;
import br.com.helpit.usuario.exception.CodigoConfirmacaoNaoEncontradoException;
import br.com.helpit.usuario.repository.CodigoConfirmacaoRepository;
import br.com.helpit.usuario.service.CodigoConfirmacaoService;
import br.com.helpit.usuario.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

import static java.lang.String.format;

@Service
public class CodigoConfirmacaoServiceImpl implements CodigoConfirmacaoService, AuthenticatedService {

    private final UsuarioService usuarioService;
    private final CodigoConfirmacaoRepository codigoConfirmacaoRepository;

    public CodigoConfirmacaoServiceImpl(UsuarioService usuarioService,
                                        CodigoConfirmacaoRepository codigoConfirmacaoRepository)
    {
        this.usuarioService = usuarioService;
        this.codigoConfirmacaoRepository = codigoConfirmacaoRepository;
    }

    public CodigoConfirmacao obterCodigoConfirmacao(String codigoConfirmacao) {
        return codigoConfirmacaoRepository
                .findByCodigoConfirmacao(codigoConfirmacao)
                .orElseThrow(CodigoConfirmacaoNaoEncontradoException::new);
    }

    @Override
    @Transactional
    public String obterCodigoConfirmacaoDeUsuario(Usuario usuario) {

        if (usuario.getStatusAtividade() != StatusAtividade.AGUARDANDO_CONFIRMACAO)
            throw new IlegalServiceStateException(format("Usuário \"%s\" já obteve o" +
                    " cadastramento confirmado.", usuario.getLogin()));

        String codigoConfirmacaoTexto = obterNovoCodigoConfirmacaoEmBase64(usuario);

        CodigoConfirmacao codigoConfirmacao = CodigoConfirmacao.builder()
                .codigoConfirmacao(codigoConfirmacaoTexto)
                .usuario(usuario)
                .build();

        codigoConfirmacaoRepository.save(codigoConfirmacao);

        return codigoConfirmacaoTexto;
    }

    @Transactional
    public void confirmarCadastramentoDeUsuario(String codigoConfirmacaoString, Usuario usuario) {

        CodigoConfirmacao codigoConfirmacao = obterCodigoConfirmacao(codigoConfirmacaoString);

        if (!Objects.equals(codigoConfirmacao.getUsuario().getId(), usuario.getId()))
            throw new IlegalServiceStateException("O código de verificação não pertence ao usuário autenticado.");

        usuarioService.atualizarStatusAtividade(usuario, StatusAtividade.ATIVADO);
        codigoConfirmacaoRepository.delete(codigoConfirmacao);

    }

    @Override
    public void confirmarCadastramentoDeUsuarioAutenticado(String codigoConfirmacao) {

        final Authentication authentication = getAuthentication();

        Usuario usuarioAutenticado = usuarioService
                .obterUsuarioPorLoginOuEmail(authentication.getName());

        confirmarCadastramentoDeUsuario(codigoConfirmacao, usuarioAutenticado);

    }

    private String obterNovoCodigoConfirmacaoEmBase64(Usuario usuario) {
        return Base64.getEncoder()
                .encodeToString(format("%s:%s", usuario.getLogin(), usuario.getEmail())
                        .getBytes(StandardCharsets.UTF_8));
    }

}
