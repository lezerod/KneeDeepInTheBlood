package model;

public abstract class SpielfeldObject extends Gameobject
{
	private Position positon = new Position(0, 0);
	
	public Position getPositon() {
		return positon;
	}

	public void setPositon(Position positon) {
		this.positon = positon;
	}

	private SpielfeldObject(int id, String bezeichner) 
	{
		super(id, bezeichner);
		// TODO Auto-generated constructor stub
	}

	public SpielfeldObject(int id, String bezeichner, Position positon) {
		super(id, bezeichner);
		this.positon = positon;
	}
}
