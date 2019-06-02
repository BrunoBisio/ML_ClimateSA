package climate.service.definition;

import java.time.LocalDate;
import java.util.List;

import climate.model.ClimateReport;
import climate.model.Constelation;

public interface ClimateReportService {
    
    public ClimateReport getClimateReport(long day, Constelation constelation) throws Exception;

    public List<ClimateReport> getClimateReportBetween(Constelation constelation, LocalDate dateFrom, LocalDate dateTo) throws Exception;
}