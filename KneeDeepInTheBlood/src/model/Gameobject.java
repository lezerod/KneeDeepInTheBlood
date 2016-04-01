package model;

public abstract class Gameobject 
{
	public String bezeichner = null;
	
	public String getBezeichner() {
		return bezeichner;
	}
	public void setBezeichner(String bezeichner) {
		this.bezeichner = bezeichner;
	}
	public Gameobject(String bezeichner) {
		super();
		this.bezeichner = bezeichner;
	}
}
