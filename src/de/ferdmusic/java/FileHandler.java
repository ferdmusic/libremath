package src.de.ferdmusic.java;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.zip.*;



public class FileHandler {
    String filepath;
    DocumentHandler dh = new DocumentHandler();
    Path zielordner;

    public FileHandler(String uberschrift, String text) {
        filepath = createFolder(uberschrift);
        copyTemplate();
        createContent(zielordner, uberschrift, text);
        zipFolder();
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
        String jarPath = System.getProperty("java.class.path");
        String directoryPath = new File(jarPath).getParent();

        // Construct the path to the template directory
        String templatePath = directoryPath + File.separator + "template";
        Path srcPath = Paths.get(templatePath);
        Path tgtPath = Paths.get(filepath);
        zielordner = tgtPath;

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

    public void createContent(Path target, String header, String text) {
        try {
            FileWriter writer = new FileWriter(target + "/content.xml");
            writer.write(dh.createMainContent(header, text));
            writer.close();
            System.out.println("Datei wurde erstellt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Path getZielordner() {
        return zielordner;
    }

    public void zipFolder() {
        // Pfad zum Zielordner
        Path sourceDirPath = Paths.get(filepath);

        // Pfad zur ZIP-Datei
        Path zipFilePath = Paths.get(filepath + ".zip");

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFilePath.toFile()))) {
            Files.walk(sourceDirPath).filter(path -> !Files.isDirectory(path)).forEach(path -> {
                ZipEntry zipEntry = new ZipEntry(sourceDirPath.relativize(path).toString());
                try {
                    zos.putNextEntry(zipEntry);
                    Files.copy(path, zos);
                    zos.closeEntry();
                } catch (IOException e) {
                    System.err.println(e);
                }
            });
        } catch (IOException e) {
            System.err.println("Fehler beim Erstellen der ZIP-Datei: " + e);
        }

        // Ändern Sie die Dateiendung in .odt
        File zipFile = zipFilePath.toFile();
        File odtFile = new File(zipFilePath.toString().replace(".zip", ".odt"));
        boolean renamed = zipFile.renameTo(odtFile);
        if (renamed) {
            System.out.println("Die Datei wurde erfolgreich in .odt umbenannt. Der Pfad zur Datei ist: " + odtFile.getAbsolutePath());
        } else {
            System.out.println("Fehler beim Umbenennen der Datei in .odt");
        }
        openFile(odtFile.getAbsolutePath());
    }
}
