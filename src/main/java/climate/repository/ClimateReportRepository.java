package climate.repository;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;

import climate.model.ClimateReport;

public interface ClimateReportRepository extends CrudRepository<ClimateReport, Long>{

    ClimateReport findByDate(LocalDate date);

    ClimateReport findById(long id);
}