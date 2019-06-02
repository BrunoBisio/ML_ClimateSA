package climate.service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import climate.model.ClimateReport;
import climate.model.Constelation;
import climate.service.definition.ClimateReportService;
import climate.service.definition.ClimateService;

@Service
public class ClimateReportServiceImpl implements ClimateReportService {

    @Autowired
    private ClimateService climateService;

    @Override
    public ClimateReport getClimateReport(long day, Constelation constelation) throws Exception {
        if (checkDay(day)) {
            ClimateReport report = new ClimateReport();
            report.setWeather(climateService.getClimate(constelation, day));
            report.setDate(LocalDate.now().plusDays(day));
            return report;
        } else {
            throw new Exception("La fecha no debe superar los 10 años");
        }
    }

    // se deberia crear un clon para no afectar a la persistida Constelation constAux = (Constelation) constelation.clone();
    @Override
    public List<ClimateReport> getClimateReportBetween(Constelation constelation, LocalDate dateFrom, LocalDate dateTo)
            throws Exception {
        long difInDays = ChronoUnit.DAYS.between(dateFrom, dateTo);
        LinkedList<ClimateReport> climateReports = new LinkedList<>();
        for(int i = 1;i < difInDays;i++) {
            climateReports.add(getClimateReport(i, constelation));
        }
        return climateReports;
    }

    private boolean checkDay(long day) {
        return (day <= ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.now().plusYears(10)));
    }
}