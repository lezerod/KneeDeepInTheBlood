package model;
/**
 * Dies ist eine Abstrakte Klasse welche von {@link Gameobject} erbt. Sie besitzt eine setter und getter Methode um auf den Integer Value zuzugreifen.
 * @author til
 *
 */
public abstract class Effekt extends Gameobject
{
	private int value = 0;


	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Effekt(String bezeichner, int value) {
		super(bezeichner);
		this.value = value;
	}
	
	





	
	
	


}
	
	
	


