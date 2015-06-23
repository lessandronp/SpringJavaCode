package com.solucaocriativa.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.solucaocriativa.dao.UsuarioDao;
import com.solucaocriativa.entidade.Usuario;
import com.solucaocriativa.service.ServicoUsuario;
import com.solucaocriativa.service.generico.impl.ServicoGenericoImpl;
import com.solucaocriativa.util.Criptografia;

@Service
public class ServicoUsuarioImpl extends ServicoGenericoImpl<Usuario, Long> 
	implements ServicoUsuario, UserDetailsService {

    private static final long serialVersionUID = 1L;
    private static Log log = LogFactory.getLog(ServicoUsuarioImpl.class);
    private UsuarioDao userDao;

    public ServicoUsuarioImpl() {
    }

    @Inject
    public ServicoUsuarioImpl(UsuarioDao userDao) {
	this.userDao = userDao;
	super.setGenericDAO(userDao);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Usuario buscaUsuarioAutenticado() {
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	if (authentication.getPrincipal() instanceof UserDetails) {
	    Usuario usuarioAutenticado = (Usuario) authentication.getPrincipal();
	    return usuarioAutenticado;
	}
	return null;
    }
    
    @Override
    public Usuario updateAuthenticatedUser(Usuario user) {
	Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), 
		user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);	
        return user;
    }
    
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Usuario findUser(String loginEmail) {
	if (loginEmail != null && !loginEmail.isEmpty()) {
	    return userDao.findUser(loginEmail);
	}
	return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Usuario findUser(String loginEmail, Long id) {
	return userDao.buscaUsuario(loginEmail, id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Usuario findUser(String loginEmail, String password) throws Exception {
	return userDao.buscaUsuario(loginEmail, Criptografia.encrypt(password));
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Usuario saveUser(Usuario user) throws IOException, 
    	PersistenceException {
	Usuario userSaved = null;
	try {
		user.setSenha(Criptografia.encrypt(user.getPassword()));
		userSaved = this.userDao.save(user);
	} catch(RollbackException e) {
	    log.error(e.getMessage());
	    throw new PersistenceException(e.getMessage());
	} catch (Exception e) {
	    log.error(e.getMessage());
	}
	return userSaved;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Usuario atualizaUsuario(Usuario user) throws IOException {
	Usuario userUpdated = null;
	try {
		user.setSenha(Criptografia.encrypt(user.getPassword()));
		userUpdated = this.userDao.update(user);
		this.updateAuthenticatedUser(userUpdated);
	} catch (Exception e) {
	    log.error(e.getMessage());
	}
	return userUpdated;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public int numeroUsuarios() {
	return userDao.numeroUsuarios();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Usuario> carregaComPaginacao(int page, int rowsPerPage) {
	return userDao.buscaComPaginacao(page, rowsPerPage);
    }
    
    @Override
    public Usuario loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.findUser(s);
    }
}
