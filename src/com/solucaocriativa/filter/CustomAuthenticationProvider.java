package com.solucaocriativa.filter;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.solucaocriativa.entidade.Usuario;
import com.solucaocriativa.service.ServicoUsuario;
import com.solucaocriativa.util.Constants;
import com.solucaocriativa.util.Criptografia;
import com.solucaocriativa.util.MessageUtil;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static Log log = LogFactory.getLog(CustomAuthenticationProvider.class);
    private Usuario usuario;
    
    @Autowired
    private ServicoUsuario usuarioService;

    @Override
    public Authentication authenticate(Authentication auth) {

	try {
	    if (!auth.getName().isEmpty()) {

		usuario = usuarioService.findUser(auth.getName());

		if (usuario != null) {
		    validatePassword(auth);
		    return validateNamePassword(auth);
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
    private Authentication validateNamePassword(Authentication auth) {
	if (auth.getName().equals(auth.getCredentials())) {
	    log.debug("O usuario informado e a senha sao os mesmos.");
	    throw new BadCredentialsException("O usuario informado e a senha sao os mesmos.");
	} else {
	UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
		new UsernamePasswordAuthenticationToken(usuario, 
		auth.getCredentials(), montaAuthorities(usuario));
	return usernamePasswordAuthenticationToken;
	}
    }

    private Collection<? extends GrantedAuthority> montaAuthorities(
	    Usuario usuario) {
        //	for(PermissaoGrupo pg :  grupoUsuario.getPermissoesGrupo() ) ){
        //	    String nomeTela = pg.getTela().getNome();
        //	    if(StatusQuestionType.YES.equals(pg.getPermiteIncluir())) {
        //		permissions.add(new SimpleGrantedAuthority("PERM_" + nomeTela +"_INSERIR") );
        //	    }
        //		    
        //	    
        //	}
	return preparaRegras(usuario.getAuthorities());
    }
    
    public Collection<GrantedAuthority> preparaRegras(Collection<GrantedAuthority> grantedAuthorities) {
	Collection<GrantedAuthority> regras = new ArrayList<>();
	for (GrantedAuthority grantedAuthority : grantedAuthorities) {
	    regras.add(new SimpleGrantedAuthority(Constants.ROLE_PREFFIX
		    .concat(grantedAuthority.getAuthority().replaceAll(Constants.REGEX_ASC, "")
			    .toUpperCase())));
	}
	return regras;
    }

    /**
     * Valida a senha do usuário.
     * @param auth Autenticação
     * @throws Exception
     */
    private void validatePassword(Authentication auth) throws Exception {
	try {
	    if (!Criptografia.encrypt((String) auth.getCredentials()).equals(usuario.getPassword())) {
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
}