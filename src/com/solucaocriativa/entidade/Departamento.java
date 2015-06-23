package com.solucaocriativa.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity(name = "departamento")
@Table(name = "departamento")
public class Departamento implements Serializable, GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "departamentoSeq", sequenceName = "DEPARTAMENTO_SEQ", 
    	allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departamentoSeq")
    @Column(name = "id_departamento", nullable = false, updatable = false)
    private Long id;

    @Column(name = "nome", nullable = false, length = 20)
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }
    
    @Override
    public String getAuthority() {
	return this.getNome();
    }

}
