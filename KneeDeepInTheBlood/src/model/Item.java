package model;

public class Item extends SpielfeldObject
{
	private Effekt effekt = new Effekt(null, 0, false){};

	public Effekt getEffekt() {
		return effekt;
	}

	public void setEffekt(Effekt effekt) {
		this.effekt = effekt;
	}

	public Item(String bezeichner, Position positon, int width,
			int height, Effekt effekt) {
		super(bezeichner, positon, width, height);
		this.effekt = effekt;
	}
}
