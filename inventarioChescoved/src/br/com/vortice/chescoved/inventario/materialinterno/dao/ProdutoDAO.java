package br.com.vortice.chescoved.inventario.materialinterno.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.vortice.chescoved.inventario.dao.DAOAb;
import br.com.vortice.chescoved.inventario.model.ProdutoModel;

public class ProdutoDAO extends DAOAb {
	
	private static final String SELECT_BASE = "SELECT nome,codigo,local_estoque,valor_custo,valor_venda,quantidade FROM PRODUTO_MI ";

	public ProdutoModel selectProductByCode(ProdutoModel produto) throws Exception{
        Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
      
        String sql = SELECT_BASE+" where codigo = ? order by codigo";
        try {
        	stmt = connection.prepareStatement(sql);
        	stmt.setLong(1, produto.getCodigo());
            rs = stmt.executeQuery();
            if (rs.next()) {
            	ProdutoModel produtoNovo = new ProdutoModel();
            	produtoNovo.setCodigo(rs.getLong("codigo"));
            	produtoNovo.setNome(rs.getString("nome"));
            	produtoNovo.setLocalEstoque(rs.getString("local_estoque"));
            	produtoNovo.setValorCusto(rs.getBigDecimal("valor_custo"));
            	produtoNovo.setValorVenda(rs.getBigDecimal("valor_venda"));
            	produtoNovo.setQuantidade(rs.getInt("quantidade"));
            	return produtoNovo;
            }
        }catch (Exception e) {
        	throw e;
        }finally {
        	rs.close();
        	stmt.close();
            connection.close();
        }
		return null;
	}
	
	public List<ProdutoModel> selectAll() throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
      
        String sql = SELECT_BASE+" order by nome";
        try {
        	stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            List<ProdutoModel> listaPalletes = new ArrayList<ProdutoModel>();
            while (rs.next()) {
            	ProdutoModel produtoNovo = new ProdutoModel();
            	produtoNovo.setCodigo(rs.getLong("codigo"));
            	produtoNovo.setNome(rs.getString("nome"));
            	produtoNovo.setLocalEstoque(rs.getString("local_estoque"));
            	produtoNovo.setValorCusto(rs.getBigDecimal("valor_custo"));
            	produtoNovo.setValorVenda(rs.getBigDecimal("valor_venda"));
            	produtoNovo.setQuantidade(rs.getInt("quantidade"));
            	listaPalletes.add(produtoNovo);
            }
        	return listaPalletes;
        }catch (Exception e) {
        	throw e;
        }finally {
        	rs.close();
        	stmt.close();
            connection.close();
        }
	}
	
	public void insert(ProdutoModel produto) throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        String sql = "insert into produto_MI(codigo,nome,local_estoque,valor_custo,valor_venda,quantidade) values(?,?,?,?,?,?)";
        try {
        	stmt = connection.prepareStatement(sql);
        	
        	int i = 1;
        	stmt.setLong(i++, produto.getCodigo());
        	stmt.setString(i++, produto.getNome().trim().toUpperCase());
        	stmt.setString(i++, produto.getLocalEstoque().toUpperCase());
        	stmt.setBigDecimal(i++, produto.getValorCusto());
        	stmt.setBigDecimal(i++, produto.getValorVenda());
        	stmt.setInt(i++, produto.getQuantidade());

        	stmt.executeUpdate();
        	
        }catch (Exception e) {
        	if(e.getMessage().contains("NOME_PRODUTO_MI_UNIQUE")){
				throw new Exception("Nome já existe associado a outro produto.");
			}
			throw e;
        }finally {
        	stmt.close();
            connection.close();
        }
	}
	
	public void atualizarQuantidade(ProdutoModel produto) throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        String sql = "UPDATE  produto_MI SET quantidade = ? WHERE CODIGO = ?";
        try {
        	stmt = connection.prepareStatement(sql);
        	
        	int i = 1;
        	stmt.setInt(i++, produto.getQuantidade());
        	stmt.setLong(i++, produto.getCodigo());
        	
        	stmt.executeUpdate();
        	
        }catch (Exception e) {
        	throw e;
        }finally {
        	stmt.close();
            connection.close();
        }
	}
	
	public void update(ProdutoModel produto) throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        String sql = "UPDATE  produto_MI SET nome = ?,local_estoque=?,valor_custo=?,valor_venda=? WHERE CODIGO = ?";
        try {
        	stmt = connection.prepareStatement(sql);
        	
        	int i = 1;
        	stmt.setString(i++, produto.getNome().trim().toUpperCase());
        	stmt.setString(i++, produto.getLocalEstoque().toUpperCase());
        	stmt.setBigDecimal(i++, produto.getValorCusto());
        	stmt.setBigDecimal(i++, produto.getValorVenda());
        	stmt.setLong(i++, produto.getCodigo());
        	
        	stmt.executeUpdate();
        	
        }catch (Exception e) {
        	throw e;
        }finally {
        	stmt.close();
            connection.close();
        }
	}
	
}
