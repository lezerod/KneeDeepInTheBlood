package model;

public abstract class Effekt extends Waffe
{
	private int value = 0;
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Effekt(int id, String bezeichner, Effekt effekt) 
	{
		super(id, bezeichner, effekt);
		// TODO Auto-generated constructor stub
	}
	
	
	

}
