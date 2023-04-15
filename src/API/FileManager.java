/**
 * @author Max Day
 * Created At: 2023/04/04
 */
package API;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    private static final String PATH = "passwords.txt";
//TODO: please fix this its so bad omg
    private File file = new File(PATH);

    public void createDirectory() throws IOException {// for anyone who wants to say that throwing it here is stupid then I say cope and seethe xDD
        if (file.createNewFile())
            System.out.println("File created");
        else
            System.out.println("File already exists.");
    }

    public void writeFile(String data) throws IOException { // for anyone who wants to say that throwing it here is stupid then I say cope and seethe xDD
        FileWriter fWriter = new FileWriter(PATH);
        fWriter.write(data);
        fWriter.close();
    }

    public String readFile() throws IOException {
        return Files.readString(Paths.get(PATH));
    }

    public String readFile(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }


    /**
     * TODO: add remove entry aswell
     */
    public void addEntry(String entry) throws IOException {
        String s = readFile();
        if (s.contains(entry.split("\\|")[0] + "|")) {
            System.err.println("The username already exists. Failed to write new user");
            return;
        }
        writeFile(s + "\n" + entry);
    }

    //////TBLManager//////


    public void updateTBLManager(String data) throws IOException {

    }


    public void loadTBLManager(String data) throws IOException {

    }
    public void createTBLManager(String owner) throws IOException {

        Path p= Files.createFile(Path.of("src/tables/" + owner + ".txt"));

        //if (file.createNewFile("src/tables/" + owner + ".txt"))
            System.out.println("File created");
        //else
          //  System.out.println("File already exists.");

    }


}



