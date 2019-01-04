package br.com.edson.cloud.dto;

import java.math.BigDecimal;

import br.com.edson.cloud.enums.Risco;

public class ClienteDTO {

	private Long id;

	private String nome;

	private BigDecimal limCredito;

	private Risco risco;

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
		final ClienteDTO other = (ClienteDTO) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ClienteDTO [id=" + id + ", nome=" + nome + ", limCredito=" + limCredito + ", risco=" + risco
				+ ", taxaRisco=" + taxaRisco + "]";
	}
	
	
}
