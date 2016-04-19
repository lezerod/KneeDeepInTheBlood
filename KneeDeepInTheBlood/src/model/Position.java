package model;
/**
 * In dieser Klasse wird die Position von Objekten festgelegt.
 * Sie besitzt eine print Methode um die Position der Objekte auszugeben.
 * @author til
 *
 */
public class Position 
{
	private double x = 0;
	private double y = 0;
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public Position(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public void print()
	{
		System.out.println("( "+ getX()+" | "+ getY()+" )");
	}
}

