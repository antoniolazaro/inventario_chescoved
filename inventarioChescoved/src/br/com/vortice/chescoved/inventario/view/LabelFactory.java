package br.com.vortice.chescoved.inventario.view;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LabelFactory {
	
	private LabelFactory() {
	}
	
	public static Label getLabelPadrao(String descricao){
		Label label = new Label(descricao+":");
		Font fonte = new Font(12);
		fonte.font("Arial", FontWeight.BOLD, 12);
		label.setFont(fonte);
		return label;
	}

}
