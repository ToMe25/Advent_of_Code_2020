import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

		List<Integer> adapters = new ArrayList<>();
		for (String line : Files.readAllLines(input.toPath())) {
			adapters.add(Integer.parseInt(line));
		}

		Collections.sort(adapters);

		int one = 0;
		int three = 1;
		long possibleSolutions = 1;
		List<Long> futureSolutions = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			futureSolutions.add((long) 0);
		}
		int joltage = 0;
		for (int adapter : adapters) {
			int lowest = adapter - joltage;
			if (adapters.contains(joltage + 2) && lowest < 2) {
				futureSolutions.set(1, futureSolutions.get(1) + possibleSolutions);
			}
			if (adapters.contains(joltage + 3) && lowest < 3) {
				futureSolutions.set(2, futureSolutions.get(2) + possibleSolutions);
			}
			possibleSolutions += futureSolutions.get(0);
			futureSolutions.remove(0);
			futureSolutions.add((long) 0);
			if (adapter - joltage == 1) {
				one++;
			} else if (adapter - joltage == 3) {
				three++;
			}
			joltage = adapter;
		}

		System.out.printf(
				"Found %d one jolt differences and %d three jolt differences, getting a step one result of %d.%n", one,
				three, one * three);

		System.out.println("Found " + possibleSolutions + " valid solutions.");
	}
}
