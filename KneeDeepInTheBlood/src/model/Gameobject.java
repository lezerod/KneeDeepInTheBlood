package model;
/**
 * Dies ist eine Abstrakte Klasse welcher �ber die getter und setter Methode den String von au�en ver�ndern l�sst.
 * @author til
 *
 */
public abstract class Gameobject 
{
	public String bezeichner = null;
	
	public String getBezeichner() {
		return bezeichner;
	}
	public void setBezeichner(String bezeichner) {
		this.bezeichner = bezeichner;
	}
	public Gameobject(String bezeichner) {
		super();
		this.bezeichner = bezeichner;
	}
}
