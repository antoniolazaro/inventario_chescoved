package br.com.vortice.chescoved.inventario.estoquearc.business;

import java.util.ArrayList;
import java.util.List;

import br.com.vortice.chescoved.inventario.estoquearc.dao.PepsDAO;
import br.com.vortice.chescoved.inventario.model.PepsModel;
import br.com.vortice.chescoved.inventario.model.ProdutoModel;

public class PepsBusiness{
	
	private PepsDAO pepsDAO;
	
	public PepsBusiness() {
		this.pepsDAO = new PepsDAO();
	}
	
	public List<PepsModel> projetarPeps(ProdutoModel filtro) throws Exception{
		List<PepsModel> listaCurvaAbc = pepsDAO.selectMovimentacaoProduto(filtro);
		if(listaCurvaAbc.size() > 0){
			List<PepsModel> listaPepsFinal = new ArrayList<PepsModel>();
			for(PepsModel pepsModel:listaCurvaAbc){
				Integer totalSaidaNota = pepsDAO.selectTotalSaidaByNotaFiscal(pepsModel);
				int quantidadeFinal = pepsModel.getQuantidade()-totalSaidaNota;
				if(quantidadeFinal > 0){
					pepsModel.setQuantidadeRestante(quantidadeFinal);	
					listaPepsFinal.add(pepsModel);
				}
			}
			if(listaPepsFinal.size() == 0){
				throw new Exception("Produto está com estoque zerado.");
			}
			return listaPepsFinal;
		}else{
			throw new Exception("Não foi encontrada nota fiscal de entrada para esse produto.");
		}
	}

	
}
