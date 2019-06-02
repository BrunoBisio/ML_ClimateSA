package climate.service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import climate.utils.PositionUtils;
import climate.constants.Climate;
import climate.model.Constelation;
import climate.model.Position;
import climate.model.PrecipitationReport;
import climate.service.definition.ClimateService;

@Service
public class ClimateServiceImpl implements ClimateService {
    
    // obtener clima
    @Override
    public String getClimate(Constelation constelation, long day) {
        // actualizo la posicion de los planetas
        constelation.update(day);
        // obtengo los vectores
        Position[] pos = constelation.getPositions();
        // obtengo el clima dependiendo de la posicion de los planetas
        if (PositionUtils.areCollinear(pos)) {
            Position[] posWithCenter = new Position[pos.length + 1];
            for (int i = 0; i < pos.length; i++) {
                posWithCenter[i] = pos[i];
            }
            posWithCenter[posWithCenter.length-1] = constelation.getSun().getPosition();
            return PositionUtils.areCollinear(posWithCenter) ? Climate.DRAUGH.getValue() : Climate.OPTIMUM.getValue();
        } else if (PositionUtils.isPositionInPoligon(pos, constelation.getSun().getPosition())) {
            return Climate.PRECIPITATION.getValue();
        }
        return "";
    }

    // 1. ¿Cuántos períodos de sequía habrá?
    @Override
    public long getDroughPeriods(Constelation constelation) {
        return getPeriod(constelation, Climate.DRAUGH.getValue());
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
            if (climate.equals(Climate.PRECIPITATION.getValue())) {
                perimeter = PositionUtils.getPerimeter(constelation.getPositions());
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
                if (climate.equals(Climate.PRECIPITATION.getValue())) ammount++;
            }
        }

        return new PrecipitationReport<>(ammount, report);
    }

    // 3. ¿Cuántos períodos de condiciones óptimas de presión y temperatura habrá?
    @Override
    public long getOptimumPeriods(Constelation constelation) {
        return getPeriod(constelation, Climate.OPTIMUM.getValue());
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