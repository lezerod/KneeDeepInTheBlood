package model;

public abstract class Gameobject 
{
	private int id = 0;
	private String bezeichner = null;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBezeichner() {
		return bezeichner;
	}
	public void setBezeichner(String bezeichner) {
		this.bezeichner = bezeichner;
	}
	public Gameobject(int id, String bezeichner) {
		super();
		this.id = id;
		this.bezeichner = bezeichner;
	}
}
