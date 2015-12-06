package br.com.vortice.chescoved.inventario.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.vortice.chescoved.inventario.dao.CurvaAbcDAO;
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
			BigDecimal valorTotalMovimentacoes = new BigDecimal(0);
			for(CurvaAbcModel curvaAbc:listaCurvaAbc){
				valorTotalMovimentacoes.add(curvaAbc.getValorTotal());
			}
			
			List<CurvaAbcModel> listaCurvaAbcFinal = new ArrayList<CurvaAbcModel>();
			ProdutoModel produto = listaCurvaAbc.get(0).getProduto();
			Long codigoProdutoAtual = produto.getCodigo();
			Integer quantidadeTotal = 0;
			BigDecimal valorTotal = new BigDecimal(0);
			for(CurvaAbcModel curvaAbc:listaCurvaAbc){
				if(!codigoProdutoAtual.equals(produto.getCodigo())){
					CurvaAbcModel curvaAbcAgrupado = new CurvaAbcModel();
					curvaAbcAgrupado.setProduto(curvaAbc.getProduto());
					curvaAbcAgrupado.setQuantidade(quantidadeTotal);
					curvaAbcAgrupado.setValorUnitario(valorTotal);
					listaCurvaAbcFinal.add(curvaAbc);
					quantidadeTotal = 0;
					valorTotal = new BigDecimal(0);
				}
				quantidadeTotal+=curvaAbc.getQuantidade();
				valorTotal.add(curvaAbc.getValorUnitario());
			}
			return listaCurvaAbcFinal;		
		}else{
			throw new Exception("Não há movimentação para esse período.");
		}
	}

	
}
