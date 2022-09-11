package telran.time.application;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

public class PrintCalendar {

	public static void main(String[] args) {
		int monthYearDay[];
		try {
			monthYearDay = getMonthYearDay(args);
			printCalendar(monthYearDay[0], monthYearDay[1], monthYearDay[2]);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private static void printCalendar(int month, int year, int startingDayOfWeek) {
		printTitle(month, year, startingDayOfWeek);
		printWeekDays(startingDayOfWeek);
		printDates(month, year, startingDayOfWeek);

	}

	private static void printDates(int month, int year, int startingDayOfWeek) {
		int column = getFirstColumn(month, year, startingDayOfWeek);
		printOffset(column);
		int nDays = getMonthDays(month, year);
		int nWeekDays = DayOfWeek.values().length;
		for (int day = 1; day <= nDays; day++) {
			System.out.printf("%4d", day);
			column++;
			if (column == nWeekDays) {
				column = 0;
				System.out.println();
			}
		}
	}

	private static int getMonthDays(int month, int year) {
		YearMonth ym = YearMonth.of(year, month);

		return ym.lengthOfMonth();
	}

	private static void printOffset(int column) {
		System.out.printf("%s", " ".repeat(column * 4));

	}

	private static int getFirstColumn(int month, int year, int startingDayOfWeek) {
		LocalDate firstMonthDate = LocalDate.of(year, month, 1);
		int weekDay = firstMonthDate.getDayOfWeek().getValue();
		int res = weekDay - startingDayOfWeek;
		return res < 0 ? res + 7 : res;
	}

	private static void printWeekDays(int startingDayOfWeek) {
		DayOfWeek dayWeeks[] = DayOfWeek.values();
		if (startingDayOfWeek != 1) {
			for (int i = 0; i < 7; i++) {
				dayWeeks[i] = DayOfWeek.of(startingDayOfWeek);
				startingDayOfWeek = startingDayOfWeek == 7 ? 1 : ++startingDayOfWeek;
			}
		}
		System.out.print("  ");
		for (DayOfWeek weekDay : dayWeeks) {
			System.out.printf("%s ", weekDay.getDisplayName(TextStyle.SHORT, Locale.getDefault()));
		}
		System.out.println();

	}

	private static void printTitle(int month, int year, int startingDayOfWeek) {
		Month monthEn = Month.of(month);
		DayOfWeek dayEn = DayOfWeek.of(startingDayOfWeek);
		System.out.printf("%s, %d, Calendar week starts from %s\n", monthEn.getDisplayName(TextStyle.FULL, Locale.getDefault()), year, dayEn.getDisplayName(TextStyle.FULL, Locale.getDefault()));

	}

	private static int[] getMonthYearDay(String[] args) throws Exception {
		LocalDate current = LocalDate.now();
		int[] res = { current.getMonthValue(), current.getYear(), DayOfWeek.MONDAY.getValue() };
		if (args.length > 0) {
			res[0] = getMonth(args[0]);
			if (args.length > 1) {
				res[1] = getYear(args[1]);
			}
			if (args.length > 2) {
				res[2] = getStartingDayOfWeek(args[2]);
			}
		}

		return res;
	}

	private static int getStartingDayOfWeek(String dayString) throws Exception {
		try {
			String dayInCaps = dayString.toUpperCase();
			int res = DayOfWeek.valueOf(dayInCaps).getValue();
			return res;
		} catch (Exception e) {
			throw new Exception("Please enter a full name of the day of the week in English. Example: Thursday");
		}

	}

	private static int getYear(String yearStr) throws Exception {
		try {
			int res = Integer.parseInt(yearStr);
			if (res <= 0) {
				throw new Exception("year should be a positive number");
			}
			return res;
		} catch (NumberFormatException e) {
			throw new Exception("year should be a number");
		}

	}

	private static int getMonth(String monthStr) throws Exception {
		try {
			int res = Integer.parseInt(monthStr);
			int nMonths = Month.values().length;
			if (res < 1 || res > nMonths) {
				throw new Exception(
						String.format("month %d is wrong value;" + " should be in the range [1, %d]", res, nMonths));
			}
			return res;
		} catch (NumberFormatException e) {
			throw new Exception("month should be a number");
		}

	}

}