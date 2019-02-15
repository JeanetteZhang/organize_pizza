//Programmer: Jinting Zhang

package application;

import java.net.URL;
import java.util.ResourceBundle;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField amount = new TextField();

    @FXML
    private TextField cost = new TextField();

    @FXML
    private TextField price = new TextField();
    
    @FXML
    private TextArea orderArea = new TextArea();
    
    @FXML
    private Button display = new Button();

    @FXML
    private ChoiceBox<String> setSize = new ChoiceBox();
    
    @FXML
    private ChoiceBox<String> setCheese = new ChoiceBox();
    
    @FXML
    private ChoiceBox<String> setPineapple = new ChoiceBox();
    
    @FXML
    private ChoiceBox<String> setGreenPepper = new ChoiceBox();

    @FXML
    private ChoiceBox<String> setHam = new ChoiceBox();

    private ObservableList<String> sizeList = FXCollections.observableArrayList(
    		"small", "medium", "large");
    
    private ObservableList<String> cheeseList = FXCollections.observableArrayList(
    		"single", "double", "triple");
    
    private ObservableList<String> pineappleList = FXCollections.observableArrayList(
    		"none", "single");
    
    private ObservableList<String> pineappleList2 = FXCollections.observableArrayList("none");
    
    private ObservableList<String> greenPepperList = FXCollections.observableArrayList(
    		"none", "single");
    
    private ObservableList<String> greenPepperList2 = FXCollections.observableArrayList("none");
    
    private ObservableList<String> hamList = FXCollections.observableArrayList(
    		"none", "single");
    
    private Pizza newPizza;
    private LineItem newLineItem;
    private int num = 1;
	private ArrayList<LineItem> orders = new ArrayList<>();
    
    @FXML
    void initialize() {
        assert amount != null : "fx:id=\"amount\" was not injected: check your FXML file 'Main.fxml'.";
        assert setCheese != null : "fx:id=\"setCheese\" was not injected: check your FXML file 'Main.fxml'.";
        assert setGreenPepper != null : "fx:id=\"setGreenPepper\" was not injected: check your FXML file 'Main.fxml'.";
        assert setHam != null : "fx:id=\"setHam\" was not injected: check your FXML file 'Main.fxml'.";
        assert setPineapple != null : "fx:id=\"setPineapple\" was not injected: check your FXML file 'Main.fxml'.";
        assert setSize != null : "fx:id=\"setSize\" was not injected: check your FXML file 'Main.fxml'.";
        
        setSize.setValue("small");
        setSize.setItems(sizeList);
        setCheese.setValue("single");
        setCheese.setItems(cheeseList);
        setGreenPepper.setValue("none");
        setPineapple.setValue("none");
        setHam.setValue("single");
        setHam.setItems(hamList);
        // Set different item list for pineapple and green pepper according to different choices of ham
        setHam.valueProperty().addListener((Observable,oldVal,newVal)->
        {
        	switch(newVal) {
        	case "single":
        		setGreenPepper.setItems(greenPepperList);
                setPineapple.setItems(pineappleList);
                break;
        	case "none":
        		setGreenPepper.setItems(greenPepperList2);
                setPineapple.setItems(pineappleList2);
                break;
        	}
        });

        setSize.valueProperty().addListener((observableValue, oldVal, newVal) ->
        {
        	changeCost();
        });
        setCheese.valueProperty().addListener((observableValue, oldVal, newVal) ->
        {
        	changeCost();
        });
        setHam.valueProperty().addListener((observableValue, oldVal, newVal) ->
        {
        	changeCost();
        });
        setGreenPepper.valueProperty().addListener((observableValue, oldVal, newVal) ->
        {
        	String greenPepperValue = setGreenPepper.getValue();
        	String hamValue = setHam.getValue();
        	if (greenPepperValue.equals("single") && hamValue.equals("none")) {
        		setGreenPepper.setValue("none");
        	}
        	changeCost();
        });
        setPineapple.valueProperty().addListener((observableValue, oldVal, newVal) ->
        {
        	String pineappleValue = setPineapple.getValue();
        	String hamValue = setHam.getValue();
        	if (pineappleValue.equals("single") && hamValue.equals("none")) {
        		setPineapple.setValue("none");
        	}
        	changeCost();
        });
        amount.textProperty().addListener((observableValue, oldVal, newVal) ->
    	{
    		if (newVal.equals("")) {
    			amount.setText("");
    			return;
    		}
        	int numSet = 1;
        	try {
        		numSet = Integer.parseInt(newVal);
        	}catch (NumberFormatException e) {
        		amount.setText(oldVal);
        		return;
        	}
        	if (numSet < 1 || numSet > 100) {
    			amount.setText(oldVal);
    			try {
					throw new IllegalPizza("The pizza number is illegal.");
				} catch (IllegalPizza e) {
					e.printStackTrace();
				}
        	}
    		num = numSet;
    		changeCost();
        });
        // Show the order list when clicking on display icon.
        display.setOnAction(event ->
    	{
    		if (cost.getText().equals(""))
    			return;
    		float totalCost = 0;
    		String output = "";
    		orders.add(newLineItem);
    		orders.sort(null);
    		for (LineItem order : orders) {
    			totalCost += order.getCost();
    			output += order + "\n";
    		}
    		output += "Total Cost: " + String.format("$%.2f", totalCost);
    		orderArea.setText(output);
    	});
    	changeCost();
     } // end initialize
    
    private void changeCost() {
    	String size = setSize.getValue();
    	String cheese = setCheese.getValue();
       	String pineapple = setPineapple.getValue();
    	String greenPepper = setGreenPepper.getValue();
    	String ham = setHam.getValue();
    	try {
    		newPizza = new Pizza(size, cheese, pineapple, greenPepper, ham);
    	}catch (IllegalPizza e) {
    		System.err.println("This pizza is illegal!");
    		return;
    	}
    	price.setText(String.format("$%.2f", newPizza.getCost()));
    	try {
    		newLineItem = new LineItem(num, newPizza);
    	}catch (IllegalPizza e) {
    		System.err.println("This is an illegal pizza order.");
    		return;
    	}
    	cost.setText(String.format("$%.2f", newLineItem.getCost()));
    } // end changeCost

} // end controller
