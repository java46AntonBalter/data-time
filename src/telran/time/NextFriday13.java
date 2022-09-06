package telran.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.*;

public class NextFriday13 implements TemporalAdjuster {

	@Override
	public Temporal adjustInto(Temporal temporal) {
		LocalDate start = LocalDate.from(temporal);
		if (start.getDayOfMonth() < 13) {
			start = start.withDayOfMonth(13);
		} else {
			start = start.plusMonths(1).withDayOfMonth(13);
		}

		while (start.getDayOfWeek() != DayOfWeek.FRIDAY) {
			start = start.plusMonths(1).withDayOfMonth(13);
		}
		return start;
	}

}
