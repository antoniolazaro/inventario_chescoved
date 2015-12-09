package br.com.vortice.chescoved.inventario.view.menu.materialinterno;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.com.vortice.chescoved.inventario.materialinterno.business.InventarioBusiness;
import br.com.vortice.chescoved.inventario.model.InventarioModel;
import br.com.vortice.chescoved.inventario.model.InventarioProdutoModel;
import br.com.vortice.chescoved.inventario.util.DateUtil;
import br.com.vortice.chescoved.inventario.view.HboxFactory;
import br.com.vortice.chescoved.inventario.view.LabelFactory;
import br.com.vortice.chescoved.inventario.view.ShowAlertUtil;
import br.com.vortice.chescoved.inventario.view.custom.DateMaskInputTextListener;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

public class InventarioMenuItem {
	
	private InventarioBusiness inventarioBusiness;
	private ObservableList<InventarioProdutoModel> conteudoTabela;
	private InventarioModel inventario;
	private TableView<InventarioProdutoModel> table;
	private List<InventarioProdutoModel> listaProdutos;
	private final Label dataInventarioLabel = LabelFactory.getLabelPadrao("Data do Inventário");
	private final Button confirmarInventarioButton = new Button("Confirmar Inventário");
	private final Button cancelarInventarioButton = new Button("Cancelar Inventário");
	private final TextField dataInventarioText = new TextField();
	 
	public InventarioMenuItem() {
		inventarioBusiness = new InventarioBusiness();
		inventario = new InventarioModel();
	}
	
