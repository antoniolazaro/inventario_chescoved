package br.com.vortice.chescoved.inventario.estoqueprincipal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.vortice.chescoved.inventario.dao.DAOAb;
import br.com.vortice.chescoved.inventario.model.PepsModel;
import br.com.vortice.chescoved.inventario.model.ProdutoModel;

public class PepsDAO extends DAOAb {
	
	public List<PepsModel> selectMovimentacaoProduto(ProdutoModel produtoMovel) throws Exception{
        Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        StringBuilder sql = new StringBuilder("SELECT mp.data_movimentacao,mp.produto_codigo,mp.quantidade,mp.nota_fiscal,produto.nome,mp.data_recebimento_produto  ");
        sql.append(" from movimentacao_produto mp, produto ")
        .append(" where mp.produto_codigo = produto.codigo ")
        .append(" and mp.tipo_movimentacao_codigo = 1 ")
        .append(" and mp.produto_codigo = ? ")
        .append(" order by mp.data_recebimento_produto,mp.nota_fiscal ");
        
        List<PepsModel> lista = new ArrayList<PepsModel>();
        try {
        	stmt = connection.prepareStatement(sql.toString());
        	
        	int index = 1;
        	stmt.setLong(index++, produtoMovel.getCodigo());
        	
            rs = stmt.executeQuery();
    
            while (rs.next()) {
            	PepsModel pepsModel = new PepsModel();
            	pepsModel.setProduto(new ProdutoModel(rs.getLong("produto_codigo")));
            	pepsModel.setQuantidade(rs.getInt("quantidade"));
            	pepsModel.setNotaFiscal(rs.getString("nota_fiscal"));
            	pepsModel.setDataMovimentacao(rs.getTimestamp("data_movimentacao"));
            	pepsModel.setDataRecebimento(rs.getTimestamp("data_recebimento_produto"));
            	pepsModel.getProduto().setNome(rs.getString("nome"));
            	lista.add(pepsModel);
            }
        }catch (Exception e) {
        	throw e;
        }finally {
        	rs.close();
        	stmt.close();
            connection.close();
        }
		return lista;
	}
	
	public Integer selectTotalSaidaByNotaFiscal(PepsModel pepsModel) throws Exception{
        Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        StringBuilder sql = new StringBuilder("SELECT sum(mp.quantidade) as total  ");
        sql.append(" from movimentacao_produto mp, produto ")
        .append(" where mp.produto_codigo = produto.codigo ")
        .append(" and mp.tipo_movimentacao_codigo = 2 ")
        .append(" and mp.produto_codigo = ? ")
        .append(" and mp.nota_fiscal = ? ")
        .append(" order by mp.data_recebimento_produto,mp.nota_fiscal ");
        
        try {
        	stmt = connection.prepareStatement(sql.toString());
        	
        	int index = 1;
        	stmt.setLong(index++, pepsModel.getProduto().getCodigo());
        	stmt.setString(index++, pepsModel.getNotaFiscal());
        	
            rs = stmt.executeQuery();
    
            if (rs.next()) {
            	return rs.getInt("total");
            }
        }catch (Exception e) {
        	throw e;
        }finally {
        	rs.close();
        	stmt.close();
            connection.close();
        }
		return 0;
	}
	
	
}
