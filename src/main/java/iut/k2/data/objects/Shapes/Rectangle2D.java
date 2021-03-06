package iut.k2.data.objects.Shapes;

import iut.k2.physics.Coordinate2D;

/**
 * 
 * @author Benjamin D'Hoop
 *
 */
public class Rectangle2D implements Shape{

	private double x;
	private double y;
	private double width;
	private double height;
	
	public Rectangle2D(double x, double y, double width, double height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	@Override
	public boolean contains(double x, double y) {
		double xMin = this.x;
		double xMax = xMin + width;
		double yMin = this.y;
		double yMax = yMin + height;
		return (x >= xMin && x <= xMax) && (y >= yMin && y <= yMax);
	}

	@Override
	public boolean contains(double x, double y, double w, double h) {
		boolean retour = true;
		
		//Données de la figure en paramère
		double xMin = x;
		double xMax = x + w;
		double yMin = y;
		double yMax = y + h;
		
		return contains(xMin, yMin)
		&& contains(xMin, yMax) 
		&& contains(xMax, yMin)
		&& contains(xMax, yMax);
	}

	@Override
	public boolean contains(Shape s) {
		//Données de la figure en paramère
		if(s instanceof Rectangle2D){
			Rectangle2D r = (Rectangle2D)s;
			double xMin = r.getX();
			double yMin = r.getY();
			double width = r.getWidth();
			double height = r.getHeight();
			
			return contains(xMin, yMin, width, height);
		}else if(s instanceof Rectangle){
			Rectangle r = (Rectangle)s;
			return contains((double)r.getX(),
					(double)r.getY(), 
					(double)r.getWidth(), 
					(double)r.getHeight());
		}else if(s instanceof Circle){
			Circle c = (Circle)s;
			return contains(c.getLargeHitBox().getX(), 
					c.getLargeHitBox().getY(), 
					c.getLargeHitBox().getWidth(), 
					c.getLargeHitBox().getHeight());
		}
		return false;
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y , (int)width, (int)height);
	}
	
	@Override
	public Rectangle2D getBounds2D() {
		
		double x = (double) this.x;
		double y = (double) this.y;
		double width = (double)(this.width);
		double height = (double)(this.height);
		
		return new Rectangle2D(x, y, width, height);
	}

	@Override
	public boolean intersects(double x, double y, double w, double h) {
		
		double xMin = x;
		double xMax = x + w;
		double yMin = y;
		double yMax = y + h;
		
		if(h == 0){
			return contains(xMin, yMin) ||
					contains(xMax, yMin);
		}else if(w == 0){
			return contains(xMin, yMin) ||
					contains(xMin, yMax);
		}else{
			return contains(xMin,yMin) || 
				contains(xMin,yMax) ||
				contains(xMax,yMin) ||
				contains(xMax,yMax);
		}
	}

	@Override
	public boolean intersects(Shape s) {
		if(s instanceof Rectangle2D){
			Rectangle2D r2 = (Rectangle2D)s;
			return intersects(r2.getX(), 
				r2.getY(), 
				r2.getWidth(), 
				r2.getHeight()) || r2.intersects(x, y, width, height);
		}else if(s instanceof Circle){
			Circle c = (Circle)s;
			return c.intersects(this);
		}
		return false;
	}

	@Override
	public void move(Coordinate2D c) {
		x += c.getX();
		y += c.getY();
	}

}
