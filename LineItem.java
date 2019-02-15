package application;

import java.io.Serializable;
/**
 * This class is part of the solution to assignment 3. It represents a single line item
 * in a pizza order. The line item contains a link to the Pizza object as well as the number
 * of these pizzas to be ordered.
 * @author Alan McLeod
 * @version 1.2
 */
public class LineItem implements Comparable<LineItem>, Serializable {

	private static final long serialVersionUID = -1791484805268644829L;
	private int numberPizzas;
	private Pizza pizza;
	
	/**
	 * The full parameter constructor.
	 * @param number The number of pizzas to be ordered. This must lie between 1 and 100, inclusive.
	 * @param pizza The Pizza object for this order.
	 * @throws IllegalPizza If the number parameter is not valid.
	 */
	public LineItem(int number, Pizza pizza) throws IllegalPizza {
		setNumber(number);
		if (pizza == null)
			throw new IllegalPizza("Pizza not supplied!");
		this.pizza = pizza;
//		this.pizza = pizza.clone();	// If Pizza becomes mutable
	} // end full parameter constructor
	
	/**
	 * Overloaded constructor.
	 * @param pizza The Pizza object for this order. The order size is assumed to be one.
	 * @throws IllegalPizza Should never be thrown.
	 */
	public LineItem(Pizza pizza) throws IllegalPizza {
		this(1, pizza);
	} // overloaded constructor
	
	/**
	 * The mutator for the number of pizzas for this line item.
	 * @param number The number of pizzas, which must lie between 1 and 100 inclusive.
	 * @throws IllegalPizza If the number is not legal.
	 */
	public void setNumber(int number) throws IllegalPizza {
		if (number < 1 || number > 100)
			throw new IllegalPizza("Illegal number of pizzas!");
		numberPizzas = number;		
	} // end setNumber
	
	/**
	 * The accessor the Pizza object.
	 * @return A pointer to the stored Pizza object.
	 */
	public Pizza getPizza() {
		return pizza;	// add .clone() if Pizza becomes mutable
	} // end getPizza
	
	/**
	 * An accessor for the number of pizzas ordered.
	 * @return The number of pizzas ordered.
	 */
	public int getNumber() {
		return numberPizzas;
	} // end getNumber
	
	/**
	 * An accessor for the total cost of this line item.
	 * @return The cost, which is the number of pizzas times the cost of the pizza itself in dollars.
	 */
	public float getCost() {
		float discount = 1.0F;
		if (numberPizzas >= 10 && numberPizzas <= 20)
			discount = 0.9F;
		else if (numberPizzas > 20)
			discount = 0.85F;
		return numberPizzas * discount * pizza.getCost();
	} // end getCost
	
	/**
	 * A string representation of this object.
	 * @return A string representation containing the number of pizzas and a description of the pizza
	 * itself.
	 */
	public String toString() {
		if (numberPizzas < 10)
			return " " + numberPizzas + " " + pizza;
		else
			return numberPizzas + " " + pizza;
	} // end toString
	
	/**
	 * Compares the current LineItem object to the one supplied. The basis for comparison is the
	 * total cost as returned by getCost().
	 * @param The supplied LineItem object.
	 * @return A negative integer if the cost of the supplied LineItem object is lower, a positive
	 * integer if it is higher and zero if the total costs are within a dollar.
	 */
	public int compareTo(LineItem item) {
		return (int)(item.getCost() - getCost());
	} // end compareTo
	
} // end LineItem class