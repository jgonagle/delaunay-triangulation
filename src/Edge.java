public class Edge 
{
	private static int id = 0;

	private final int myId;
	private final Point[] points = new Point[2];
	private final Triangle[] triangles;

	public Edge(Point pointA, Point pointB) 
	{
		this.points[0] = pointA;
		this.points[1] = pointB;

		triangles = new Triangle[2];
		myId = id++;
	}

	// replacedTriangle = null if not replacing existing triangle
	public boolean replaceTriangle(Triangle replacedTriangle,
			Triangle newTriangle) {
		for (int i = 0; i < 2; i++) {
			if (replacedTriangle.getId() == triangles[i].getId()) {
				triangles[i] = newTriangle;

				return true;
			}
		}

		return false;
	}

	public boolean respectsDelaunayCondition() {
		if ((triangles[0] != null) && (triangles[1] != null)) {
			return ((triangles[0].getEdgeOppositeAngle(this) + triangles[1]
					.getEdgeOppositeAngle(this)) <= Math.PI);
		}

		return true;
	}
	
	public double getLength()
	{
		return Math.sqrt(Math.pow((points[0].getX() - points[1].getX()), 2) +
						  Math.pow((points[0].getY() - points[1].getY()), 2));
	}

	public Triangle[] getTriangles() {
		return triangles;
	}

	public Point[] getPoints() 
	{
		return points;
	}

	public int getId() 
	{
		return myId;
	}
}
