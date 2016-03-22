package model;

public class Item extends SpielfeldObject
{
	private Effekt effekt = new Effekt(0, null, null){};

	public Effekt getEffekt() {
		return effekt;
	}

	public void setEffekt(Effekt effekt) {
		this.effekt = effekt;
	}
	
	private Item(int id, String bezeichner, Position positon) {
	super(id, bezeichner, positon);
	// TODO Auto-generated constructor stub
	}

	public Item(int id, String bezeichner, Position positon, Effekt effekt) {
		super(id, bezeichner, positon);
		this.effekt = effekt;
	}
}
