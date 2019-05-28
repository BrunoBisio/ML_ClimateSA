package service;

import java.time.LocalDate;
import java.util.HashMap;

import model.Constelation;
import model.PrecipitationReport;

public interface ClimateService {

    public HashMap<String, Long> getClimateReportUntil(Constelation constelation, String date);
    
    public String getClimate(Constelation constelation, long day);

    public long getDroughPeriods(Constelation constelation);

    public PrecipitationReport<LocalDate> getPrecipitationPeriods(Constelation constelation);

    public long getOptimumPeriods(Constelation constelation);
}