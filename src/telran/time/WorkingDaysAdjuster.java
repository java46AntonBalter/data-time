package telran.time;

import java.time.LocalDate;
import java.time.temporal.*;
import java.util.Arrays;

public class WorkingDaysAdjuster implements TemporalAdjuster {

	int[] daysOff;
	int nDays;

	public int[] getDaysOff() {
		return daysOff;
	}

	public void setDaysOff(int[] daysOff) {
		this.daysOff = daysOff;
	}

	public int getnDays() {
		return nDays;
	}

	public void setnDays(int nDays) {
		this.nDays = nDays;
	}

	public WorkingDaysAdjuster(int[] daysOff, int nDays) {

		this.daysOff = daysOff;
		this.nDays = nDays;
	}

	public WorkingDaysAdjuster() {
	}

	@Override
	public Temporal adjustInto(Temporal temporal) {
		LocalDate res = LocalDate.from(temporal);
		if (Arrays.equals(daysOff, new int[]{ 1, 2, 3, 4, 5, 6, 7 })) {
			return res;
		} else if (daysOff.length == 0) {
			return res.plusDays(nDays);
		}
		LocalDate temp = res;
		for (int i = 0; i < nDays; i++) {
			temp = temp.plusDays(1);
			if (!isDayOff(temp.getDayOfWeek().ordinal() + 1)) {
				res = temp;
			} else {
				i--;
			}
		}
		return res;
	}

	private boolean isDayOff(int num) {
		for (int i : daysOff) {
			if (num == i) {
				return true;
			}
		}
		return false;
	}

}
