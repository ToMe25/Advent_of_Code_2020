import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) throws IOException {
		File input = new File("input").getAbsoluteFile();
		if (!input.exists()) {
			System.out.println(input.getPath() + " does not exist!");
			System.out.println("trying " + input.getPath() + ".txt instead.");
			input = new File(input.getPath() + ".txt");
			if (!input.exists()) {
				System.err.println("neither " + input.getPath().substring(0, input.getPath().length() - 4) + ",");
				System.err.println("nor " + input.getPath() + " exists!");
				return;
			}
		}

		int lastNumber = 0;
		Map<Integer, Integer> lastNumbers = new HashMap<>();
		int nr = 0;
		for (String number : Files.readAllLines(input.toPath()).get(0).split(",")) {
			lastNumber = Integer.parseInt(number);
			nr++;
			lastNumbers.put(lastNumber, nr);
		}

		while (nr < 30000000) {
			int num = 0;
			if (lastNumbers.containsKey(lastNumber))
				num = nr - lastNumbers.get(lastNumber);

			lastNumbers.put(lastNumber, nr);
			lastNumber = num;
			nr++;

			if (nr == 2020) {
				System.out.println("The 2020th number is " + lastNumber + ".");
			}
		}


		System.out.println("The 30000000th number is " + lastNumber + ".");
	}
}
