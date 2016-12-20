package rocks.cow.Util;

import java.io.*;

public class FileManager {
    private File file;

    public FileManager() {}

    public FileManager(String filePath) {
        setFile(filePath);
    }

    public FileManager(File file) {
        setFile(file);
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setFile(String filePath) {
        setFile(new File(filePath));
    }

    public boolean fileExists() {
        return file.exists();
    }

    public boolean filePathExists() {
        File parent = new File(file.getParent());
        return parent.exists() && parent.isDirectory();
    }

    public boolean createPath() {
        File parent = new File(file.getParent());
        return parent.mkdirs();
    }

    public boolean createFile() throws IOException {
        return createPath() && file.createNewFile();
    }

    public String read() throws IOException {
        String data = "";
        String line;

        BufferedReader fr = new BufferedReader(new FileReader(file));

        while((line = fr.readLine()) != null) {
            data = data.concat(line + "\n");
        }

        fr.close();
        return data;
    }
    public void write(String data) throws IOException {
        BufferedWriter fw = new BufferedWriter(new FileWriter(file));
        fw.write(data);
        fw.close();
    }
}
