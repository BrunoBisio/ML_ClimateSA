package climate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import climate.model.ClimateReport;
import climate.model.Constelation;
import climate.model.Planet;
import climate.service.definition.ClimateReportService;

@RestController
public class ClimateReportController {

    @Autowired
    private ClimateReportService climateReportService;

    private Planet farengi, betasoide, vulcano, sun;
    private Constelation constelation;
    
    private void initConstelation() {
        // Farengi
        farengi = new Planet(1, 500, 500, 0);
        // Betasoide
        betasoide = new Planet(3, 2000, 2000, 0);
        // Vulcano
        vulcano = new Planet(-5, 1000, 1000, 0);
        // Sun
        sun = new Planet(0, 0, 0, 0);
        Planet[] planets = {farengi, betasoide, vulcano};
        constelation = new Constelation(3);
        constelation.setSun(sun);
        constelation.setPlanets(planets);
    }

    @RequestMapping("/clima")
    public ClimateReport getClimateReport(@RequestParam(value="dia")String day) throws Exception {
        if (tryInteger(day)) {
            initConstelation();
            return climateReportService.getClimateReport(Integer.valueOf(day), constelation);
        }
        return new ClimateReport();
        // return new ClimateReport("dia1", "clima1");
    }

    private boolean tryInteger(String number) {
        try {
            Integer.valueOf(number);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
