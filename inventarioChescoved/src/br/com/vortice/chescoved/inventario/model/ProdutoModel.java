package br.com.vortice.chescoved.inventario.model;

import java.math.BigDecimal;

public class ProdutoModel {
	
	private Long codigo;
	private String nome;
	private String localEstoque;
	private BigDecimal valorCusto;
	private BigDecimal valorVenda;
	private Integer quantidade;
	
	public ProdutoModel(Long codigo,String nome, String localEstoque, BigDecimal valorCusto, BigDecimal valorVenda) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.localEstoque = localEstoque;
		this.valorCusto = valorCusto;
		this.valorVenda = valorVenda;
	}
	
	public ProdutoModel(Long codigo	) {
		super();
		this.codigo = codigo;
	}
	
	public ProdutoModel() {
		super();
	}
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getLocalEstoque() {
		return localEstoque;
	}
	public void setLocalEstoque(String localEstoque) {
		this.localEstoque = localEstoque;
	}
	public BigDecimal getValorCusto() {
		return valorCusto;
	}
	public void setValorCusto(BigDecimal valorCusto) {
		this.valorCusto = valorCusto;
	}
	public BigDecimal getValorVenda() {
		return valorVenda;
	}
	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}
	public Integer getQuantidade() {
		if(quantidade == null){
			return 0;
		}
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public BigDecimal getTotalCusto(){
		if(valorCusto != null && quantidade != null){
			return valorCusto.multiply(new BigDecimal(quantidade));
		}else{
			return new BigDecimal(0);
		}
	}
	
	public BigDecimal getTotalVenda(){
		if(valorVenda != null && quantidade != null){
			return valorVenda.multiply(new BigDecimal(quantidade));
		}else{
			return new BigDecimal(0);
		}
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
		ProdutoModel other = (ProdutoModel) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return getCodigo().toString();
	}


}
