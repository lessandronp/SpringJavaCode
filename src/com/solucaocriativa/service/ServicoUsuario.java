package com.solucaocriativa.service;

import java.io.IOException;
import java.util.List;

import com.solucaocriativa.entidade.Usuario;
import com.solucaocriativa.service.generico.ServicoGenerico;

/**
 * Interface de serviço do usuário.
 * @author Lessandro
 */
public interface ServicoUsuario extends ServicoGenerico<Usuario, Long> {

    /**
     * Recupera o usuário autenticado.
     * @return Usuário autenticado
     */
    Usuario buscaUsuarioAutenticado();

    /**
     * Find user by login/e-mail
     * @param loginEmail Login or e-mail
     * @return User
     */
    Usuario findUser(String loginEmail);

    /**
     * Find user by login/e-mail and senha
     * @param loginEmail Login or e-mail
     * @param password Password
     * @return User
     * @throws Exception 
     */
    Usuario findUser(String loginEmail, String password) throws Exception;

    /**
     * Persiste o usuário.
     * @param user Usuário
     * @return Usuário
     * @throws DnsMailException
     * @throws UniqueKeyException 
     * @throws ValidationException 
     */
    Usuario saveUser(Usuario user) throws IOException;

    /**
     * Atualiza o usuário.
     * @param user Usuário
     * @return Usuário
     * @throws DnsMailException
     * @throws ValidationException 
     */
    Usuario atualizaUsuario(Usuario user) throws IOException;

    /**
     * Recupera o número de usuários.
     * @return Número de usuários
     */
    int numeroUsuarios();

    /**
     * Recupera os usuários com paginação.
     * @param page Número da página
     * @param rowsPerPage Registros por página
     * @return Usuários
     */
    List<Usuario> carregaComPaginacao(int page, int rowsPerPage);

    /**
     * Recupera o usuário pelo login/e-mail e identificador
     * @param loginEmail Login ou e-mail
     * @param id Identificador
     * @return Usuário
     */
    Usuario findUser(String loginEmail, Long id);

    /**
     * Atualiza o usuário autenticado.
     * @param user Usuário
     * @return Usuário
     */
    Usuario updateAuthenticatedUser(Usuario user);

}
