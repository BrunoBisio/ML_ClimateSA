package climate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import climate.model.ClimateReport;
import climate.service.definition.ClimateReportService;

@RestController
public class ClimateReportController {

    @Autowired
    private ClimateReportService climateReportService;

    @RequestMapping("/clima")
    public ClimateReport getClimateReport(@RequestParam(value="dia")String day) throws Exception {
        if (tryInteger(day)) {
            return climateReportService.retrieveClimateReport(Integer.valueOf(day));
        }
        return new ClimateReport();
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
