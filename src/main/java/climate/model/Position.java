package climate.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Position {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double x;
    private double y;
    
    public Position() {
    }

    public Position (Position p){
        this.x = p.getX();
        this.y = p.getY();
    }

    public Position (double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void update (double x, double y) {
        this.x = x;
        this.y = y;
    }

    public long getId() {
        return this.id;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

}