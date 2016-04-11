package model;
/**
 * Dies ist eine Abstrakte Klasse welcher ueber die getter und setter Methode den String von auﬂen veraendern laesst.
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
