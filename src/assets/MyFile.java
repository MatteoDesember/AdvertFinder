package assets;

import java.io.*;

public class MyFile {

    private String name;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private FileWriter fileWriter;
    private FileReader fileReader;
    private File file;

    public MyFile(String name) throws IOException {
        this.name = name;
        this.file = new File(this.name);
        this.fileWriter = new FileWriter(this.file);
        this.fileReader = new FileReader(this.file);
        this.bufferedWriter = new BufferedWriter(this.fileWriter);
        this.bufferedReader = new BufferedReader(this.fileReader);
    }

    public static void write(String fileName, String text) {
        File f = new File(fileName);
        if (!f.exists())
            f.getParentFile().mkdirs();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(f))) {
            bufferedWriter.write(text);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readLines() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

    public String readLine() throws IOException {
        return bufferedReader.readLine();
    }

}