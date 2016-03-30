package TillsTry;
import java.awt.*;

public class Sprite 
{	
	protected int x = 0;
	protected int y = 0;
	protected int width = 0;
	protected int hight = 0;
	protected boolean vis = false;
	protected image image1 = null;
	
	public Sprite(int x, int y)
	{
		this.x = x;
		this.y = y;
		vis = true;
	}

}
