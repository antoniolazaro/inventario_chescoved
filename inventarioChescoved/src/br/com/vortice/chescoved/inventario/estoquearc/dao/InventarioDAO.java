package br.com.vortice.chescoved.inventario.estoquearc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import br.com.vortice.chescoved.inventario.dao.DAOAb;
import br.com.vortice.chescoved.inventario.model.InventarioModel;
import br.com.vortice.chescoved.inventario.model.InventarioProdutoModel;

public class InventarioDAO extends DAOAb {
			
	public void insert(InventarioModel inventario) throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        String sql = "insert into inventario_arc(data_inventario) values(?)";
        try {
        	stmt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        	
        	int i = 1;
        	stmt.setTimestamp(i++, new Timestamp(inventario.getDataInventario().getTime()));
        	stmt.executeUpdate();
        	
        	ResultSet rs = stmt.getGeneratedKeys();
        	if(rs.next()){
        		inventario.setCodigo(rs.getLong(1));
        	}
        	rs.close();
        	
        }catch (Exception e) {
        	throw e;
        }finally {
        	stmt.close();
            connection.close();
        }
	}
	
	public void insert(List<InventarioProdutoModel> produtosInventario) throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        String sql = "insert into inventario_produto_arc(inventario_codigo,produto_codigo,quantidade,quantidade_estoque,divergencia,quantidade_final) values(?,?,?,?,?,?)";
        try {
        	stmt = connection.prepareStatement(sql);
        	
        	
        	for(InventarioProdutoModel inventarioProduto:produtosInventario){
        		int i = 1;
        		stmt.setLong(i++, inventarioProduto.getInventario().getCodigo());
        		stmt.setLong(i++, inventarioProduto.getProduto().getCodigo());
        		stmt.setInt(i++, inventarioProduto.getQuantidade());
        		stmt.setInt(i++, inventarioProduto.getQuantidadeEstoque());
        		stmt.setInt(i++, inventarioProduto.getQuantidadeDivergencia());
        		stmt.setInt(i++, inventarioProduto.getQuantidadeFinal());
        		stmt.addBatch();
        	}
        	
        	stmt.executeBatch();
        	
        	
        }catch (Exception e) {
        	throw e;
        }finally {
        	stmt.close();
            connection.close();
        }
	}
	
}
