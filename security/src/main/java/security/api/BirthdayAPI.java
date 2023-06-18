package security.api;

import org.evs.models.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import security.service.BirthDayService;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "tester")
public class BirthdayAPI {

    private static final Logger log = LoggerFactory.getLogger(BirthdayAPI.class);

    private final BirthDayService birthDayService;
    public BirthdayAPI(BirthDayService birthDayService) {
        this.birthDayService = birthDayService;
    }

    @GetMapping(path = "valid")
    public ResponseEntity<CommonResponse> validBirthday(@Param(value = "date") String date) {
        LocalDate localDate = this.birthDayService.validBirthday(date);

        return ResponseEntity.ok(CommonResponse.buildSuccess(localDate));
    }

    @GetMapping(path = "chinese-zodiac")
    public ResponseEntity<CommonResponse> getChineseZodiac(@Param(value = "date") String date) {
        LocalDate localDate = this.birthDayService.validBirthday(date);
        String chineseZodiac = this.birthDayService.getChineseZodiac(localDate);

        return ResponseEntity.ok(CommonResponse.buildSuccess(chineseZodiac));
    }

}
