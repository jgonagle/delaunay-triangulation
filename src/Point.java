
public class Point 
{
	private static int id = 0;

	private final int myId;
	private final double[] coords = new double[2];
	
	public Point(double xCoord, double yCoord)
	{
		this.coords[0] = xCoord;
		this.coords[1] = yCoord;
		
		myId = id++;
	}
	
	public double[] getCoords()
	{
		return coords;
	}
	
	public double getX()
	{
		return coords[0];
	}
	
	public double getY()
	{
		return coords[1];
	}
	
	public int getId()
	{
		return myId;
	}
}
