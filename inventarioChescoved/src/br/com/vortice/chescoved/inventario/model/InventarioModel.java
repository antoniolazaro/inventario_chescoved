package br.com.vortice.chescoved.inventario.model;

import java.util.Date;
import java.util.List;

public class InventarioModel {
	
	private Long codigo;
	private Date dataInventario;
	private List<InventarioProdutoModel> listaProdutos;
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public Date getDataInventario() {
		return dataInventario;
	}
	public void setDataInventario(Date dataInventario) {
		this.dataInventario = dataInventario;
	}
	public List<InventarioProdutoModel> getListaProdutos() {
		return listaProdutos;
	}
	public void setListaProdutos(List<InventarioProdutoModel> listaProdutos) {
		this.listaProdutos = listaProdutos;
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
		InventarioModel other = (InventarioModel) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
