package br.com.vortice.chescoved.inventario.estoqueprincipal.business;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.vortice.chescoved.inventario.estoqueprincipal.dao.CurvaAbcDAO;
import br.com.vortice.chescoved.inventario.model.CurvaAbcModel;
import br.com.vortice.chescoved.inventario.model.ProdutoModel;

public class CurvaAbcBusiness{
	
	private CurvaAbcDAO curvaAbcDAO;
	
	public CurvaAbcBusiness() {
		this.curvaAbcDAO = new CurvaAbcDAO();
	}
	
	public List<CurvaAbcModel> calcularCurvaABC(CurvaAbcModel curvaAbcFiltro) throws Exception{
		List<CurvaAbcModel> listaCurvaAbc = curvaAbcDAO.selectMovimentacaoSaidaPorPeriodo(curvaAbcFiltro);
		if(listaCurvaAbc.size() > 0){
			BigDecimal valorTotalMovimentacoes = calcularValorTotal(listaCurvaAbc);
			List<CurvaAbcModel> listaProdutosAgrupadosPorMovimentacaoSaida = calcularValorAgrupados(listaCurvaAbc);
			List<CurvaAbcModel> listaProdutosFinais = new ArrayList<CurvaAbcModel>();
			for(CurvaAbcModel curvaAbcCalculoPercentual:listaProdutosAgrupadosPorMovimentacaoSaida){
				curvaAbcCalculoPercentual.setPercentualSaida(calcularValorPercentual(curvaAbcCalculoPercentual,valorTotalMovimentacoes)+" %");
				listaProdutosFinais.add(curvaAbcCalculoPercentual);
			}
			
			return listaProdutosFinais;		
		}else{
			throw new Exception("Não há movimentação para esse período.");
		}
	}

	protected List<CurvaAbcModel> calcularValorAgrupados(List<CurvaAbcModel> listaCurvaAbc) {
		List<CurvaAbcModel> listaProdutosAgrupadosPorMovimentacaoSaida = new ArrayList<CurvaAbcModel>();
		if(listaCurvaAbc.size() > 0){
			ProdutoModel produto = null;
			Integer quantidadeTotal = 0;
			BigDecimal valorTotal = new BigDecimal(0);
			
			Iterator<CurvaAbcModel> it = listaCurvaAbc.iterator();
			do{
				CurvaAbcModel curvaAbcAgrupado = new CurvaAbcModel();
				CurvaAbcModel curvaAbc = it.next();
				if(produto == null){
					produto = curvaAbc.getProduto();
					quantidadeTotal = 0;
					valorTotal = new BigDecimal(0);
				}
				if(curvaAbc.getProduto().equals(produto)){
					quantidadeTotal+=curvaAbc.getQuantidade();
					valorTotal = valorTotal.add(curvaAbc.getValorUnitario());	
				}else{
					curvaAbcAgrupado.setProduto(produto);
					curvaAbcAgrupado.setQuantidade(quantidadeTotal);
					curvaAbcAgrupado.setValorUnitario(produto.getValorCusto());
					curvaAbcAgrupado.setValorTotal(produto.getValorCusto().multiply(new BigDecimal(quantidadeTotal)));
					listaProdutosAgrupadosPorMovimentacaoSaida.add(curvaAbcAgrupado);
					quantidadeTotal = 0;
					valorTotal = new BigDecimal(0);
					produto = curvaAbc.getProduto();
					quantidadeTotal+=curvaAbc.getQuantidade();
					valorTotal = valorTotal.add(curvaAbc.getValorUnitario());
				}
				if(!it.hasNext()){
					curvaAbcAgrupado.setProduto(produto);
					curvaAbcAgrupado.setQuantidade(quantidadeTotal);
					curvaAbcAgrupado.setValorUnitario(produto.getValorCusto());
					curvaAbcAgrupado.setValorTotal(produto.getValorCusto().multiply(new BigDecimal(quantidadeTotal)));
					listaProdutosAgrupadosPorMovimentacaoSaida.add(curvaAbcAgrupado);
					produto = curvaAbc.getProduto();
					quantidadeTotal+=curvaAbc.getQuantidade();
					valorTotal = valorTotal.add(curvaAbc.getValorUnitario());
				}
			}while(it.hasNext());	
			
		}
		return listaProdutosAgrupadosPorMovimentacaoSaida;
	}

	protected BigDecimal calcularValorTotal(List<CurvaAbcModel> listaCurvaAbc) {
		BigDecimal valorTotalMovimentacoes = new BigDecimal(0);
		for(CurvaAbcModel curvaAbc:listaCurvaAbc){
			valorTotalMovimentacoes = valorTotalMovimentacoes.add(curvaAbc.getValorUnitario().multiply(new BigDecimal(curvaAbc.getQuantidade())));
		}
		return valorTotalMovimentacoes;
	}
	
	private String calcularValorPercentual(CurvaAbcModel curvaAbc,BigDecimal valorTotalMovimentacoes){
		BigDecimal valorTotal = curvaAbc.getValorTotal();
		BigDecimal valorBruto = valorTotal.divide(valorTotalMovimentacoes,5, RoundingMode.CEILING);
		BigDecimal valorFinal = valorBruto.multiply(new BigDecimal(100));
		return valorFinal.toString();
	}

	
}
