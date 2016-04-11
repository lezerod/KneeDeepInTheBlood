package model;
/**
 * Diese Klasse erbt von der Abstrakten Klasse {@link Alien}. Die Werte erhaelt sie von dem Interface {@link AlienNichtImmunWerte}
 * @author til
 *
 */
public class AlienNichtImmun extends Alien implements AlienNichtImmunWerte
{

	public AlienNichtImmun() 
	{	
		super(model.AlienNichtImmunWerte.bezeichner, model.AlienNichtImmunWerte.position, width, height, winkel, speed, health, waffe, immun);
		// TODO Auto-generated constructor stub
	}
}
