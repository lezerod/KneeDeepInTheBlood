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

	public Item(int id, String bezeichner, Position positon, int width,
			int height, Effekt effekt) {
		super(id, bezeichner, positon, width, height);
		this.effekt = effekt;
	}
}
