package model;
/**
 * Diese Klasse erbt von der Abstrakten Klasse {@link LivingObject} und erhaelt ihre Werte von dem Interface {@link HeldenFahrzeugWerte}.
 * @author til
 *
 */
public class HeldenFahrzeug extends LivingObject implements HeldenFahrzeugWerte
{

	public HeldenFahrzeug() {
		super(model.HeldenFahrzeugWerte.bezeichner, model.HeldenFahrzeugWerte.positionbeispawn, widthbeispawn, heightbeispawn, winkelbeispawn, speedbeispawn, healthbeispawn, waffe, immun);
		// TODO Auto-generated constructor stub
	}
	

}

