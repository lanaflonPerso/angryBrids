package iut.k2.data;


import javax.swing.JComponent;

import iut.k2.physics.Coordinate2D;

public abstract class Entity extends JComponent{

    private Coordinate2D c;
    private double positionBec;

    public Entity(Coordinate2D c) {
        this.c = c;
    }

    public void move(double x, double y) {
        setCoordinate(getCoordinate().add(x,y));
    }

    public Coordinate2D getCoordinate() {
        return c;
    }

    public void setCoordinate(Coordinate2D c) {
        this.c = c;
    }

}
