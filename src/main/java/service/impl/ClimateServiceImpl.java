package service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import customUtils.geoUtils;
import model.Constelation;
import model.Position;
import model.PrecipitationReport;
import service.definition.ClimateService;

@Service
public class ClimateServiceImpl implements ClimateService {
    
    enum Climates {
        PRECIPITATION("PRECIPITATION"),
        DRAUGH("DRAUGH"),
        OPTIMUM("OPTIMUM");
    
        private String value;
    
        private Climates(String value){
            this.value = value;
        }
    
        public String getValue() {
            return this.value;
        }
    };
    
    // obtener reporte de clima de una constelación hasta una fecha
    @Override
    public HashMap<String, Long> getClimateReportUntil(Constelation constelation, String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateParsed = LocalDate.parse(date, formatter);
        long difInDays = ChronoUnit.DAYS.between(LocalDate.now(), dateParsed);
        HashMap<String, Long> climateReport = new HashMap<String, Long>();
        String climate = "", prevClimate = "";
        Long ammount = 0L;
        for(int i = 1;i < difInDays;i++) {
            climate = getClimate(constelation, i);
            if (!climate.equals(prevClimate) && climate != "") {
                prevClimate = climate;
                ammount = climateReport.get(prevClimate);
                if (ammount != null) {
                    ammount++;
                    climateReport.put(prevClimate, ammount);
                } else {
                    climateReport.put(prevClimate, 1L);
                }
            }
        }
        return climateReport;
    }

    // obtener clima
    @Override
    public String getClimate(Constelation constelation, long day) {
        // actualizo la posicion de los planetas
        constelation.update(day);
        // obtengo los vectores
        Position[] pos = constelation.getPositions();
        // obtengo el clima dependiendo de la posicion de los planetas
        if (geoUtils.isAlligned(pos)) {
            Position[] posWithCenter = new Position[pos.length + 1];
            for (int i = 0; i < pos.length; i++) {
                posWithCenter[i] = pos[i];
            }
            posWithCenter[posWithCenter.length-1] = constelation.getSun().getPosition();
            return geoUtils.isAlligned(posWithCenter) ? Climates.DRAUGH.getValue() : Climates.OPTIMUM.getValue();
        } else if (geoUtils.isPositionInPoligon(pos, constelation.getSun().getPosition())) {
            return Climates.PRECIPITATION.getValue();
        }
        return "";
    }

    // 1. ¿Cuántos períodos de sequía habrá?
    @Override
    public long getDroughPeriods(Constelation constelation) {
        return getPeriod(constelation, Climates.DRAUGH.getValue());
    }

    // 2. ¿Cuántos períodos de lluvia habrá y qué día será el pico máximo de lluvia?
    @Override
    public PrecipitationReport<LocalDate> getPrecipitationPeriods(Constelation constelation) {
        long difInDays = ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.now().plusYears(10));
        String climate = "", prevClimate = "";
        double perimeterMax = 0, perimeter = 0;
        ArrayList<LocalDate> report = new ArrayList<LocalDate>();
        long ammount = 0;

        for(long i = 1;i < difInDays;i++) {
            climate = getClimate(constelation, i);
            if (climate.equals(Climates.PRECIPITATION.getValue())) {
                perimeter = geoUtils.getPerimeter(constelation.getPositions());
                if (perimeterMax == perimeter) {
                    report.add(LocalDate.now().plusDays(i));
                } else {
                    perimeterMax = perimeter;
                    report.clear();
                    report.add(LocalDate.now().plusDays(i));
                }
            }
            if (!climate.equals(prevClimate) && climate != null) {
                prevClimate = climate;
                if (climate.equals(Climates.PRECIPITATION.getValue())) ammount++;
            }
        }

        return new PrecipitationReport<>(ammount, report);
    }

    // 3. ¿Cuántos períodos de condiciones óptimas de presión y temperatura habrá?
    @Override
    public long getOptimumPeriods(Constelation constelation) {
        return getPeriod(constelation, Climates.OPTIMUM.getValue());
    }

    private long getPeriod(Constelation constelation, String climatePeriod) {
        long difInDays = ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.now().plusYears(10));
        String climate = "", prevClimate = "";
        long ammount = 0;
        for(int i = 1;i < difInDays;i++) {
            climate = getClimate(constelation, i);
            if (!climate.equals(prevClimate) && climate != "") {
                prevClimate = climate;
                if (climate.equals(climatePeriod)) ammount++;
            }
        }
        return ammount;
    }

}