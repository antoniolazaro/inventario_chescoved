package br.com.vortice.chescoved.inventario.business;

import java.util.List;

import br.com.vortice.chescoved.inventario.dao.MovimentacaoEstoqueDAO;
import br.com.vortice.chescoved.inventario.model.MovimentacaoEstoqueModel;

public class MovimentacaoEstoqueBusiness{
	
	private MovimentacaoEstoqueDAO movimentacaoEstoqueDAO;
	private ProdutoBusiness produtoBusiness;
	
	public MovimentacaoEstoqueBusiness() {
		this.movimentacaoEstoqueDAO = new MovimentacaoEstoqueDAO();
		this.produtoBusiness = new ProdutoBusiness();
	}

	
	public void insert(MovimentacaoEstoqueModel movimentacaoEstoqueModel) throws Exception{
		movimentacaoEstoqueDAO.insert(movimentacaoEstoqueModel);
		movimentacaoEstoqueModel.getProduto().setQuantidade(movimentacaoEstoqueModel.getQuantidade());
		produtoBusiness.atualizarQuantidade(movimentacaoEstoqueModel.getProduto());
	}
	
	public List<MovimentacaoEstoqueModel> pesquisarMovimentacoes(MovimentacaoEstoqueModel filtro) throws Exception{
		return movimentacaoEstoqueDAO.pesquisarMovimentacoes(filtro);
	}

	
}
