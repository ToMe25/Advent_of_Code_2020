import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
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

		int i = 0;
		List<Long> last = new ArrayList<>();
		long invalid = 0;
		for (String line : Files.readAllLines(input.toPath())) {
			long number = Long.parseLong(line);
			if (i > 25) {
				boolean found = false;
				for (int n = 1; n < 26; n++) {
					if (last.get(i - n) != number / 2) {
						for (int j = 1; j < 26; j++) {
							if (last.get(i - j) == number - last.get(i - n)) {
								found = true;
								break;
							}
						}
					}
				}
				if (!found) {
					System.out.println("Couldn't find number pair for " + number + ".");
					invalid = number;
				}
			}
			last.add(number);
			i++;
		}

		for (int nr = 0; nr < last.size(); nr++) {
			long min = last.get(nr);
			long max = last.get(nr);
			long sum = 0;
			i = 0;
			while (sum < invalid) {
				if (nr + i >= last.size() || last.get(nr + i) == invalid) {
					break;
				}
				if (last.get(nr + i) < min) {
					min = last.get(nr + i);
				} else if (last.get(nr + i) > max) {
					max = last.get(nr + i);
				}
				sum += last.get(nr + i);
				i++;
			}

			if (sum == invalid) {
				System.out.printf("Found contiguous set adding up to invalid: [min: %d, max: %d, weakness: %d].%n", min,
						max, min + max);
				break;
			}
		}
	}
}
