package com.solucaocriativa.entidade;

import java.util.Collection;

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

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

@Entity(name = "Usuario")
@Table(name = "usuario")
public class Usuario extends UsernamePasswordAuthenticationToken implements Cloneable { 

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

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_depto", referencedColumnName = "id_departamento", 
    	nullable = false)
    private Departamento departamento;


    public Usuario() {
	super(null, null, null);
    }

    public Usuario(Object principal, Object credentials, 
	    Collection<? extends GrantedAuthority> authorities, String nome,
	    String login, String senha, String senhaConfirmacao,
	    Departamento departamento) {
	super(principal, credentials, authorities);
	this.nome = nome;
	this.login = login;
	this.senha = senha;
	this.senhaConfirmacao = senhaConfirmacao;
	this.departamento = departamento;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
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

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
	return super.clone();
    }

}
