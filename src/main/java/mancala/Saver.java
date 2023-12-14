package mancala;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Saver {
    public static void saveObject(Serializable toSave, String filename) throws IOException {
        Path currentDirectory = Paths.get(System.getProperty("user.dir"));

        String folderName = "assets";
        Path assetsFolderPath = currentDirectory.resolve(folderName);

        if (Files.notExists(assetsFolderPath)) {
            Files.createDirectory(assetsFolderPath);
        }

        Path gameFilePath = assetsFolderPath.resolve(filename);

        try (OutputStream os = Files.newOutputStream(gameFilePath);
             ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(toSave);
        }

    }

    public static Serializable loadObject(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (Serializable) ois.readObject();
        }
    }
}
