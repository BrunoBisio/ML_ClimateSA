package service.definition;

import model.ClimateReport;

public interface ClimateReportService {
    
    public ClimateReport getClimateReport(long day) throws Exception;
}