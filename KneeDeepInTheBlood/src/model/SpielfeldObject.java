package model;

public abstract class SpielfeldObject extends Gameobject
{
	private Position positon = new Position(0, 0);
	private int width = 0;
	private int height = 0;
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Position getPositon() {
		return positon;
	}

	public void setPositon(Position positon) {
		this.positon = positon;
	}

	private SpielfeldObject(String bezeichner) 
	{
		super(bezeichner);
		// TODO Auto-generated constructor stub
	}

	public SpielfeldObject(String bezeichner, Position positon, int width,
			int height) {
		super(bezeichner);
		this.positon = positon;
		this.width = width;
		this.height = height;
	}


	

}
