package model;

public class Planet {

    private long id;
    private double velocity;
    private long distance;
    private Position position;

    public Planet() {
    }

    public Planet(double v, long d, double x, double y) {
        this.velocity = v;
        this.distance = d;
        this.position = new Position(x, y);
    }

    public long getId() {
        return this.id;
    }

    public double getVelocity(){
        return this.velocity;
    }

    public void setVelocity(double v) {
        this.velocity = v;
    }

    public long getDistance() {
        return this.distance;
    }

    public void setDistance(long d) {
        this.distance = d;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(double x, double y) {
        if (this.position != null) {
            this.position = new Position(x, y);
        } else {
            this.position.update(x,y);
        }
    }

	public void update(double day) {
        double angle = Math.toRadians(velocity * day);
        double x = distance * Math.cos(angle);
        double y = distance * Math.sin(angle);
        this.setPosition(x, y);
    }
}