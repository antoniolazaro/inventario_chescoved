package br.com.vortice.chescoved.inventario.business;

import java.util.List;

import br.com.vortice.chescoved.inventario.dao.ProdutoDAO;
import br.com.vortice.chescoved.inventario.model.ProdutoModel;

public class ProdutoBusiness{
	
	private ProdutoDAO produtoDAO;
	
	public ProdutoBusiness() {
		this.produtoDAO = new ProdutoDAO();	}

	
	public List<ProdutoModel> selectAll() throws Exception{	
		return produtoDAO.selectAll();
	}
	
	public boolean insertOrUpdate(ProdutoModel produto) throws Exception{
		ProdutoModel produtoModel = produtoDAO.selectProductByCode(produto);
		boolean isInsert = true;
		if(produtoModel == null){
			produtoDAO.insert(produto);
		}else{
			isInsert = false;
			produtoDAO.update(produto);
		}
		return isInsert;
	}
	
	public void atualizarQuantidade(ProdutoModel produto) throws Exception{
		produtoDAO.atualizarQuantidade(produto);
	}
	

	
}
