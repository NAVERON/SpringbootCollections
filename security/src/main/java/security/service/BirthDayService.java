package security.service;

import java.time.LocalDate;

public interface BirthDayService {

    LocalDate validBirthday(String rawBirthday);

    String getChineseZodiac(LocalDate birthDay);

}
