package model;

public abstract class Effekt extends Gameobject
{
	private int value = 0;
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Effekt(int id, String bezeichner) 
	{
		super(id, bezeichner);
		// TODO Auto-generated constructor stub
		//try
	}
	
	
	

}
