package application;


import java.io.Serializable;

public class Pizza implements Serializable {

	private static final long serialVersionUID = 1856146538106631512L;
	private String size;
	private String cheese;
	private String pineapple;
	private String greenPepper;
	private String ham;
	
	private final float SMALL_COST = 7.00F;	// Includes one cheese
	private final float MEDIUM_COST = 9.00F;
	private final float LARGE_COST = 11.00F;
	private final float COST_PER_TOPPING = 1.50F;
	
	private void setSize(String size) throws IllegalPizza {
		if (size == null)
			throw new IllegalPizza("Size not provided!");
		size = size.toLowerCase();
		if (size.equals("small") || size.equals("medium") || size.equals("large"))
			this.size = size;
		else
			throw new IllegalPizza("Illegal size!");		
	} // end setSize
	
	private void setCheese(String cheese) throws IllegalPizza {
		if (cheese == null)
			throw new IllegalPizza("Cheese not provided!");
		cheese = cheese.toLowerCase();
		if (cheese.equals("single") || cheese.equals("double") || cheese.equals("triple"))
			this.cheese = cheese;
		else
			throw new IllegalPizza("Illegal cheese!");		
	} // end setCheese
	
	private void setPineapple(String pineapple) throws IllegalPizza {
		if (pineapple == null)
			throw new IllegalPizza("Pineapple not provided!");
		pineapple = pineapple.toLowerCase();
		if (pineapple.equals("none") || pineapple.equals("single"))
			this.pineapple = pineapple;
		else
			throw new IllegalPizza("Illegal pineapple!");		
	} // end setPineapple
	
	private void setGreenPepper(String greenPepper) throws IllegalPizza {
		if (greenPepper == null)
			throw new IllegalPizza("Green pepper not provided!");
		greenPepper = greenPepper.toLowerCase();
		if (greenPepper.equals("none") || greenPepper.equals("single"))
			this.greenPepper = greenPepper;
		else
			throw new IllegalPizza("Illegal green pepper!");		
	} // end setGreenPepper
	
	private void setHam(String ham) throws IllegalPizza {
		if (ham == null)
			throw new IllegalPizza("Ham not provided!");
		ham = ham.toLowerCase();
		if (ham.equals("none") || ham.equals("single"))
			this.ham = ham;
		else
			throw new IllegalPizza("Illegal ham!");		
	} // end setHam
	
	/**
	 * The Pizza constructor.
	 * @param size Must be "small", "medium" or "large".
	 * @param cheese Must be "single", "double" or "triple".
	 * @param pineapple Must be "none" or "single".
	 * @param greenPepper Must be "none" or "single".
	 * @param ham Must be "none" or "single".
	 * @throws IllegalPizza If any of the parameters are not legal or if ham is "none" and
	 * pineapple or greenPepper is not "none".
	 */
	public Pizza(String size, String cheese, String pineapple, String greenPepper, String ham) throws IllegalPizza {
		setSize(size);
		setCheese(cheese);
		setPineapple(pineapple);
		setGreenPepper(greenPepper);
		setHam(ham);
		if (this.ham.equals("none") && (!this.pineapple.equals("none") || !this.greenPepper.equals("none")))
			throw new IllegalPizza("You must have ham with your pineapple or green pepper!");
	} // end full parameter constructor
	
	/**
	 * The default Pizza constructor.
	 * @throws IllegalPizza Should not be thrown.
	 */
	public Pizza() throws IllegalPizza {
		this("small", "single", "none", "none", "single");
	} // end default constructor
	
	private float getSizeCost(String size) {
		switch (size) {
		case "small":
			return SMALL_COST;
		case "medium":
			return MEDIUM_COST;
		}
		return LARGE_COST;
	} // end getSizeCost
	
	private int translateTopping(String topping) {
		switch (topping) {
		case "none":
			return 0;
		case "single":
			return 1;
		case "double":
			return 2;
		}
		return 3;
	} // end translateTopping
	
	/**
	 * Returns the cost of the current Pizza object.
	 * @return The cost in dollars.  No tax.
	 */
	public float getCost() {
		float cost = getSizeCost(size);
		cost += ((translateTopping(cheese) - 1) + translateTopping(pineapple) + 
				  translateTopping(greenPepper) + translateTopping(ham)) * COST_PER_TOPPING;
		return cost;
	} // end getCost
	
	/**
	 * Returns a string representation of the current object.
	 * @return A string description of the current Pizza object.
	 */
	public String toString() {
		String out = size + " pizza, " + cheese + " cheese";
		if (pineapple.equals("single"))
			out += ", pineapple";
		if (greenPepper.equals("single"))
			out += ", green pepper";
		if (ham.equals("single"))
			out += ", ham";
		out += String.format(". Cost: $%.2f each.", getCost());
		return out;
	} // end toString
	
	/**
	 * Tests to see if the current object is equal to the supplied Pizza object. Equality is
	 * defined as all attributes being exactly equal.
	 * @param other The supplied object for comparison.
	 * @return false if the objects are not equal or the supplied object is not a Pizza, true
	 * otherwise.
	 */
	public boolean equals(Object other) {
		if (other instanceof Pizza) {
			Pizza otherP = (Pizza)other;
			return size.equals(otherP.size) && cheese.equals(otherP.cheese) && 
				   pineapple.equals(otherP.pineapple) && greenPepper.equals(otherP.greenPepper) && 
				   ham.equals(otherP.ham);
		}
		return false;
	} // end equals
	
	/**
	 * Creates and returns a clone of the current Pizza object.
	 * @return A deep copy or clone of the current object. 
	 */
	public Pizza clone() {
		Pizza outP = null;
		try {
			outP = new Pizza(size, cheese, pineapple, greenPepper, ham);
		} catch (IllegalPizza e) {
		}
		return outP;
	} // end clone
	
} // end Pizza class
