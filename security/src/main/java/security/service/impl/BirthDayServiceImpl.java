package security.service.impl;

import org.evs.models.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import security.service.BirthDayService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class BirthDayServiceImpl implements BirthDayService {

    private static final Logger log = LoggerFactory.getLogger(BirthDayServiceImpl.class);

    private static final DateTimeFormatter LOCALDATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * @param rawBirthday  raw birthday
     * @return LocalDate
     */
    @Override
    public LocalDate validBirthday(String rawBirthday) {
        if(rawBirthday == null) {
            throw new BusinessException("RAW BIRTHDAY IS NULL");
        }
        return LocalDate.parse(rawBirthday, LOCALDATE_FORMATTER);
    }

    /**
     * @param birthday birthday
     * @return chinese zodiac
     */
    @Override
    public String getChineseZodiac(LocalDate birthday) {
        int year = birthday.getYear();
        return switch (year % 12) {
            case 0 -> "Monkey";
            case 1 -> "Rooster";
            case 2 -> "Dog";
            case 3 -> "Pig";
            case 4 -> "Rat";
            case 5 -> "Ox";
            case 6 -> "Tiger";
            case 7 -> "Rabbit";
            case 8 -> "Dragon";
            case 9 -> "Snake";
            case 10 -> "Horse";
            case 11 -> "Sheep";
            default -> "";
        };

    }

}
