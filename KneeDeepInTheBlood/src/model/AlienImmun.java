package model;
/**
 * Diese Klasse erbt von der Abstrakten Klasse Alien. Die Werte erhaelt die Klasse von dem Interface {@link AlienImmunWerte}.
 * @author til
 *
 */
public class AlienImmun extends Alien implements AlienImmunWerte
{

	public AlienImmun() {
		super(model.AlienImmunWerte.bezeichner, model.AlienImmunWerte.position, width, height, winkelbeispawn, speedbeispawn, healthbeispawn, waffe, immun);
		// TODO Auto-generated constructor stub
	}
	



}
