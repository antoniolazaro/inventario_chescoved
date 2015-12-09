package br.com.vortice.chescoved.inventario.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.vortice.chescoved.inventario.model.UsuarioModel;

public class UsuarioDAO extends DAOAb {

	public UsuarioModel login(UsuarioModel usuario) throws Exception{
        Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
      
        String sql = "select codigo,login,senha from usuario where upper(login) = UPPER(?) and senha = ?";
        try {
        	stmt = connection.prepareStatement(sql);
        	stmt.setString(1, usuario.getLogin());
        	stmt.setString(2, usuario.getSenha());
        	
            rs = stmt.executeQuery();
            
            if (rs.next()) {
            	UsuarioModel usuarioFiltrado = new UsuarioModel();
            	usuarioFiltrado.setCodigo(rs.getLong("codigo"));
            	usuarioFiltrado.setLogin(rs.getString("login"));
            	usuarioFiltrado.setSenha(rs.getString("senha"));
            	return usuarioFiltrado;
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
	
}
