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

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((nome == null) ? 0 : nome.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Departamento other = (Departamento) obj;
	if (nome == null) {
	    if (other.nome != null)
		return false;
	} else if (!nome.equals(other.nome))
	    return false;
	return true;
    }

}
