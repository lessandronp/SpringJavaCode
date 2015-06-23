package com.solucaocriativa.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "tela")
@Table(name = "tela")
public class Tela implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "telaSeq", sequenceName = "TELA_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "telaSeq")
    @Column(name = "id_tela", nullable = false, updatable = false)
    private Long id;

    @Column(name = "nome", nullable = false, length = 30)
    private String nome;

    @Column(name = "url", nullable = false, length = 255)
    private String url;

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public Long getId() {
        return id;
    }
}
