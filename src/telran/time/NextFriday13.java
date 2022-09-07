package telran.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.*;

public class NextFriday13 implements TemporalAdjuster {

	@Override
	public Temporal adjustInto(Temporal temporal) {
		LocalDate evilFriday = LocalDate.from(temporal);
		if (evilFriday.getDayOfMonth() < 13) {
			evilFriday = evilFriday.withDayOfMonth(13);
		} else {
			evilFriday = evilFriday.plusMonths(1).withDayOfMonth(13);
		}

		while (evilFriday.getDayOfWeek() != DayOfWeek.FRIDAY) {
			evilFriday = evilFriday.plusMonths(1).withDayOfMonth(13);
		}
		return temporal.with(evilFriday);
	}

}