	private TableView<InventarioProdutoModel> createTableView(){
		TableView<InventarioProdutoModel> table = new TableView<InventarioProdutoModel>();

        table.setEditable(true);
        
        TableColumn<InventarioProdutoModel, Date> codigoCol = new TableColumn<InventarioProdutoModel, Date>("Data Inventário");
        codigoCol.setPrefWidth(120);
        codigoCol.setCellValueFactory(new Callback<CellDataFeatures<InventarioProdutoModel, Date>, ObservableValue<Date>>() {
            public ObservableValue<Date> call(CellDataFeatures<InventarioProdutoModel, Date> p) {
                return new ReadOnlyObjectWrapper(new SimpleDateFormat("dd/MM/yyyy").format(p.getValue().getInventario().getDataInventario()));
            }
         });
        
        
        TableColumn<InventarioProdutoModel, String> nomeCol = new TableColumn<InventarioProdutoModel, String>("Produto");
        nomeCol.setPrefWidth(300);
        nomeCol.setCellValueFactory(new Callback<CellDataFeatures<InventarioProdutoModel, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<InventarioProdutoModel, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getProduto().getNome());
            }
         });
        
        TableColumn<InventarioProdutoModel, Long> codigoProdutoCol = new TableColumn<InventarioProdutoModel, Long>("Código");
        codigoProdutoCol.setPrefWidth(120);
        codigoProdutoCol.setCellValueFactory(new Callback<CellDataFeatures<InventarioProdutoModel, Long>, ObservableValue<Long>>() {
            public ObservableValue<Long> call(CellDataFeatures<InventarioProdutoModel, Long> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getProduto().getCodigo());
            }
         });

        TableColumn<InventarioProdutoModel, Integer> quantidadeContada = new TableColumn<InventarioProdutoModel, Integer>("Quantidade Contada");
        quantidadeContada.setPrefWidth(200);
        quantidadeContada.setCellValueFactory(new PropertyValueFactory<InventarioProdutoModel, Integer>("quantidade"));
        quantidadeContada.setCellFactory(TextFieldTableCell.<InventarioProdutoModel,Integer>forTableColumn(new IntegerStringConverter()));
        quantidadeContada.setOnEditCommit(
            (CellEditEvent<InventarioProdutoModel, Integer> t) -> {
            	
            	TableView<InventarioProdutoModel> tableViewProdutos = t.getTableView();
            	int linhaModificada = t.getTablePosition().getRow();
            	
            	InventarioProdutoModel itemModificado = (InventarioProdutoModel) tableViewProdutos.getItems().get(linhaModificada);
            	itemModificado.setQuantidade(t.getNewValue());
            	
            	itemModificado.setQuantidadeDivergencia(itemModificado.getQuantidadeDivergencia());
            	itemModificado.setTotalCusto(itemModificado.getProduto().getValorCusto().multiply(new BigDecimal(itemModificado.getQuantidadeDivergencia())));
            	
            	tableViewProdutos.refresh();
            
            	tableViewProdutos.getSelectionModel().select(linhaModificada+1);
            	
            	tableViewProdutos.focusModelProperty().get().focus(4);
            	table.requestFocus();
            	
        });

        TableColumn<InventarioProdutoModel, Integer> quantidadeEstoque = new TableColumn<InventarioProdutoModel, Integer>("Quantidade Estoque");
        quantidadeEstoque.setPrefWidth(200);
        quantidadeEstoque.setCellValueFactory(new PropertyValueFactory<InventarioProdutoModel, Integer>("quantidadeEstoque"));
        
        TableColumn<InventarioProdutoModel, Integer> quantidadeDivergencia = new TableColumn<InventarioProdutoModel, Integer>("Divergência");
        quantidadeDivergencia.setPrefWidth(200);
        quantidadeDivergencia.setEditable(true);
        quantidadeDivergencia.setCellValueFactory(new PropertyValueFactory<InventarioProdutoModel, Integer>("quantidadeDivergencia"));
        
        TableColumn<InventarioProdutoModel, BigDecimal> valorCusto = new TableColumn<InventarioProdutoModel, BigDecimal>("Valor Custo");
        valorCusto.setPrefWidth(200);
        valorCusto.setCellValueFactory(new Callback<CellDataFeatures<InventarioProdutoModel, BigDecimal>, ObservableValue<BigDecimal>>() {
            public ObservableValue<BigDecimal> call(CellDataFeatures<InventarioProdutoModel, BigDecimal> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getProduto().getValorCusto());
            }
         });

        TableColumn<InventarioProdutoModel, BigDecimal> totalCusto = new TableColumn<InventarioProdutoModel, BigDecimal>("Total Custo");
        totalCusto.setPrefWidth(200);
        totalCusto.setCellValueFactory(new PropertyValueFactory<InventarioProdutoModel, BigDecimal>("totalCusto"));
        
        conteudoTabela = FXCollections.observableArrayList(new ArrayList<InventarioProdutoModel>());
       
        table.setItems(conteudoTabela);
        table.getColumns().addAll(codigoCol,nomeCol,codigoProdutoCol,quantidadeContada,quantidadeEstoque,quantidadeDivergencia,valorCusto,totalCusto);
        
        
        return table;
	}

	
	private VBox createFiltroProduto(){
		final VBox vb = new VBox();
        
		dataInventarioText.setPromptText("Data do Inventário");
		dataInventarioText.setMaxWidth(100);
		dataInventarioText.lengthProperty().addListener(new DateMaskInputTextListener(dataInventarioText));
		HBox hb = HboxFactory.getHBoxPadrao(dataInventarioLabel, dataInventarioText);
		vb.getChildren().add(hb);
       
        final Button criarInventarioButton = new Button("Criar Inventário"); 
        
        criarInventarioButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	try{
            		if(StringUtils.isNotEmpty(dataInventarioText.getText())){
            			inventario.setDataInventario(DateUtil.parse(dataInventarioText.getText()));	
            		}else{
            			ShowAlertUtil.exibirMensagemErro("Campo data do inventário é obrigatório.");
            		}
            	    
            		listaProdutos = inventarioBusiness.findAllProdutos(inventario);
        	        if(listaProdutos == null){
        	        	listaProdutos = new ArrayList<InventarioProdutoModel>();
        	        }
        	        conteudoTabela = FXCollections.observableArrayList(listaProdutos);
        	        table.setItems(conteudoTabela);
            		table.setVisible(true);
            		confirmarInventarioButton.setVisible(true);
            		cancelarInventarioButton.setVisible(true);
            	}catch(Exception ex){
            		ShowAlertUtil.exibirMensagemErro("Erro: "+ex.getMessage());
                }
            }
        });
        
        vb.getChildren().add(criarInventarioButton);
        
        return vb;
	}
	
	public void buildMenuItem(BorderPane root){
	    final Label label = new Label("ESTOQUE MATERIAL INTERNO > INVENTÁRIO");
        label.setFont(new Font("Arial", 20));

        VBox pesquisar = createFiltroProduto();
        table = createTableView();
        table.setVisible(false);
        
        confirmarInventarioButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	try{
            		inventario.setListaProdutos(listaProdutos);
            		inventarioBusiness.insert(inventario);
            		ShowAlertUtil.exibirMensagemInfo("Inventário executado com sucesso. O estoque passou a ter a quantidade auditada.");
            	}catch(Exception ex){
            		ShowAlertUtil.exibirMensagemErro("Erro: "+ex.getMessage());
                }
            }
        });
        confirmarInventarioButton.setVisible(false);
        
        cancelarInventarioButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	try{
            		listaProdutos = new ArrayList<InventarioProdutoModel>();
			        conteudoTabela = FXCollections.observableArrayList(listaProdutos);
			        table.setItems(conteudoTabela);
			        dataInventarioText.setText("");
		    		table.setVisible(false);
		    		confirmarInventarioButton.setVisible(false);
		    		cancelarInventarioButton.setVisible(false);
            		ShowAlertUtil.exibirMensagemInfo("Inventário cancelado com sucesso. ");
            	}catch(Exception ex){
            		ShowAlertUtil.exibirMensagemErro("Erro: "+ex.getMessage());
                }
            }
        });
        cancelarInventarioButton.setVisible(false);
        
        final VBox vbox = new VBox();
        vbox.getChildren().addAll(label,pesquisar,table,confirmarInventarioButton,cancelarInventarioButton);
 
        root.setCenter(vbox);

	}

}
