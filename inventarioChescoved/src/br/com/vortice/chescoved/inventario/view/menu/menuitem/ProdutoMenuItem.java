package br.com.vortice.chescoved.inventario.view.menu.menuitem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.com.vortice.chescoved.inventario.business.ProdutoBusiness;
import br.com.vortice.chescoved.inventario.model.ProdutoModel;
import br.com.vortice.chescoved.inventario.view.ShowAlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ProdutoMenuItem {
	
	private ProdutoBusiness produtoBusiness;
	private ObservableList<ProdutoModel> conteudoTabela;
	private final TextField codigoSearch = new TextField();
	private final TextField nomeSearch = new TextField();
	private final TextField localEstoqueSearch = new TextField();
	private final TextField codigo = new TextField();
    private final TextField nome = new TextField();
    private final TextField localEstoque = new TextField();
    private final TextField valorCusto = new TextField();
    private final TextField valorVenda = new TextField();
	
	public ProdutoMenuItem() {
		produtoBusiness = new ProdutoBusiness();
	}
	
	private TableView<ProdutoModel> createTableView(){
		TableView<ProdutoModel> table = new TableView<ProdutoModel>();
		table.setRowFactory( tv -> {
		    TableRow<ProdutoModel> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		        	ProdutoModel produtoSelecionado = row.getItem();
		        	codigo.setText(produtoSelecionado.getCodigo().toString());
		        	nome.setText(produtoSelecionado.getNome());
		        	localEstoque.setText(produtoSelecionado.getLocalEstoque());
		        	valorCusto.setText(produtoSelecionado.getValorCusto().toString());
		        	valorVenda.setText(produtoSelecionado.getValorVenda().toString());		        	
		        }
		    });
		    return row ;
		});

        table.setEditable(true);
        
        TableColumn<ProdutoModel, Long> codigoCol = new TableColumn<ProdutoModel, Long>("Código");
        codigoCol.setMaxWidth(100);
        codigoCol.setCellValueFactory(new PropertyValueFactory<ProdutoModel, Long>("codigo"));     
        
        TableColumn<ProdutoModel, String> nomeCol = new TableColumn<ProdutoModel, String>("Nome");
        nomeCol.setMaxWidth(300);
        nomeCol.setCellValueFactory(new PropertyValueFactory<ProdutoModel, String>("nome"));

        TableColumn<ProdutoModel, String> localEstoqueCol = new TableColumn<ProdutoModel, String>("Local do Estoque");
        localEstoqueCol.setMaxWidth(300);
        localEstoqueCol.setCellValueFactory(new PropertyValueFactory<ProdutoModel, String>("localEstoque"));

        TableColumn<ProdutoModel, Integer> quantidadeCol = new TableColumn<ProdutoModel, Integer>("Quantidade");
        quantidadeCol.setMaxWidth(150);
        quantidadeCol.setCellValueFactory(new PropertyValueFactory<ProdutoModel, Integer>("quantidade"));
        
        TableColumn<ProdutoModel, BigDecimal> valorCustoCol = new TableColumn<ProdutoModel, BigDecimal>("Valor de Custo");
        valorCustoCol.setMaxWidth(150);
        valorCustoCol.setCellValueFactory(new PropertyValueFactory<ProdutoModel, BigDecimal>("valorCusto"));

        TableColumn<ProdutoModel, BigDecimal> totalCusto = new TableColumn<ProdutoModel, BigDecimal>("Total Custo");
        totalCusto.setMaxWidth(150);
        totalCusto.setCellValueFactory(new PropertyValueFactory<ProdutoModel, BigDecimal>("totalCusto"));

        TableColumn<ProdutoModel, BigDecimal> valorVendaCol = new TableColumn<ProdutoModel, BigDecimal>("Valor de Venda");
        valorVendaCol.setMaxWidth(150);
        valorVendaCol.setCellValueFactory(new PropertyValueFactory<ProdutoModel, BigDecimal>("valorVenda"));
        
        TableColumn<ProdutoModel, BigDecimal> totalVenda = new TableColumn<ProdutoModel, BigDecimal>("Total Venda");
        totalVenda.setMaxWidth(150);
        totalVenda.setCellValueFactory(new PropertyValueFactory<ProdutoModel, BigDecimal>("totalVenda"));
       
        List<ProdutoModel> listaProdutos = null;
        try{
        	listaProdutos = produtoBusiness.selectAll();
	        
        }catch(Exception ex){
        	ShowAlertUtil.exibirMensagemErro("Erro: "+ex.getMessage());
        }
        if(listaProdutos == null){
        	listaProdutos = new ArrayList<ProdutoModel>();
        }
        conteudoTabela = FXCollections.observableArrayList(listaProdutos);
        
        
        FilteredList<ProdutoModel> filteredData = new FilteredList<>(conteudoTabela, p -> true);
        
        codigoSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(produto -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                if (produto.getCodigo().toString().contains(newValue)) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });
        nomeSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(produto -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                if (produto.getNome().contains(newValue)) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });
        localEstoqueSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(produto -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                if (produto.getLocalEstoque().contains(newValue)) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });
        
        SortedList<ProdutoModel> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        
        table.setItems(sortedData);
        table.getColumns().addAll(codigoCol,nomeCol,localEstoqueCol,quantidadeCol,valorCustoCol,totalCusto,valorVendaCol,totalVenda);
        return table;
	}

	private HBox createAdicionarProduto(){
		final HBox hb = new HBox();
        
        codigo.setPromptText("Código");
        codigo.setMaxWidth(100);
        
        nome.setPromptText("Nome");
        nome.setMaxWidth(200);
        
        localEstoque.setPromptText("Local do Estoque");
        localEstoque.setMaxWidth(200);
        
        valorCusto.setPromptText("Valor Custo");
        valorCusto.setMaxWidth(200);
        
        valorVenda.setPromptText("Valor Venda");
        valorVenda.setMaxWidth(200);
        
       
        final Button addButton = new Button("Adicionar Produto");
        
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	try{
            		
            		if(StringUtils.isEmpty(codigo.getText())){
            			ShowAlertUtil.exibirMensagemErro("Campo código é obrigatório.");
            		}
            		if(StringUtils.isEmpty(nome.getText())){
            			ShowAlertUtil.exibirMensagemErro("Campo nome é obrigatório.");
            		}
            		if(StringUtils.isEmpty(localEstoque.getText())){
            			ShowAlertUtil.exibirMensagemErro("Campo local de estoque é obrigatório.");
            		}
            		if(StringUtils.isEmpty(valorCusto.getText())){
            			ShowAlertUtil.exibirMensagemErro("Campo valor de custo é obrigatório.");
            		}
            		if(StringUtils.isEmpty(valorVenda.getText())){
            			ShowAlertUtil.exibirMensagemErro("Campo valor de venda é obrigatório.");
            		}
            		
	            	ProdutoModel produtoModel = new ProdutoModel(Long.valueOf(codigo.getText()),nome.getText(),localEstoque.getText(),new BigDecimal(valorCusto.getText()),new BigDecimal(valorVenda.getText()));
	            	if(produtoBusiness.insertOrUpdate(produtoModel)){
	            		conteudoTabela.add(produtoModel);	
	            	}
	                codigo.clear();
	                nome.clear();
	                localEstoque.clear();
	                valorCusto.clear();
	                valorVenda.clear();
	                ShowAlertUtil.exibirMensagemInfo("Ação realizada com sucesso!");
            	}catch(Exception ex){
            		ShowAlertUtil.exibirMensagemErro("Erro: "+ex.getMessage());
                }
            }
        });
        hb.getChildren().addAll(codigo,nome,localEstoque,valorCusto,valorVenda,addButton);
        hb.setSpacing(5);
        return hb;
	}
	
	private HBox createFiltroProduto(){
		final HBox hb = new HBox();
        
        codigoSearch.setPromptText("Código");
        codigoSearch.setMaxWidth(100);
       
        nomeSearch.setPromptText("Nome");
        nomeSearch.setMaxWidth(200);
        
        localEstoqueSearch.setPromptText("Local Estoque");
        localEstoqueSearch.setMaxWidth(200);
        
        hb.getChildren().addAll(codigoSearch,nomeSearch,localEstoqueSearch);
        hb.setSpacing(5);
        return hb;
	}

	public void buildMenuItem(Stage primaryStage){
		Scene scene = new Scene(new Group());
	    final Label label = new Label("Lista Produtos");
        label.setFont(new Font("Arial", 20));

        HBox pesquisar = createFiltroProduto();
        TableView<ProdutoModel> table = createTableView();
        HBox adicionarPalleteSection = createAdicionarProduto();
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label,pesquisar,table,adicionarPalleteSection);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        
        primaryStage.setScene(scene);
        primaryStage.show();

	}
}
