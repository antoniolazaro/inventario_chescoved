package br.com.vortice.chescoved.inventario.estoquearc.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import br.com.vortice.chescoved.inventario.dao.DAOAb;
import br.com.vortice.chescoved.inventario.model.CurvaAbcModel;
import br.com.vortice.chescoved.inventario.model.ProdutoModel;

public class CurvaAbcDAO extends DAOAb {
	
	public List<CurvaAbcModel> selectMovimentacaoSaidaPorPeriodo(CurvaAbcModel curvaAbcModel) throws Exception{
        Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
      
        StringBuilder sql = new StringBuilder("SELECT mp.produto_codigo,mp.quantidade,produto.valor_custo,produto.nome  ");
        sql.append(" from movimentacao_produto_arc mp, produto_arc produto ")
        .append(" where mp.produto_codigo = produto.codigo ")
        .append(" and mp.data_movimentacao >= ? and mp.data_movimentacao <= ? ")
        .append(" and mp.tipo_movimentacao_codigo = 2 ")
        .append(" order by mp.produto_codigo,produto.valor_custo ");
        
        List<CurvaAbcModel> lista = new ArrayList<CurvaAbcModel>();
        try {
        	stmt = connection.prepareStatement(sql.toString());
        	
        	int index = 1;
        	stmt.setTimestamp(index++, new Timestamp(curvaAbcModel.getDataInicio().getTime()));
        	stmt.setTimestamp(index++, new Timestamp(curvaAbcModel.getDataFim().getTime()));
        	
            rs = stmt.executeQuery();
    
            while (rs.next()) {
            	CurvaAbcModel curvaAbc = new CurvaAbcModel();
            	curvaAbc.setProduto(new ProdutoModel(rs.getLong("produto_codigo")));
            	curvaAbc.setQuantidade(rs.getInt("quantidade"));
            	BigDecimal valorCusto = rs.getBigDecimal("valor_custo");
            	curvaAbc.getProduto().setValorCusto(valorCusto);
            	curvaAbc.setValorUnitario(valorCusto);
            	curvaAbc.getProduto().setNome(rs.getString("nome"));
            	lista.add(curvaAbc);
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
	
	
}
