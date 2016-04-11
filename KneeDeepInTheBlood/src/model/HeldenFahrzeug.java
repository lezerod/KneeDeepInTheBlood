package model;
/**
 * Diese Klasse erbt von der Abstrakten Klasse {@link LivingObject} und erhält ihre Werte von dem Interface {@link HeldenFahrzeugWerte}.
 * @author til
 *
 */
public class HeldenFahrzeug extends LivingObject implements HeldenFahrzeugWerte
{

	public HeldenFahrzeug() {
		super(model.HeldenFahrzeugWerte.bezeichner, model.HeldenFahrzeugWerte.position, width, height, winkel, speed, health, waffe, immun);
		// TODO Auto-generated constructor stub
	}
	

}

