
public class Triangle 
{
	private static int id = 0;
	
	private final int myId;
	private final Edge[] edges = new Edge[3];
	
	//precalculated values to speed up Barycentric calculation
	//which may be frequent for some triangles (especially early ones)
	private double bx, by, cx, cy, bbDot, bcDot, ccDot, d;
	boolean barycentricPreCalc = false;
	
	//triangles consist of edges not points (for convenience later in the 
	//flipping portion of the triangularization
	//assumes pairwise sharing of exactly one unique point between edges
	public Triangle (Edge edgeA, Edge edgeB, Edge edgeC)
	{		
		this.edges[0] = edgeA;
		this.edges[1] = edgeB;
		this.edges[2] = edgeC;
		
		barycentricPrecalc();
		
		myId = id++;
	}
	
	private void barycentricPrecalc()
	{
		Point pointA = edges[0].getPoints()[0];
		Point pointB = edges[0].getPoints()[1];
		Point pointC = null;
		
		//find common point in other two edges
		//floor and mod notation used to keep loop depth of one for early breaking
		for (int i = 0; i < 4; i++)
		{
			if (edges[1].getPoints()[i / 2].getId() == 
				edges[2].getPoints()[i % 2].getId())
			{
				pointC = edges[1].getPoints()[i / 2];
				
				break;
			}
		}
		
		if (pointC != null)
		{
			bx = pointB.getX() - pointA.getX();
			by = pointB.getY() - pointA.getY();;
			cx = pointC.getX() - pointA.getX();
			cy = pointC.getY() - pointA.getY();
			bbDot = Math.pow(bx, 2) + Math.pow(by, 2);
			bcDot = (bx * cx) + (by * cy);
			ccDot = Math.pow(cx, 2) + Math.pow(cy, 2);
			d = 1 / ((bbDot * ccDot) - Math.pow(bcDot,  2));
			
			barycentricPreCalc = true;
		}
	}
	
	public boolean pointInsideTriangle(Point pointP)
	{
		Point pointA = edges[0].getPoints()[0];
		
		if (barycentricPreCalc)
		{
			double px = pointP.getX() - pointA.getX();
			double py = pointP.getY() - pointP.getY();
			double pbDot = (px * bx) + (py * by);
			double pcDot = (px * cx) + (py * cy);
			
			double u = ((ccDot * pbDot) - (bcDot * pcDot)) * d;
			double v = ((bbDot * pcDot) - (bcDot * pbDot)) * d;
			
			return ((u >= 0) && (v >= 0) && ((u + v) < 1));
		}
		else
		{
			return false;
		}
	}

	public double getEdgeOppositeAngle(Edge edgeC)
	{
		double lengthC = edgeC.getLength();	
		double lengthA = (lengthC / 2), lengthB = (lengthC / 2);
		
		for (int i = 0; i < 3; i++)
		{
			if (edgeC.getId() == edges[i].getId())
			{
				lengthA = edges[(i + 1) % 3].getLength();
				lengthB = edges[(i + 2) % 3].getLength();
				
				break;
			}
		}
		
		return Math.acos((Math.pow(lengthA, 2) + 
						   Math.pow(lengthB, 2) - 
						   Math.pow(lengthC, 2)) / (2 * lengthA * lengthB));
	}
	
	public Edge[] getEdges()
	{
		return edges;
	}

	public int getId() 
	{
		return myId;
	}
}
