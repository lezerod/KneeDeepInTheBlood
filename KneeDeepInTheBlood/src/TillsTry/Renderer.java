package TillsTry;

public class Renderer extends Thread
{
	private MyComponent myComponent = new MyComponent();
	
	public void run()
	{
		while(true)
		{
			myComponent.renderScreen();
			
			try
			{
				Thread.sleep(20L);
			}
			catch (InterruptedException ex)
			{
				
			}
		}
	}
}
