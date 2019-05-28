package model;

import java.util.Arrays;

public class Constelation {

    private Planet[] planets;
    private Planet sun;
   
    public Constelation(int n) {
        this.planets = new Planet[n];
        this.sun = new Planet();
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