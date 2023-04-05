package moh.adp.test.spec;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.inject.Singleton;

@Singleton
public class TestSpecLoader {
	private final String specFileFolder = "C:/temp/testspecs";

	public TestSpecLoader() {

	}

	public void load() {
		try {
			Path path = Paths.get(specFileFolder);
			System.out.println("path " + path.getFileName() + " " + path.toFile().isDirectory());
			Files.list(path).forEach( f -> {
				System.out.println("file " + f);
			});			
		} catch (Exception e) {
			System.out.println("couldn't load files " + e.getMessage());
			e.printStackTrace();
		}
	}

}
