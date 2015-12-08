package br.com.vortice.chescoved.inventario.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import br.com.vortice.chescoved.inventario.model.MovimentacaoEstoqueModel;
import br.com.vortice.chescoved.inventario.model.ProdutoModel;
import br.com.vortice.chescoved.inventario.model.TipoMovimentacaoEstoqueModel;

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
	
	public List<MovimentacaoEstoqueModel> pesquisarMovimentacoes(MovimentacaoEstoqueModel filtro) throws Exception{
        Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
      
        StringBuilder sql = new StringBuilder("SELECT mp.produto_codigo,mp.quantidade,produto.valor_custo,produto.nome,mp.nota_fiscal, ");
        sql.append(" mp.tipo_movimentacao_codigo,tipo_movimentacao.nome as nome_movimentacao,mp.data_movimentacao,mp.data_recebimento_produto ")
        .append(" from movimentacao_produto mp, produto,tipo_movimentacao ")
        .append(" where mp.produto_codigo = produto.codigo ")
        .append(" and (mp.produto_codigo = ? or  ? is null) ")
        .append(" and mp.data_movimentacao >= ? and mp.data_movimentacao <= ? ")
        .append(" and mp.tipo_movimentacao_codigo = tipo_movimentacao.codigo ")
        .append(" order by mp.produto_codigo,produto.valor_custo ");
        
        List<MovimentacaoEstoqueModel> lista = new ArrayList<MovimentacaoEstoqueModel>();
        try {
        	stmt = connection.prepareStatement(sql.toString());
        	
        	int index = 1;
        	if(filtro.getProduto() != null){
        		stmt.setLong(index++, filtro.getProduto().getCodigo());
        		stmt.setLong(index++, filtro.getProduto().getCodigo());
        	}else{
        		stmt.setNull(index++,Types.INTEGER);
        		stmt.setNull(index++,Types.INTEGER);
        	}
        	stmt.setTimestamp(index++, new Timestamp(filtro.getDataInicio().getTime()));
        	stmt.setTimestamp(index++, new Timestamp(filtro.getDataFim().getTime()));
        	
            rs = stmt.executeQuery();
    
            while (rs.next()) {
            	MovimentacaoEstoqueModel movimentacao = new MovimentacaoEstoqueModel();
            	movimentacao.setProduto(new ProdutoModel(rs.getLong("produto_codigo")));
            	movimentacao.setQuantidade(rs.getInt("quantidade"));
            	movimentacao.setTipoMovimentacaoEstoque(new TipoMovimentacaoEstoqueModel(rs.getLong("tipo_movimentacao_codigo")));
            	movimentacao.getTipoMovimentacaoEstoque().setNome(rs.getString("nome_movimentacao"));
            	movimentacao.getProduto().setNome(rs.getString("nome"));
            	movimentacao.setNotaFiscal(rs.getString("nota_fiscal"));
            	movimentacao.setDataMovimentacao(rs.getTimestamp("data_movimentacao"));
            	movimentacao.setDataRecebimento(rs.getTimestamp("data_recebimento_produto"));
            	lista.add(movimentacao);
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
