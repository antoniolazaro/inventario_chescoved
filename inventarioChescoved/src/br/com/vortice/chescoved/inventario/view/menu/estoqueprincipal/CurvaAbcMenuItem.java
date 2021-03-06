package br.com.vortice.chescoved.inventario.view.menu.estoqueprincipal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.com.vortice.chescoved.inventario.estoqueprincipal.business.CurvaAbcBusiness;
import br.com.vortice.chescoved.inventario.model.CurvaAbcModel;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class CurvaAbcMenuItem {
	
	private ObservableList<CurvaAbcModel> conteudoTabela;
	private TableView<CurvaAbcModel> table;
	private CurvaAbcBusiness curvaAbcBusiness;
	private CurvaAbcModel curvaAbcModel;
	private final Label dataInicioLabel = LabelFactory.getLabelPadrao("Data Início");
	private final Label dataFimLabel = LabelFactory.getLabelPadrao("Data Fim");
	
	public CurvaAbcMenuItem() {
		curvaAbcBusiness = new CurvaAbcBusiness();
		curvaAbcModel = new CurvaAbcModel();
	}
	
	private TableView<CurvaAbcModel> createTableView(){
		TableView<CurvaAbcModel> table = new TableView<CurvaAbcModel>();

        table.setEditable(false);
        
        TableColumn<CurvaAbcModel, String> totalCusto = new TableColumn<CurvaAbcModel, String>("Classificação");
        totalCusto.setPrefWidth(150);
        totalCusto.setCellValueFactory(new PropertyValueFactory<CurvaAbcModel, String>("percentualSaida"));
        
        TableColumn<CurvaAbcModel, String> nomeCol = new TableColumn<CurvaAbcModel, String>("Produto");
        nomeCol.setPrefWidth(300);
        nomeCol.setCellValueFactory(new Callback<CellDataFeatures<CurvaAbcModel, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<CurvaAbcModel, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getProduto().getNome());
            }
         });
        
        TableColumn<CurvaAbcModel, Long> codigoProdutoCol = new TableColumn<CurvaAbcModel, Long>("Código");
        codigoProdutoCol.setPrefWidth(150);
        codigoProdutoCol.setCellValueFactory(new Callback<CellDataFeatures<CurvaAbcModel, Long>, ObservableValue<Long>>() {
            public ObservableValue<Long> call(CellDataFeatures<CurvaAbcModel, Long> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getProduto().getCodigo());
            }
         });

        TableColumn<CurvaAbcModel, Integer> quantidadeEstoque = new TableColumn<CurvaAbcModel, Integer>("Quantidade");
        quantidadeEstoque.setPrefWidth(150);
        quantidadeEstoque.setCellValueFactory(new PropertyValueFactory<CurvaAbcModel, Integer>("quantidade"));
        
        TableColumn<CurvaAbcModel, BigDecimal> quantidadeDivergencia = new TableColumn<CurvaAbcModel, BigDecimal>("Custo Unitário");
        quantidadeDivergencia.setPrefWidth(150);
        quantidadeDivergencia.setCellValueFactory(new PropertyValueFactory<CurvaAbcModel, BigDecimal>("valorUnitario"));
        
        TableColumn<CurvaAbcModel, Integer> quantidadeFinal = new TableColumn<CurvaAbcModel, Integer>("Custo Total");
        quantidadeFinal.setPrefWidth(150);
        quantidadeFinal.setCellValueFactory(new PropertyValueFactory<CurvaAbcModel, Integer>("valorTotal"));
        
        conteudoTabela = FXCollections.observableArrayList(new ArrayList<CurvaAbcModel>());
       
        table.setItems(conteudoTabela);
        table.getColumns().addAll(totalCusto,nomeCol,codigoProdutoCol,quantidadeEstoque,quantidadeDivergencia,quantidadeFinal);
        return table;
	}

	
	private VBox createFiltroProduto(){
		final VBox vb = new VBox();
        
		final TextField dataInicio = new TextField();
		dataInicio.setPromptText("Data Início");
		dataInicio.setPrefWidth(100);
		dataInicio.lengthProperty().addListener(new DateMaskInputTextListener(dataInicio));
		HBox hb = HboxFactory.getHBoxPadrao(dataInicioLabel, dataInicio);
		vb.getChildren().add(hb);
		
		final TextField dataFim = new TextField();
		dataFim.setPromptText("Data Fim");
		dataFim.setPrefWidth(100);
		dataFim.lengthProperty().addListener(new DateMaskInputTextListener(dataFim));
		hb = HboxFactory.getHBoxPadrao(dataFimLabel, dataFim);
		vb.getChildren().add(hb);
		
        final Button addButton = new Button("Calcular curva ABC");
        
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	try{
            		
            		if(StringUtils.isNotEmpty(dataInicio.getText())){
            			curvaAbcModel.setDataInicio(DateUtil.parse(dataInicio.getText()));	
            		}else{
            			ShowAlertUtil.exibirMensagemErro("Campo data início é obrigatório.");
            		}
            		if(StringUtils.isNotEmpty(dataFim.getText())){
            			curvaAbcModel.setDataFim(DateUtil.parse(dataFim.getText()));	
            		}else{
            			ShowAlertUtil.exibirMensagemErro("Campo data fim é obrigatório.");
            		}
            	    
            		List<CurvaAbcModel> listaProdutos = curvaAbcBusiness.calcularCurvaABC(curvaAbcModel);
        	        if(listaProdutos == null){
        	        	listaProdutos = new ArrayList<CurvaAbcModel>();
        	        }
        	        conteudoTabela = FXCollections.observableArrayList(listaProdutos);
        	        table.setItems(conteudoTabela);
            		table.setVisible(true);
            	}catch(Exception ex){
            		ShowAlertUtil.exibirMensagemErro("Erro: "+ex.getMessage());
                }
            }
        });
        
        vb.getChildren().addAll(addButton);
        return vb;
	}
	
	
	public void buildMenuItem(BorderPane root){
	    final Label label = new Label("ESTOQUE PRINCIPAL > CURVA ABC");
        label.setFont(new Font("Arial", 20));

        VBox pesquisar = createFiltroProduto();
        table = createTableView();
        table.setVisible(false);
        
        final VBox vbox = new VBox();
        vbox.getChildren().addAll(label,pesquisar,table);
 
        root.setCenter(vbox);

	}
}
