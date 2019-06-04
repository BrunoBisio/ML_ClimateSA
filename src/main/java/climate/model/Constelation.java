package climate.model;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;

@Entity
public class Constelation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToMany
    @OrderColumn(name="id")
    private Planet[] planets;
    @OneToOne
    private Planet sun;
   
    public Constelation() {
    }

    public Constelation(int n) {
        this.planets = new Planet[n];
        this.sun = new Planet();
    }

    public Constelation(Constelation c) {
        this.planets = new Planet[c.getPlanets().length];
        for (int i = 0; i < c.getPlanets().length; i++){
            this.planets[i] = new Planet(c.getPlanets()[i]);
        }
        this.sun = new Planet(c.getSun());
    }

    public long getId() {
        return this.id;
    }

    public Planet[] getPlanets() {
        return planets;
    }

    public void setPlanets(Planet[] planets) {
        this.planets = planets;
    }

    public Planet getSun() {
        return sun;
    }

    public void setSun(Planet sun) {
        this.sun = sun;
    }

	public Position[] getPositions() {
		return Arrays.stream(planets).map(p -> p.getPosition()).toArray(Position[]::new);
	}

	public void update(long day) {
        for (int i = 0; i < planets.length; i++) {
            planets[i].update(day);
        }
    }
}