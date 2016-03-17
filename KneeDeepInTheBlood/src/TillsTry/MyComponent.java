package TillsTry;

import java.awt.*;
import javax.swing.*;

public class MyComponent extends JComponent
{
	private Image myImage;
	
	public void paintComponent(Graphics g)
	{
		
	}
	
	public void renderScreen()
	{
		Graphics g = getGraphics();
		
		g.drawImage(myImage,0,0,this);
		g.drawLine(0, 0, 10, 20);
	}
}
