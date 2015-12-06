package br.com.vortice.chescoved.inventario.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Types;

import br.com.vortice.chescoved.inventario.model.MovimentacaoEstoqueModel;

public class MovimentacaoEstoqueDAO extends DAOAb {
	
	public void insert(MovimentacaoEstoqueModel movimentacaoEstoque) throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        String sql = "insert into movimentacao_produto(tipo_movimentacao_codigo,produto_codigo,data_movimentacao,quantidade,nota_fiscal,data_recebimento_produto) values(?,?,?,?,?,?)";
        try {
        	stmt = connection.prepareStatement(sql);
        	
        	int i = 1;
        	stmt.setLong(i++, movimentacaoEstoque.getTipoMovimentacaoEstoque().getCodigo());
        	stmt.setLong(i++, movimentacaoEstoque.getProduto().getCodigo());
        	stmt.setTimestamp(i++, new Timestamp(movimentacaoEstoque.getDataMovimentacao().getTime()));
        	stmt.setInt(i++, movimentacaoEstoque.getQuantidade());
        	stmt.setString(i++, movimentacaoEstoque.getNotaFiscal());
        	if(movimentacaoEstoque.getDataRecebimento() != null){
        		stmt.setTimestamp(i++, new Timestamp(movimentacaoEstoque.getDataRecebimento().getTime()));
        	}else{
        		stmt.setNull(i++, Types.TIMESTAMP);
        	}
        	stmt.executeUpdate();
        	
        }catch (Exception e) {
        	throw e;
        }finally {
        	stmt.close();
            connection.close();
        }
	}
	
}
