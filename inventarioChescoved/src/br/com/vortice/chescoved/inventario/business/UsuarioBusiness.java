package br.com.vortice.chescoved.inventario.business;

import br.com.vortice.chescoved.inventario.dao.UsuarioDAO;
import br.com.vortice.chescoved.inventario.model.UsuarioModel;

public class UsuarioBusiness{
	
	private UsuarioDAO usuarioDAO;
	
	public UsuarioBusiness() {
		this.usuarioDAO = new UsuarioDAO();	
	}

	public UsuarioModel login(UsuarioModel usuario) throws Exception{
		usuario = usuarioDAO.login(usuario);
		if(usuario == null){
			throw new Exception("Usuário ou senha inválidos.");
		}
		return usuario;
	}
	
}
