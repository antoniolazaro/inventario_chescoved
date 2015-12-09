package br.com.vortice.chescoved.inventario.view.menu.estoqueprincipal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.com.vortice.chescoved.inventario.estoqueprincipal.business.ProdutoBusiness;
import br.com.vortice.chescoved.inventario.model.ProdutoModel;
import br.com.vortice.chescoved.inventario.view.HboxFactory;
import br.com.vortice.chescoved.inventario.view.LabelFactory;
import br.com.vortice.chescoved.inventario.view.ShowAlertUtil;
import br.com.vortice.chescoved.inventario.view.custom.MoneyMaskInputTextListener;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

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
    private final TableView<ProdutoModel> table = new TableView<ProdutoModel>();
    
    private final Label codigoBuscaLabel = LabelFactory.getLabelPadrao("Código");
    private final Label nomeBuscaLabel = LabelFactory.getLabelPadrao("Produto");
    private final Label localEstoqueBuscaLabel = LabelFactory.getLabelPadrao("Local do Estoque");
    private final Label codigoLabel = LabelFactory.getLabelPadrao("Código");
    private final Label nomeLabel = LabelFactory.getLabelPadrao("Produto");
    private final Label localEstoqueLabel = LabelFactory.getLabelPadrao("Local do Estoque");
    private final Label valorCustoLabel = LabelFactory.getLabelPadrao("Valor de Custo");
    private final Label valorVendaLabel = LabelFactory.getLabelPadrao("Valor de Venda");
    
    
	public ProdutoMenuItem() {
		produtoBusiness = new ProdutoBusiness();
	}
	
	private TableView<ProdutoModel> createTableView(){
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
        nomeCol.setCellValueFactory(new Callback<CellDataFeatures<ProdutoModel, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<ProdutoModel, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getNome().toUpperCase());
            }
         });

        TableColumn<ProdutoModel, String> localEstoqueCol = new TableColumn<ProdutoModel, String>("Local do Estoque");
        localEstoqueCol.setMaxWidth(300);
        localEstoqueCol.setCellValueFactory(new Callback<CellDataFeatures<ProdutoModel, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<ProdutoModel, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getLocalEstoque().toUpperCase());
            }
         });

        
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
       
        reloadTable(table);
        table.getColumns().addAll(codigoCol,nomeCol,localEstoqueCol,quantidadeCol,valorCustoCol,totalCusto,valorVendaCol,totalVenda);
        return table;
	}

	protected void reloadTable(TableView<ProdutoModel> table) {
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

                if (produto.getNome().toUpperCase().contains(newValue.toUpperCase())) {
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

                if (produto.getLocalEstoque().toUpperCase().contains(newValue.toUpperCase())) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });
        
        SortedList<ProdutoModel> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        
        table.setItems(sortedData);
	}

	private VBox createAdicionarProduto(){
		final VBox vb = new VBox();
        
        codigo.setPromptText("Código");
        codigo.setMaxWidth(100);
        HBox hb = HboxFactory.getHBoxPadrao(codigoLabel, codigo);
		vb.getChildren().add(hb);
        
        nome.setPromptText("Nome");
        nome.setMaxWidth(200);
        hb = HboxFactory.getHBoxPadrao(nomeLabel, nome);
		vb.getChildren().add(hb);
        
        localEstoque.setPromptText("Local do Estoque");
        localEstoque.setMaxWidth(200);
        hb = HboxFactory.getHBoxPadrao(localEstoqueLabel, localEstoque);
		vb.getChildren().add(hb);
        
        valorCusto.setPromptText("Valor Custo");
        valorCusto.setMaxWidth(200);
        valorCusto.lengthProperty().addListener(new MoneyMaskInputTextListener(valorCusto));
        
        hb = HboxFactory.getHBoxPadrao(valorCustoLabel, valorCusto);
		vb.getChildren().add(hb);
        
        valorVenda.setPromptText("Valor Venda");
        valorVenda.setMaxWidth(200);
        valorVenda.lengthProperty().addListener(new MoneyMaskInputTextListener(valorVenda));

        hb = HboxFactory.getHBoxPadrao(valorVendaLabel, valorVenda);
		vb.getChildren().add(hb);
        
        final Button addButton = new Button("SALVAR");
        
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
            		
            		
            		
            		BigDecimal valorCustoBd = new BigDecimal(valorCusto.getText().replaceAll("R\\$ ","").replaceAll("\\.","").replaceAll(",","."));
            		BigDecimal valorVendaBd = new BigDecimal(valorVenda.getText().replaceAll("R\\$ ","").replaceAll("\\.","").replaceAll(",","."));
	            	ProdutoModel produtoModel = new ProdutoModel(Long.valueOf(codigo.getText()),nome.getText(),localEstoque.getText(),valorCustoBd,valorVendaBd);
	            	if(produtoBusiness.insertOrUpdate(produtoModel)){
	            		conteudoTabela.add(produtoModel);	
	            	}else{
	            		conteudoTabela.clear();
	            		reloadTable(table);
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
        vb.getChildren().add(addButton);
        return vb;
	}
	
	private VBox createFiltroProduto(){
		final VBox vb = new VBox();
        
        codigoSearch.setPromptText("Código");
        codigoSearch.setMaxWidth(100);
        HBox hb = HboxFactory.getHBoxPadrao(codigoBuscaLabel, codigoSearch);
		vb.getChildren().add(hb);
       
        nomeSearch.setPromptText("Nome");
        nomeSearch.setMaxWidth(200);
        hb = HboxFactory.getHBoxPadrao(nomeBuscaLabel, nomeSearch);
		vb.getChildren().add(hb);
       
        
        localEstoqueSearch.setPromptText("Local Estoque");
        localEstoqueSearch.setMaxWidth(200);
        hb = HboxFactory.getHBoxPadrao(localEstoqueBuscaLabel, localEstoqueSearch);
		vb.getChildren().add(hb);
        
        return vb;
	}

	public void buildMenuItem(BorderPane root){
	    final Label label = new Label("ESTOQUE PRINCIPAL > CONSULTA DE PRODUTOS");
        label.setFont(new Font("Arial", 20));

        VBox pesquisar = createFiltroProduto();
        VBox blocPesquisa = new VBox();
        blocPesquisa.getChildren().addAll(label,pesquisar);
        
        TableView<ProdutoModel> table = createTableView();
        final Label labelLista = new Label("ESTOQUE PRINCIPAL > LISTA DE PRODUTOS");
        labelLista.setFont(new Font("Arial", 20));        
        VBox blocoTabela = new VBox();
        blocoTabela.getChildren().addAll(labelLista,table);
        
        
        final Label labelEdicao = new Label("ESTOQUE PRINCIPAL > CADASTRO/EDIÇÃO DE PRODUTOS");
        labelEdicao.setFont(new Font("Arial", 20));
        VBox adicionarPalleteSection = createAdicionarProduto();
        VBox blocoCadastro = new VBox();
        blocoCadastro.getChildren().addAll(labelEdicao,adicionarPalleteSection);
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER_LEFT);
        
        grid.add(blocPesquisa, 0, 1, 2, 1);
        grid.add(blocoTabela, 0, 3, 2, 1);
        grid.add(blocoCadastro, 0, 4, 2, 1);
        
        root.setCenter(grid);

	}
}
