package model;

public class Waffe extends Gameobject
{
	private Effekt effekt = new Effekt(null, 0, false){};

	public Effekt getEffekt() {
		return effekt;
	}

	public void setEffekt(Effekt effekt) 
	{
		this.effekt = effekt;
	}
	
	public Waffe(int id, String bezeichner, Effekt effekt) 
	{
		super(bezeichner);
		this.effekt = effekt;
	}
}



	

	
	
	
	


	

