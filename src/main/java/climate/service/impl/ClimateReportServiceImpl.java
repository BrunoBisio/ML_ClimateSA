package climate.service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import climate.model.ClimateReport;
import climate.model.Constelation;
import climate.repository.ClimateReportRepository;
import climate.repository.ConstelationRepository;
import climate.service.definition.ClimateReportService;
import climate.service.definition.ClimateService;

@Service
public class ClimateReportServiceImpl implements ClimateReportService {

    @Autowired
    private ClimateService climateService;
    @Autowired
    private ClimateReportRepository climateReportRepo;
    @Autowired
    private ConstelationRepository constelationRepo;

    private static final Logger log = LoggerFactory.getLogger(ClimateReportServiceImpl.class);

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

    @Override
    public List<ClimateReport> getClimateReportBetween(Constelation constelation, LocalDate dateFrom, LocalDate dateTo)
            throws Exception {
        long difInDaysWithToday = ChronoUnit.DAYS.between(LocalDate.now(), dateFrom);
        long difInDays = ChronoUnit.DAYS.between(dateFrom, dateTo);
        LinkedList<ClimateReport> climateReports = new LinkedList<>();
        for(int i = 0;i < difInDays;i++) {
            climateReports.add(getClimateReport(difInDaysWithToday + i, constelation));
        }
        return climateReports;
    }

    @Override
    public ClimateReport retrieveClimateReport(Integer day) {
        ClimateReport report = null;
        if (checkDay(day)) {
            LocalDate date = LocalDate.now().plusDays(day);
            report = climateReportRepo.findByDate(date);
            if (report == null) {
                Constelation constelation = constelationRepo.findFirstById(0);
                try {
                    report = getClimateReport(day, constelation);
                } catch (Exception ex) {
                    log.error(ex.getMessage(), (Object[]) ex.getStackTrace());
                }
            }
        }
        return report != null ? report : new ClimateReport();
    }

    private boolean checkDay(long day) {
        return (day <= ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.now().plusYears(10)));
    }
}