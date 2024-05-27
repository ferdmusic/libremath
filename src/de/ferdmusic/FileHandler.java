package src.de.ferdmusic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class FileHandler {
    String filepath;

    public FileHandler() {
    }

    public String createFolder(String header) {
        String outputDir = "output" + File.separator;
        File outputFolder = new File(outputDir);

        if (!outputFolder.exists()) {
            outputFolder.mkdir();
        }

        File file = new File(outputDir + header);
        if (file.exists()) {
            int i = 1;
            while (file.exists()) {
                file = new File(outputDir + header + i);
                i++;
            }
        }
        file.mkdir();
        filepath = file.getAbsolutePath();

        // Return the path of the created directory
        return file.getAbsolutePath();
    }

    public void openFile(String filePath) {
        try {
            if (System.getProperty("os.name").startsWith("Windows")) {
                // Windows command
                new ProcessBuilder("cmd", "/c", filePath).start();
            } else {
                // Assume Unix or Linux
                new ProcessBuilder("xdg-open", filePath).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void copyTemplate() {
        String currentDir = System.getProperty("user.dir");
        String fileSeparator = System.getProperty("file.separator");

        // Construct the relative path to the template directory
        String relativePath = "src" + fileSeparator + "de" + fileSeparator + "ferdmusic" + fileSeparator + "template";
        Path srcPath = Paths.get(currentDir, relativePath);
        Path tgtPath = Paths.get(filepath);

        try {
            Files.walk(srcPath)
                    .forEach(source -> copy(source, tgtPath.resolve(srcPath.relativize(source))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copy(Path source, Path target) {
        try {
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
