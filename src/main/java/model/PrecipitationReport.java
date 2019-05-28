package model;

import java.util.ArrayList;

public class PrecipitationReport<LocalDate> {

    private long ammount;
    private ArrayList<LocalDate> maxPrecipitation;

    public PrecipitationReport(long ammount, ArrayList<LocalDate> maxPrecipitation) {
        this.ammount = ammount;
        this.maxPrecipitation = maxPrecipitation;
    }

    public long getAmmount() {
        return ammount;
    }

    public ArrayList<LocalDate> getMaxPrecipitation() {
        return maxPrecipitation;
    }

    public void setMaxPrecipitation(ArrayList<LocalDate> maxPrecipitation) {
        this.maxPrecipitation = maxPrecipitation;
    }

    public void setAmmount(long ammount) {
        this.ammount = ammount;
    }

    public void addMaxPrecipitation(LocalDate date) {
        this.maxPrecipitation.add(date);
    }
}