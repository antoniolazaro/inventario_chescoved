package br.com.vortice.chescoved.inventario.model;

import java.math.BigDecimal;

public class InventarioProdutoModel {
	
	private Long codigo;
	private InventarioModel inventario;
	private ProdutoModel produto;
	private Integer quantidade;
	private Integer quantidadeEstoque;
	private Integer quantidadeFinal;
	
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
			return quantidadeEstoque-quantidade;
		}else if(quantidade != null){
			return quantidade;
		}else if(quantidadeEstoque != null){
			return quantidadeEstoque;
		}else{
			return 0;
		}
	}
	public BigDecimal getTotalCusto(){
		if(produto.getValorCusto() != null && quantidadeEstoque != null){
			return produto.getValorCusto().multiply(new BigDecimal(quantidadeEstoque));
		}else{
			return new BigDecimal(0);
		}
	}
	
	public BigDecimal getTotalVenda(){
		if(produto.getValorVenda() != null && quantidadeEstoque != null){
			return produto.getValorVenda().multiply(new BigDecimal(quantidadeEstoque));
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
		InventarioProdutoModel other = (InventarioProdutoModel) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
