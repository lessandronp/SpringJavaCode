package com.solucaocriativa.entidade;

import java.io.Serializable;

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

@Entity
@Table(name = "permissao_tela_dpto")
public class PermissaoTelaDepartamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "permissaoTelaDptoSeq", sequenceName = "PERMISSAO_TELA_DPTO_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permissaoTelaDptoSeq")
    @Column(name = "id_permissao_tela_dpto", nullable = false, updatable = false)
    private Long id;

    @Column(name = "permite_incluir", nullable = false)
    private boolean permiteIncluir;

    @Column(name = "permite_excluir", nullable = false)
    private boolean permiteExluir;

    @Column(name = "permite_editar", nullable = false)
    private boolean permiteEditar;

    @Column(name = "permite_visualizar", nullable = false)
    private boolean permiteVisualizar;

    @JoinColumn(name = "id_depto", referencedColumnName = "id_departamento", 
	    nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Departamento departamento;

    @JoinColumn(name = "id_tela", referencedColumnName = "id_tela", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Tela tela;

    public boolean isPermiteIncluir() {
	return permiteIncluir;
    }

    public void setPermiteIncluir(boolean permiteIncluir) {
	this.permiteIncluir = permiteIncluir;
    }

    public boolean isPermiteExluir() {
	return permiteExluir;
    }

    public void setPermiteExluir(boolean permiteExluir) {
	this.permiteExluir = permiteExluir;
    }

    public boolean isPermiteEditar() {
	return permiteEditar;
    }

    public void setPermiteEditar(boolean permiteEditar) {
	this.permiteEditar = permiteEditar;
    }

    public Tela getTela() {
	return tela;
    }

    public void setTela(Tela tela) {
	this.tela = tela;
    }

    public Long getId() {
        return id;
    }

    public boolean isPermiteVisualizar() {
        return permiteVisualizar;
    }

    public void setPermiteVisualizar(boolean permiteVisualizar) {
        this.permiteVisualizar = permiteVisualizar;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
}
