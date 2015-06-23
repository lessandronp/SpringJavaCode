package com.solucaocriativa.dao;

import java.util.List;

import com.solucaocriativa.dao.generico.DaoGenerico;
import com.solucaocriativa.entidade.Usuario;

/**
 * Interface Dao de usuário.
 * @author Lessandro
 */
public interface UsuarioDao extends DaoGenerico<Usuario, Long> {

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
     */
    Usuario buscaUsuario(String loginEmail, String password);

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
    List<Usuario> buscaComPaginacao(int page, int rowsPerPage);

    /**
     * Recupera o usuário pelo login/e-mail e identificador
     * @param loginEmail Login ou e-mail
     * @param id Identificador
     * @return Usuário
     */
    Usuario buscaUsuario(String loginEmail, Long id);
}
