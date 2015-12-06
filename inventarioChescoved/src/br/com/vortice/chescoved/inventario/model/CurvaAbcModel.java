package br.com.vortice.chescoved.inventario.model;

import java.math.BigDecimal;
import java.util.Date;

public class CurvaAbcModel {
	
	private String classificacaoCurvaABC;
	private Date dataInicio;
	private Date dataFim;
	private ProdutoModel produto;
	private Integer quantidade;
	private BigDecimal valorUnitario;
	
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
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
	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	public BigDecimal getValorTotal() {
		if(valorUnitario != null && quantidade != null){
			return valorUnitario.multiply(new BigDecimal(quantidade));
		}else{
			return new BigDecimal(0);
		}
	}
	public String getClassificacaoCurvaABC() {
		return classificacaoCurvaABC;
	}
	public void setClassificacaoCurvaABC(String classificacaoCurvaABC) {
		this.classificacaoCurvaABC = classificacaoCurvaABC;
	}
}
