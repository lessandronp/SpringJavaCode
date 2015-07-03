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
    private boolean permiteExcluir;

    @Column(name = "permite_editar", nullable = false)
    private boolean permiteEditar;

    @Column(name = "permite_visualizar", nullable = false)
    private boolean permiteVisualizar;

    @JoinColumn(name = "id_depto", referencedColumnName = "id_departamento", 
	    nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Departamento departamento;

    @JoinColumn(name = "id_tela", referencedColumnName = "id_tela", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Tela tela;

    public boolean isPermiteIncluir() {
	return permiteIncluir;
    }

    public void setPermiteIncluir(boolean permiteIncluir) {
	this.permiteIncluir = permiteIncluir;
    }

    public boolean isPermiteExcluir() {
        return permiteExcluir;
    }

    public void setPermiteExcluir(boolean permiteExcluir) {
        this.permiteExcluir = permiteExcluir;
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

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result
		+ ((departamento == null) ? 0 : departamento.hashCode());
	result = prime * result + (permiteEditar ? 1231 : 1237);
	result = prime * result + (permiteExcluir ? 1231 : 1237);
	result = prime * result + (permiteIncluir ? 1231 : 1237);
	result = prime * result + (permiteVisualizar ? 1231 : 1237);
	result = prime * result + ((tela == null) ? 0 : tela.hashCode());
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
	PermissaoTelaDepartamento other = (PermissaoTelaDepartamento) obj;
	if (departamento == null) {
	    if (other.departamento != null)
		return false;
	} else if (!departamento.equals(other.departamento))
	    return false;
	if (permiteEditar != other.permiteEditar)
	    return false;
	if (permiteExcluir != other.permiteExcluir)
	    return false;
	if (permiteIncluir != other.permiteIncluir)
	    return false;
	if (permiteVisualizar != other.permiteVisualizar)
	    return false;
	if (tela == null) {
	    if (other.tela != null)
		return false;
	} else if (!tela.equals(other.tela))
	    return false;
	return true;
    }
}
