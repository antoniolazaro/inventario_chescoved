package br.com.vortice.chescoved.inventario.estoqueprincipal.business;

import java.util.ArrayList;
import java.util.List;

import br.com.vortice.chescoved.inventario.estoqueprincipal.dao.InventarioDAO;
import br.com.vortice.chescoved.inventario.model.InventarioModel;
import br.com.vortice.chescoved.inventario.model.InventarioProdutoModel;
import br.com.vortice.chescoved.inventario.model.ProdutoModel;

public class InventarioBusiness{
	
	private InventarioDAO inventarioDAO;
	private ProdutoBusiness produtoBusiness;
	
	public InventarioBusiness() {
		this.inventarioDAO = new InventarioDAO();
		this.produtoBusiness = new ProdutoBusiness();
	}

	public void insert(InventarioModel inventario) throws Exception{
		inventarioDAO.insert(inventario);
		for(InventarioProdutoModel inventarioProduto:inventario.getListaProdutos()){
			if(inventarioProduto.getQuantidadeDivergencia() == 0){
				inventarioProduto.setQuantidadeFinal(inventarioProduto.getQuantidadeEstoque());
			}else{
				inventarioProduto.setQuantidadeFinal(inventarioProduto.getQuantidade());
			}
		}
		inventarioDAO.insert(inventario.getListaProdutos());
		for(InventarioProdutoModel inventarioProduto:inventario.getListaProdutos()){
			inventarioProduto.getProduto().setQuantidade(inventarioProduto.getQuantidadeFinal());
			produtoBusiness.atualizarQuantidade(inventarioProduto.getProduto());
		}
	}
	
	public List<InventarioProdutoModel> findAllProdutos(InventarioModel inventario) throws Exception{
		List<ProdutoModel> listaProdutos = produtoBusiness.selectAll();
		List<InventarioProdutoModel> lista = new ArrayList<InventarioProdutoModel>();
		for(ProdutoModel produto: listaProdutos){
			InventarioProdutoModel inventarioProdutoModel = new InventarioProdutoModel();
			inventarioProdutoModel.setQuantidadeEstoque(produto.getQuantidade());
//			inventarioProdutoModel.setQuantidadeFinal(produto.getQuantidade().intValue());
			inventarioProdutoModel.setProduto(produto);
			inventarioProdutoModel.setInventario(inventario);
			lista.add(inventarioProdutoModel);
		}
		return lista;
	}

	
}
