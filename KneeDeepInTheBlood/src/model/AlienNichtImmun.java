package model;

public class AlienNichtImmun extends Alien implements AlienNichtImmunWerte
{

	public AlienNichtImmun() 
	{	
		super(model.AlienNichtImmunWerte.bezeichner, model.AlienNichtImmunWerte.position, width, height, winkel, speed, health, waffe, immun);
		// TODO Auto-generated constructor stub
	}
}
