package model;

public class Waffe extends Gameobject
{
	private Effekt effekt = new Effekt(null, 0){};

	public Effekt getEffekt() {
		return effekt;
	}
	
	private boolean spezialwaffe = false;
	
	public boolean isSpezialwaffe() {
		return spezialwaffe;
	}

	public void setSpezialwaffe(boolean spezialwaffe) {
		this.spezialwaffe = spezialwaffe;}


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



	

	
	
	
	


	

