package climate.jobs;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import climate.model.ClimateReport;
import climate.model.Constelation;
import climate.repository.ClimateReportRepository;
import climate.repository.ConstelationRepository;
import climate.service.definition.ClimateReportService;

@Component
public class ClimateReportTasks {

    @Autowired
    private ConstelationRepository constelationRepo;
    @Autowired
    private ClimateReportRepository climateReportRepo;
    @Autowired
    private ClimateReportService climateReportServ;

    private static final Logger log = LoggerFactory.getLogger(ClimateReportTasks.class);
    
    // Esta Job tenia el objetivo de actualizar diariamente el modelo de la db en caso de subirse a la nube.
    /*
    @Scheduled(cron = "0 0 2 * * *")
    public void updateModel() {
        log.info("se actualiza el estado de los planetas");
        List<Constelation> constelations = constelationRepo.findAll();
        constelations.stream().forEach(c -> c.update(1));
        constelationRepo.saveAll(constelations);
        log.info("se actualizo el estado de los planetas");
    }
    */
    // @Scheduled(cron = "0 0 21 * * *")
    @Scheduled(fixedDelay = 600000)
    public void fillClimateReports() {
        log.info("llenado de base de datos");
        List<ClimateReport> climateReports = new LinkedList<>();
        ClimateReport cr = climateReportRepo.findTopByOrderByDateDesc();
        Constelation constelation = constelationRepo.findFirstById(0);
        if (constelation != null) {
            try {
                Constelation constelationCopy = new Constelation(constelation);
                LocalDate date = cr != null ? cr.getDate().plusDays(1) : LocalDate.now();
                climateReports = climateReportServ.getClimateReportBetween(constelationCopy, date, date.plusDays(90));
                climateReportRepo.saveAll(climateReports);
            } catch (Exception ex) {
                log.error("No se pudo actualizar la base de datos: " + ex.getMessage(), (Object[])ex.getStackTrace());
            }
            log.info("se actualizo la base de datos satisfactoriamente");
        } else {
            log.error("No se encontro una constelacion sobre la que se pueda trabajar");
        }
    }
}