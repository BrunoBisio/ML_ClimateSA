package climate.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Planet implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double velocity;
    private long distance;
    private String name;
    @OneToOne
    private Position position;

    public Planet() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Object clone() throws CloneNotSupportedException { 
        return super.clone(); 
    }

	public void update(double day) {
        double angle = Math.toRadians(velocity * day);
        double x = this.position.getX() + distance * Math.cos(angle);
        double y = this.position.getY() + distance * Math.sin(angle);
        this.setPosition(x, y);
    }
}