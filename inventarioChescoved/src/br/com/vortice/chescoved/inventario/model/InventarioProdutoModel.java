package br.com.vortice.chescoved.inventario.model;

import java.math.BigDecimal;

public class InventarioProdutoModel {
	
	private Long codigo;
	private InventarioModel inventario;
	private ProdutoModel produto;
	private Integer quantidade;
	private Integer quantidadeEstoque;
	private Integer quantidadeFinal;
	private Integer quantidadeDivergencia;
	private BigDecimal totalCusto;
	private BigDecimal totalVenda;
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public InventarioModel getInventario() {
		return inventario;
	}
	public void setInventario(InventarioModel inventario) {
		this.inventario = inventario;
	}
	public ProdutoModel getProduto() {
		return produto;
	}
	public void setProduto(ProdutoModel produto) {
		this.produto = produto;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}
	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}
		
	public Integer getQuantidadeFinal() {
		return quantidadeFinal;
	}
	public void setQuantidadeFinal(Integer quantidadeFinal) {
		this.quantidadeFinal = quantidadeFinal;
	}
	
	public Integer getQuantidadeDivergencia() {
		if(quantidadeEstoque != null && quantidade != null){
			return quantidade-quantidadeEstoque;
		}else if(quantidade != null){
			return quantidade;
		}else if(quantidadeEstoque != null){
			return quantidadeEstoque;
		}else{
			return 0;
		}
	}
	public BigDecimal getTotalCusto(){
		return totalCusto;
	}
	
	public BigDecimal getTotalVenda(){
		return totalVenda;
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
		InventarioProdutoModel other = (InventarioProdutoModel) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	public void setQuantidadeDivergencia(Integer quantidadeDivergencia) {
		this.quantidadeDivergencia = quantidadeDivergencia;
	}
	public void setTotalCusto(BigDecimal totalCusto) {
		this.totalCusto = totalCusto;
	}
	public void setTotalVenda(BigDecimal totalVenda) {
		this.totalVenda = totalVenda;
	}

}
