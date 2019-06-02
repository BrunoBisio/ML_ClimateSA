package climate.service.definition;

import java.time.LocalDate;

import climate.model.Constelation;
import climate.model.PrecipitationReport;

public interface ClimateService {
    
    public String getClimate(Constelation constelation, long day);

    public long getDroughPeriods(Constelation constelation);

    public PrecipitationReport<LocalDate> getPrecipitationPeriods(Constelation constelation);

    public long getOptimumPeriods(Constelation constelation);
}