package de.tub.benchmarkscheduler.api;

import de.tub.benchmarkscheduler.model.RawResult;
import de.tub.benchmarkscheduler.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/results")
public class AnalysisController {

    @Autowired
    ResultService service;


    @RequestMapping
    List<RawResult> getByDates(@RequestParam String lower, @RequestParam String upper) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date lowerDate = df.parse(lower);
        Date upperDate= df.parse(upper);
        Logger logger = Logger.getLogger("resultcontroller");
        return service.getBetweenDates(lowerDate,upperDate);
    }
}
