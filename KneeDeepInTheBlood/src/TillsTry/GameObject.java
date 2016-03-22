package TillsTry;

public class GameObject 
{
	public static final float VISIBLE_DURATION = 1.3f;
	
	public int x, y, width, height;
	
	public float visibleTime;
	
	public boolean isClicked;
	public boolean isRemovable;
	
	public GameObject(int x, int y, int width, int height) 
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.visibleTime = 0;
		this.isClicked = false;
		this.isRemovable = false;
	}
	
	public void update(float delta)
	{
		visibleTime+= delta;
		
		if(visibleTime >= VISIBLE_DURATION || isClicked)
		{
			isRemovable = true;
		}
	}
	
	public boolean overlaps(int x, int y, int width, int height)
	{
		if(this.x + this.width >= x && this.x <= x + width)
		{
			if(this.y + this.height >= y && this.y <= y + height)
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean contains(int x, int y)
	{
		if(x >= this.x && x <= this.x + this.width)
		{
			if(y >= this.y && y <= this.y + this.height)
				return true;
		}
		return false;
	}
	
	
}
