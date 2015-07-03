package com.solucaocriativa.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.solucaocriativa.entidade.PermissaoTelaDepartamento;
import com.solucaocriativa.entidade.Usuario;
import com.solucaocriativa.service.ServicoPermissaoTelaDepartamento;
import com.solucaocriativa.service.ServicoUsuario;
import com.solucaocriativa.util.Constants;
import com.solucaocriativa.util.Criptografia;
import com.solucaocriativa.util.MessageUtil;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static Log log = LogFactory.getLog(CustomAuthenticationProvider.class);

    @Autowired
    private ServicoUsuario usuarioService;

    @Autowired
    private ServicoPermissaoTelaDepartamento servicoPermissaoTelaDepartamento;
    
    @Override
    public Authentication authenticate(Authentication auth) {

	try {
	    if (!auth.getName().isEmpty()) {

		Usuario usuario = usuarioService.findUser(auth.getName());

		if (usuario != null) {
		    validatePassword(auth, usuario);
		    return validateNamePassword(auth, usuario);
		}
	    }

	    throw new BadCredentialsException(MessageUtil.getMessage("msg.user.notFind"));

	} catch (BadCredentialsException e) {
	    throw new BadCredentialsException(e.getMessage());
	} catch (Exception e) {
	    log.error("Ocorreu um erro ao recuperar o usuario. ".concat(e.getMessage()));
	    throw new Error("Ocorreu um erro ao recuperar o usuario.");
	}
    }

    /**
     * Valida se a senha informada é o próprio nome do usuário.
     * @param auth Autenticação
     * @return Autenticação
     */
    private Authentication validateNamePassword(Authentication auth, Usuario usuario) {
	
	if (auth.getName().equals(auth.getCredentials())) {
	    log.debug("O usuario informado e a senha sao os mesmos.");
	    throw new BadCredentialsException("O usuario informado e a senha sao os mesmos.");

	} else {
		Collection<GrantedAuthority> regras = montaAcoes(usuario);
		regras.addAll(preparaRegrasGerais(usuario));

		Authentication authentication = 
			new Usuario(auth.getPrincipal(), auth.getCredentials(), regras, 
				usuario.getNome(), usuario.getLogin(), 
				usuario.getSenha(), usuario.getSenhaConfirmacao(), usuario.getDepartamento());
		return authentication;
	}
    }

    /**
     * Valida a senha do usuário.
     * @param auth Autenticação
     * @throws Exception
     */
    private void validatePassword(Authentication auth, Usuario usuario) throws Exception {
	try {
	    if (!Criptografia.encrypt((String) auth.getCredentials()).equals(usuario.getSenha())) {
		throw new BadCredentialsException("Erro ao realizar a validação da senha.");
	    } 	
	} catch (BadCredentialsException e) {
	    log.error(e.getMessage());
	    throw new BadCredentialsException(e.getMessage());
	} catch (Exception e) {
	    log.error(e.getMessage());
	    throw new Exception(e.getMessage());
	}
    }

    @Override
    public boolean supports(Class<?> arg0) {
	return true;
    }
    
    private Collection<GrantedAuthority> montaAcoes(Usuario usuario) {
	
	Collection<GrantedAuthority> acoes = new ArrayList<>();
	List<PermissaoTelaDepartamento> permissoes = 
		servicoPermissaoTelaDepartamento.buscaPermissoes(usuario.getDepartamento());

	for (PermissaoTelaDepartamento permissaoTelaDepartamento : permissoes) {
	    if (permissaoTelaDepartamento.isPermiteIncluir()) {
		adicionaAcao(acoes, permissaoTelaDepartamento, "_INCLUIR");
	    }
	    if (permissaoTelaDepartamento.isPermiteVisualizar()) {
		adicionaAcao(acoes, permissaoTelaDepartamento, "_VISUALIZAR");
	    }
	    if (permissaoTelaDepartamento.isPermiteEditar()) {
		adicionaAcao(acoes, permissaoTelaDepartamento, "_EDITAR");
	    }
	    if (permissaoTelaDepartamento.isPermiteExcluir()) {
		adicionaAcao(acoes, permissaoTelaDepartamento, "_EXCLUIR");
	    }
	}
	return acoes;
    }
    
    public void adicionaAcao(Collection<GrantedAuthority> acoes, 
	    PermissaoTelaDepartamento permissaoTelaDepartamento,
	    String acao) {
	acoes.add(new SimpleGrantedAuthority("ROLE_".concat(permissaoTelaDepartamento.getTela()
		.getCodigo()).concat(acao)));
    }

    public Collection<GrantedAuthority> preparaRegrasGerais(Usuario usuario) {
	Collection<GrantedAuthority> regras = new ArrayList<>();
	regras.add(new SimpleGrantedAuthority(Constants.ROLE_PREFFIX
		.concat(usuario.getDepartamento().getAuthority()
			.replaceAll(Constants.REGEX_ASC, "").toUpperCase())));
	return regras;
    }

}