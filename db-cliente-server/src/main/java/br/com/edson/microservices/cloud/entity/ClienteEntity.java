package br.com.edson.microservices.cloud.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mechamoedson.cloud.enums.Risco;

@Entity(name = "cliente")
@Table(name = "cliente_tbl", catalog = "cliente_microservice")
public class ClienteEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "lim_credito", nullable = false)
	private BigDecimal limCredito;

	@Enumerated(EnumType.STRING)
	@Column(name = "risco", columnDefinition = "ENUM('A', 'B', 'C')", nullable = false)
	private Risco risco;

	@Column(name = "taxa_risco")
	private double taxaRisco;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getLimCredito() {
		return limCredito;
	}

	public void setLimCredito(BigDecimal limCredito) {
		this.limCredito = limCredito;
	}

	public Risco getRisco() {
		return risco;
	}

	public void setRisco(Risco risco) {
		this.risco = risco;
	}

	public double getTaxaRisco() {
		return taxaRisco;
	}

	public void setTaxaRisco(double taxaRisco) {
		this.taxaRisco = taxaRisco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ClienteEntity other = (ClienteEntity) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
