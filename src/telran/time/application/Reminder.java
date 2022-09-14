package telran.time.application;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class Reminder {

	public static void main(String[] args) {
		int reminderParams[];
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
					"please set the mandatory arguments - interval length and ChronoUnit enum string value (case insensitive)");
		}
		int[] res = { 10, 3600 };
		ChronoUnit intervalUnit = getUnit(args[1]);
		res[0] = getDurationInSeconds(args[0], intervalUnit);
		if (args.length > 2) {
			res[1] = getDurationInSeconds(args[2], intervalUnit);
		}
		return res;
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

	private static int getDurationInSeconds(String duration, ChronoUnit unit) throws Exception {
		int dur;
		try {
			dur = Integer.parseInt(duration);
			if (dur <= 0) {
				throw new Exception("Duration value should be a positive number");
			}
		} catch (NumberFormatException e) {
			throw new Exception("value should be an integer number");
		}
		
		return Math.toIntExact(Duration.of(dur, unit). toSeconds());
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