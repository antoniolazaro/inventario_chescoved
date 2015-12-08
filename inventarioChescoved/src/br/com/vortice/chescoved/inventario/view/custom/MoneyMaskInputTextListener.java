package br.com.vortice.chescoved.inventario.view.custom;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class MoneyMaskInputTextListener extends ComponentCustomAb implements ChangeListener<Number>{
	
	private TextField textField;
	
	public MoneyMaskInputTextListener(TextField textField) {
		this.textField = textField;
	}
	
	@Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        String value = textField.getText();
        value = value.replaceAll("[^0-9]", "");
        value = value.replaceAll("([0-9]{1})([0-9]{14})$", "$1.$2");
        value = value.replaceAll("([0-9]{1})([0-9]{11})$", "$1.$2");
        value = value.replaceAll("([0-9]{1})([0-9]{8})$", "$1.$2");
        value = value.replaceAll("([0-9]{1})([0-9]{5})$", "$1.$2");
        value = value.replaceAll("([0-9]{1})([0-9]{2})$", "$1,$2");
        textField.setText("R$ "+value);
        positionCaret(textField);

        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (newValue.length() > 17)
                	textField.setText(oldValue);
            }
        });
    }

}
