package br.com.vortice.chescoved.inventario.view.menu;

import br.com.vortice.chescoved.inventario.view.menu.menuitem.CurvaAbcMenuItem;
import br.com.vortice.chescoved.inventario.view.menu.menuitem.InventarioMenuItem;
import br.com.vortice.chescoved.inventario.view.menu.menuitem.MovimentacaoEstoqueMenuItem;
import br.com.vortice.chescoved.inventario.view.menu.menuitem.PepsMenuItem;
import br.com.vortice.chescoved.inventario.view.menu.menuitem.ProdutoMenuItem;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MenuBuilder {
	
	private BorderPane root;
	private Stage primaryStage;
	
	public MenuBuilder(Stage primaryStage,BorderPane root) {
		this.primaryStage = primaryStage;
		this.root = root;
	}
	
	public void buildMenu(){
		MenuBar menuBar = new MenuBar();
	    menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
	    menuBar.useSystemMenuBarProperty().set(true);

	    root.setTop(menuBar);
	    
	    Menu produtoMenu = menuEstoquePrincipal();
	    
	    Menu sair = new Menu("Sair");
	    sair.setOnAction(actionEvent -> Platform.exit());
	    
	    menuBar.getMenus().addAll(produtoMenu,sair);
	}

	protected Menu menuEstoquePrincipal() {
		Menu produtoMenu = new Menu("Estoque Principal");
	    MenuItem manterProdutoMenuItem = new MenuItem("Cadastro de produto");
	    manterProdutoMenuItem.setOnAction(actionEvent -> new ProdutoMenuItem().buildMenuItem(primaryStage));
	    MenuItem movimentacaoEstoqueMenuItem = new MenuItem("Movimentação de Estoque");
	    movimentacaoEstoqueMenuItem.setOnAction(actionEvent -> new MovimentacaoEstoqueMenuItem().buildMenuItem(primaryStage));
	    MenuItem inventarioMenuItem = new MenuItem("Inventário");
	    inventarioMenuItem.setOnAction(actionEvent -> new InventarioMenuItem().buildMenuItem(primaryStage));
	    MenuItem curvaABCMenuItem = new MenuItem("Curva ABC");
	    curvaABCMenuItem.setOnAction(actionEvent -> new CurvaAbcMenuItem().buildMenuItem(primaryStage));
	    MenuItem pepsMenuItem = new MenuItem("PEPS");
	    pepsMenuItem.setOnAction(actionEvent -> new PepsMenuItem().buildMenuItem(primaryStage));
	    produtoMenu.getItems().addAll(manterProdutoMenuItem,movimentacaoEstoqueMenuItem,inventarioMenuItem,curvaABCMenuItem,pepsMenuItem);
		return produtoMenu;
	}
	
	

}
