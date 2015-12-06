package br.com.vortice.chescoved.inventario.model;

public class TipoMovimentacaoEstoqueModel {
	
	private Long codigo;
	private String nome;
	
	public TipoMovimentacaoEstoqueModel() {
		// TODO Auto-generated constructor stub
	}

	public TipoMovimentacaoEstoqueModel(Long codigo) {
		this.codigo = codigo;
	}
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		TipoMovimentacaoEstoqueModel other = (TipoMovimentacaoEstoqueModel) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return getNome().toString();
	}

}
