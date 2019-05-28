package service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.ClimateReport;
import model.Constelation;
import service.definition.ClimateReportService;
import service.definition.ClimateService;

@Service
public class ClimateReportServiceImpl implements ClimateReportService {

    @Autowired
    private ClimateService climateService;

    public ClimateReport getClimateReport(long day) throws Exception {
        if (checkDay(day)) {
            ClimateReport report = new ClimateReport();
            Constelation constelation = null; // se obtiene de la db
            report.setClima(climateService.getClimate(constelation, day));
            report.setDia(Long.toString(day));
            return report;
        } else {
            throw new Exception("La fecha no debe superar los 10 años");   
        }
    }

    private boolean checkDay(long day) {
        return (day <= ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.now().plusYears(10)));
    }
}