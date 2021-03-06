package br.com.vortice.chescoved.inventario.model;

import java.math.BigDecimal;
import java.util.Date;

public class CurvaAbcModel {
	
	private String classificacaoCurvaABC;
	private String percentualSaida;
	private Date dataInicio;
	private Date dataFim;
	private ProdutoModel produto;
	private Integer quantidade;
	private BigDecimal valorUnitario;
	private BigDecimal valorTotal;
	
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
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	public String getClassificacaoCurvaABC() {
		return classificacaoCurvaABC;
	}
	public void setClassificacaoCurvaABC(String classificacaoCurvaABC) {
		this.classificacaoCurvaABC = classificacaoCurvaABC;
	}
	public String getPercentualSaida() {
		return percentualSaida;
	}
	public void setPercentualSaida(String percentualSaida) {
		this.percentualSaida = percentualSaida;
	}
}
