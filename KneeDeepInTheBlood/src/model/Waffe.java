package model;

public class Waffe extends Gameobject
{
	private Effekt effekt = new Effekt(0, null, null){};

	public Effekt getEffekt() {
		return effekt;
	}

	public void setEffekt(Effekt effekt) 
	{
		this.effekt = effekt;
	}
	
	private Waffe(int id, String bezeichner) 
	{
		super(id, bezeichner);
		// TODO Auto-generated constructor stub
	}
	
	public Waffe(int id, String bezeichner, Effekt effekt) 
	{
		super(id, bezeichner);
		this.effekt = effekt;
	}
}



	

	
	
	
	


	

