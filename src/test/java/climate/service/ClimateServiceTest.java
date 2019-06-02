package climate.service;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import climate.model.Constelation;
import climate.model.Planet;
import climate.model.PrecipitationReport;
import climate.service.definition.ClimateService;
import climate.service.impl.ClimateServiceImpl;

@RunWith(SpringRunner.class)
public class ClimateServiceTest {

    @TestConfiguration
    static class ClimateServiceImplTestContextConfiguration {
  
        @Bean
        public ClimateService climateService() {
            return new ClimateServiceImpl();
        }
    }
    
    @Autowired
    private ClimateService climateService;

    private Planet farengi, betasoide, vulcano, sun;
    private Constelation constelation;
    
    public void initConstelation() {
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

    @Test
    public void draughPeriodsTest() {
        initConstelation();
        long draughPeriods = climateService.getDroughPeriods(constelation);
        assertTrue(draughPeriods > 0);
    }

    @Test
    public void precipitationPeriodsTest() {
        initConstelation();
        PrecipitationReport<LocalDate> report = climateService.getPrecipitationPeriods(constelation);
        assertTrue(report != null);
    }

    @Test
    public void optimumPeriodsTest() {
        initConstelation();
        long optimumPeriods = climateService.getOptimumPeriods(constelation);
        assertTrue(optimumPeriods > 0);
    }
}