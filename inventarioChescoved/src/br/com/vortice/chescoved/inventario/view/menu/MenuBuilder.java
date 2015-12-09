package br.com.vortice.chescoved.inventario.view.menu;

import br.com.vortice.chescoved.inventario.view.menu.estoqueprincipal.CurvaAbcMenuItem;
import br.com.vortice.chescoved.inventario.view.menu.estoqueprincipal.InventarioMenuItem;
import br.com.vortice.chescoved.inventario.view.menu.estoqueprincipal.MovimentacaoEstoqueMenuItem;
import br.com.vortice.chescoved.inventario.view.menu.estoqueprincipal.PepsMenuItem;
import br.com.vortice.chescoved.inventario.view.menu.estoqueprincipal.ProdutoMenuItem;
import br.com.vortice.chescoved.inventario.view.menu.estoqueprincipal.RelatorioMovimentacaoEstoqueMenuItem;
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
	    Menu arcMenu = menuEstoqueARC();
	    Menu materialInternoMenu = menuEstoqueMaterialInterno();
	    
	    Menu sair = new Menu("Sair");
	    sair.setOnAction(actionEvent -> Platform.exit());
	    
	    menuBar.getMenus().addAll(produtoMenu,arcMenu,materialInternoMenu);
	}

	protected Menu menuEstoquePrincipal() {
		Menu produtoMenu = new Menu("Estoque Principal");
	    MenuItem manterProdutoMenuItem = new MenuItem("Cadastro de produto");
	    manterProdutoMenuItem.setOnAction(actionEvent -> new ProdutoMenuItem().buildMenuItem(root));
	    MenuItem movimentacaoEstoqueMenuItem = new MenuItem("Movimentação de Estoque");
	    movimentacaoEstoqueMenuItem.setOnAction(actionEvent -> new MovimentacaoEstoqueMenuItem().buildMenuItem(root));
	    MenuItem inventarioMenuItem = new MenuItem("Inventário");
	    inventarioMenuItem.setOnAction(actionEvent -> new InventarioMenuItem().buildMenuItem(root));
	    MenuItem curvaABCMenuItem = new MenuItem("Curva ABC");
	    curvaABCMenuItem.setOnAction(actionEvent -> new CurvaAbcMenuItem().buildMenuItem(root));
	    MenuItem pepsMenuItem = new MenuItem("PEPS");
	    pepsMenuItem.setOnAction(actionEvent -> new PepsMenuItem().buildMenuItem(root));
	    MenuItem relatorioMovimentacaoEstoqueItem = new MenuItem("Relatório Movimentação de Estoque");
	    relatorioMovimentacaoEstoqueItem.setOnAction(actionEvent -> new RelatorioMovimentacaoEstoqueMenuItem().buildMenuItem(root));
	    produtoMenu.getItems().addAll(manterProdutoMenuItem,movimentacaoEstoqueMenuItem,inventarioMenuItem,curvaABCMenuItem,pepsMenuItem,relatorioMovimentacaoEstoqueItem);
		return produtoMenu;
	}
	
	protected Menu menuEstoqueARC() {
		Menu produtoMenu = new Menu("Estoque ARC");
	    MenuItem manterProdutoMenuItem = new MenuItem("Cadastro de produto");
	    manterProdutoMenuItem.setOnAction(actionEvent -> new br.com.vortice.chescoved.inventario.view.menu.estoquearc.ProdutoMenuItem().buildMenuItem(root));
	    MenuItem movimentacaoEstoqueMenuItem = new MenuItem("Movimentação de Estoque");
	    movimentacaoEstoqueMenuItem.setOnAction(actionEvent -> new br.com.vortice.chescoved.inventario.view.menu.estoquearc.MovimentacaoEstoqueMenuItem().buildMenuItem(root));
	    MenuItem inventarioMenuItem = new MenuItem("Inventário");
	    inventarioMenuItem.setOnAction(actionEvent -> new br.com.vortice.chescoved.inventario.view.menu.estoquearc.InventarioMenuItem().buildMenuItem(root));
	    MenuItem curvaABCMenuItem = new MenuItem("Curva ABC");
	    curvaABCMenuItem.setOnAction(actionEvent -> new br.com.vortice.chescoved.inventario.view.menu.estoquearc.CurvaAbcMenuItem().buildMenuItem(root));
	    MenuItem pepsMenuItem = new MenuItem("PEPS");
	    pepsMenuItem.setOnAction(actionEvent -> new br.com.vortice.chescoved.inventario.view.menu.estoquearc.PepsMenuItem().buildMenuItem(root));
	    MenuItem relatorioMovimentacaoEstoqueItem = new MenuItem("Relatório Movimentação de Estoque");
	    relatorioMovimentacaoEstoqueItem.setOnAction(actionEvent -> new br.com.vortice.chescoved.inventario.view.menu.estoquearc.RelatorioMovimentacaoEstoqueMenuItem().buildMenuItem(root));
	    produtoMenu.getItems().addAll(manterProdutoMenuItem,movimentacaoEstoqueMenuItem,inventarioMenuItem,curvaABCMenuItem,pepsMenuItem,relatorioMovimentacaoEstoqueItem);
		return produtoMenu;
	}
	
	protected Menu menuEstoqueMaterialInterno() {
		Menu produtoMenu = new Menu("Material Interno");
	    MenuItem manterProdutoMenuItem = new MenuItem("Cadastro de produto");
	    manterProdutoMenuItem.setOnAction(actionEvent -> new br.com.vortice.chescoved.inventario.view.menu.materialinterno.ProdutoMenuItem().buildMenuItem(root));
	    MenuItem movimentacaoEstoqueMenuItem = new MenuItem("Movimentação de Estoque");
	    movimentacaoEstoqueMenuItem.setOnAction(actionEvent -> new br.com.vortice.chescoved.inventario.view.menu.materialinterno.MovimentacaoEstoqueMenuItem().buildMenuItem(root));
	    MenuItem inventarioMenuItem = new MenuItem("Inventário");
	    inventarioMenuItem.setOnAction(actionEvent -> new br.com.vortice.chescoved.inventario.view.menu.materialinterno.InventarioMenuItem().buildMenuItem(root));
	    MenuItem relatorioMovimentacaoEstoqueItem = new MenuItem("Relatório Movimentação de Estoque");
	    relatorioMovimentacaoEstoqueItem.setOnAction(actionEvent -> new br.com.vortice.chescoved.inventario.view.menu.materialinterno.RelatorioMovimentacaoEstoqueMenuItem().buildMenuItem(root));
	    produtoMenu.getItems().addAll(manterProdutoMenuItem,movimentacaoEstoqueMenuItem,inventarioMenuItem,relatorioMovimentacaoEstoqueItem);
		return produtoMenu;
	}
	
	

}
