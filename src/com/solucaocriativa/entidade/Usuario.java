package com.solucaocriativa.entidade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name = "Usuario")
@Table(name = "usuario")
public class Usuario implements Cloneable, UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "usuarioSeq", sequenceName = "USUARIO_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuarioSeq")
    @Column(name = "id_usuario", nullable = false, updatable = false)
    private Long id;
    
    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "login", length = 20, nullable = false)
    private String login;

    @Column(name = "senha", length = 32, nullable = false)
    private String senha;

    @Transient
    private String senhaConfirmacao;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_depto", referencedColumnName = "id_departamento", 
    	nullable = false)
    private Departamento departamento;

    @Override
    public Object clone() throws CloneNotSupportedException {
	return super.clone();
    }

    @Override
    public String getUsername() {
	return login;
    }

    @Override
    public String getPassword() {
	return senha;
    }

    @Transient
    @Override
    public boolean isAccountNonExpired() {
	return Boolean.TRUE;
    }

    @Transient
    @Override
    public boolean isAccountNonLocked() {
	return Boolean.TRUE;
    }

    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
	return Boolean.TRUE;
    }

    @Transient
    @Override
    public boolean isEnabled() {
	return Boolean.TRUE;
    }

    @Transient
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
	List<GrantedAuthority> permissions = new ArrayList<GrantedAuthority>();
	permissions.add(new SimpleGrantedAuthority(departamento.getAuthority()));
	return permissions;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenhaConfirmacao() {
        return senhaConfirmacao;
    }

    public void setSenhaConfirmacao(String senhaConfirmacao) {
        this.senhaConfirmacao = senhaConfirmacao;
    }

    public Long getId() {
        return id;
    }
}
