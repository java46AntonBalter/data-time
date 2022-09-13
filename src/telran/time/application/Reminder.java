package telran.time.application;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class Reminder {

	public static void main(String[] args) {
		int reminderParams[] = new int[0];
		try {
			reminderParams = setReminderParams(args);
			playBeeps(reminderParams[0], reminderParams[1]);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		
	}

	private static int[] setReminderParams(String[] args) throws Exception {
		if (args.length < 2) {
			throw new Exception(
					"please set the mandatory arguments - interval value and ChronoUnit enum string value (case insensitive)");
		}
		int[] res = { 10, 3600 };
		ChronoUnit intervalUnit = getUnit(args[1]);
		res[0] = getIntervalInSeconds(args[0], intervalUnit);
		if (args.length > 2) {
			res[1] = getDurationInSeconds(intervalUnit, args[2]);
		}
		return res;
	}

	private static int getDurationInSeconds(ChronoUnit unit, String duration) throws Exception {
		int durationInUnits = getIntFromString(duration);
		if (durationInUnits <= 0) {
			throw new Exception("Interval value should be a positive number");
		}
		return Math.toIntExact(TimeUnit.of(unit).toSeconds(durationInUnits));
	}

	private static int getIntFromString(String duration) throws Exception {
		int res;
		try {
			res = Integer.parseInt(duration);
		} catch (NumberFormatException e) {
			throw new Exception("value should be an integer number");
		}
		return res;
	}

	private static int getIntervalInSeconds(String amount, ChronoUnit unit) throws Exception {
		int intervalValue = getIntervalValue(amount);
		return Math.toIntExact(TimeUnit.of(unit).toSeconds(intervalValue));
	}

	private static ChronoUnit getUnit(String unit) throws Exception{
		ChronoUnit res = ChronoUnit.SECONDS;
		try {
			res = ChronoUnit.valueOf(unit.toUpperCase());
		} catch (Exception e) {
			throw new Exception("Please enter a valid unit name");
		}
		return res;
	}

	private static int getIntervalValue(String amount) throws Exception {
		int res = getIntFromString(amount);
		if (res <= 0) {
			throw new Exception("Interval value should be a positive number");
		}
		return res;
	}

	private static void playBeeps(int intervalInSeconds, int durationInSeconds) {
		Instant current = Instant.now();
		int currentFromEpochInSeconds = Math.toIntExact(current.getEpochSecond());

		while (Math.toIntExact(Instant.now().getEpochSecond()) < (currentFromEpochInSeconds + durationInSeconds)) {
			System.out.println("\007\007\007");
			try {
				TimeUnit.SECONDS.sleep(intervalInSeconds);
			} catch (Exception e) {
				Thread.currentThread().interrupt();
			}
		}

	}

}