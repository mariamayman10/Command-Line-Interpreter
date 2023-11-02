import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.nio.file.StandardCopyOption;
import java.util.Vector;
import java.util.*;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;


public class Terminal {
    static Parser parser;
    Vector<String> CommandHistory = new Vector<>();
    public Terminal() {
        parser = new Parser();
    }
    public static String echo(String arg) {
        return arg;
    }
    public static String pwd() {
        return System.getProperty("user.dir");
    }
    public static String ls() {
        StringBuilder output = new StringBuilder();
        File directory = new File(pwd());
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    output.append(file.getName()).append(" ");
                }
            }
        }
        return String.valueOf(output);
    }
    public static String ls_r() {
        StringBuilder output = new StringBuilder();
        File directory = new File(pwd());
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                List<File> filesList = Arrays.asList(files);
                Collections.reverse(filesList);
                for (File file : filesList) {
                    output.append(file.getName()).append(" ");
                }
            }
        }
        return String.valueOf(output);
    }
    public static void mkdir(String[] args) {//handle forward slash
        for (String arg : args) {
            if (arg.contains("\\")) {
                String newArg = arg.substring(0, arg.lastIndexOf("\\"));
                Path path = Paths.get(newArg);
                if (Files.exists(path) && Files.isDirectory(path)) {
                    String directoryName = arg.substring(arg.lastIndexOf("\\") + 1);
                    File directory = new File(newArg, directoryName);
                    if (!directory.mkdir()) System.err.println("Directory Already Exists");
                } else System.err.println("Invalid Path, Directory isn't created");
            } else {
                File directory = new File(pwd(), arg);
                if (!directory.mkdir()) System.err.println("Directory Already Exists");
            }
        }
    }
    public static void rmdir(String arg) {
        if (Objects.equals(arg, "*")) {
            File directory = new File(pwd());
            File[] files = directory.listFiles();
            if (files != null) {
                for(File file: files){
                    if(file.isDirectory() && Objects.requireNonNull(file.list()).length == 0){
                        if (!file.delete()) System.out.println(("Failed to Delete Directory"));
                    }
                }
            }
        }
        else {
            Path path = Paths.get(arg);
            if (Files.exists(path) && Files.isDirectory(path)) {
                File directory = new File(String.valueOf(path));
                File[] files = directory.listFiles();
                if (files != null && files.length == 0) {
                    if (!directory.delete()) System.err.println("Failed to Delete Directory");
                }
                else
                    System.err.println("Directory isn't empty");
            }
            else
                System.err.println("Invalid Path");
        }
    }
    public static void rm(String arg) {
        File file = new File(pwd(), arg);
        if(!file.isFile() && file.isDirectory()){
            System.err.println("Directory is given instead of File");
        }
        else if (file.exists()){
            if (!file.delete()) System.err.println("Failed to delete the file");
        }
        else System.err.println("File doesn't exist");
    }
    public static void touch(String arg) {
        File file = new File(pwd(), arg);
        if(!file.isFile() && file.isDirectory()){
            System.err.println("It is an existed Directory");
        }
        else if (file.exists())
            System.err.println("File already exist");
        else {
            try {
                if (!file.createNewFile())
                    System.err.println("Failed to create the file");
            } catch (IOException e) {
                System.err.println("Error occurred: " + e.getMessage());
            }
        }
    }
    public static void cp_r(String[] args) {
        Path sourcePath = Paths.get(args[0]);
        Path destinationPath = Paths.get(args[1]);
        if (!Files.exists(sourcePath) || !Files.isReadable(sourcePath))
            System.err.println("The source path is either missing or not readable.");
        else if (!Files.exists(destinationPath) || !Files.isWritable(destinationPath))
            System.err.println("The destination path is either missing or not writable.");
        else {
            try {
                Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        Path targetPath = destinationPath.resolve(sourcePath.relativize(dir));
                        Files.createDirectories(targetPath);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Path targetPath = destinationPath.resolve(sourcePath.relativize(file));
                        Files.copy(file, targetPath, StandardCopyOption.REPLACE_EXISTING);
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                System.err.println("Failed to copy files and directories: " + e.getMessage());
            }
        }
    }
    public static String cat (String[] args){
        StringBuilder output = new StringBuilder();
        File file1 = new File(args[0]);
        if (file1.exists() && file1.isFile() && file1.canRead()) {
            try {
                BufferedReader Reader1 = new BufferedReader(new FileReader(file1));
                String line1;
                while ((line1 = Reader1.readLine()) != null) {
                    output.append(line1).append('\n');
                }
                Reader1.close();
            } catch (IOException e) {
                System.err.println("An error occurred while reading the first file: " + e.getMessage());
            }
        }
        else
            System.err.println("The file name you entered either doesn't exist or not readable or isn't a file");
        if(args.length > 1){
            File file2 = new File(args[1]);
            if (!file2.exists() || !file2.canRead() || !file2.isFile())
                System.out.println("The second file name you entered either doesn't exist or not readable or isn't a file");
            else {
                try {
                    BufferedReader Reader2 = new BufferedReader(new FileReader(args[1]));
                    String line2;
                    while ((line2=Reader2.readLine())!=null)
                        output.append(line2).append('\n');
                    Reader2.close();
                }
                catch (IOException e) {
                    System.err.println("An error occurred while reading the second file: " + e.getMessage());
                }
            }
        }
        return String.valueOf(output);
    }
    public static String wc (String arg) {
        String output = "";
        int Lines=0,Words=0,Chars=0;
        File file = new File(arg);
        if (!file.exists() || !file.isFile())
            System.err.println("The file name you entered either doesn't exist or isn't a file.");
        else {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    Lines++;
                    Chars += line.length();
                    Words += line.split(" ").length;
                }
                reader.close();
            } catch (IOException e) {
                System.err.println("An error occurred while reading the file: " + e.getMessage());
            }
            output += Lines + " " + Words + " " + Chars + " " + arg;
        }
        return output;
    }
    public void chooseCommandAction() throws IOException {
        while (true) {
            System.out.print('>');

            String input;
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();

            boolean writeToFile = false;
            String fileName = input.substring(input.indexOf('>')+1);
            fileName = fileName.trim();
            if(input.contains(">")) {
                writeToFile = true;
            }

            if (parser.parse(input)) {
                CommandHistory.add(input);
                String commandName = parser.getCommandName();
                String[] args = parser.getArgs();
                switch (commandName) {
                    case "echo" -> {
                        if(writeToFile){
                            File file = new File(pwd(), fileName);
                            try(FileWriter writer = new FileWriter(file)){
                                writer.write(echo(args[0]));
                            }catch(IOException e){
                                System.err.println("Failed to create the file for output");
                            }
                        }
                        else System.out.println(echo(args[0]));
                    }
                    case "pwd" -> {
                        if(writeToFile) {
                            File file = new File(pwd(), fileName);
                            try(FileWriter writer = new FileWriter(file)){
                                writer.write(pwd());
                            }catch(IOException e){
                                System.err.println("Failed to create the file for output");
                            }
                        }
                        else System.out.println(pwd());
                    }
                    case "ls" -> {
                        if(writeToFile){
                            File file = new File(pwd(), fileName);
                            try(FileWriter writer = new FileWriter(file)){
                                writer.write(ls());
                            }catch(IOException e){
                                System.err.println("Failed to create the file for output");
                            }
                        }
                        else System.out.println(ls());
                    }
                    case "ls -r" -> {
                        if(writeToFile){
                            File file = new File(pwd(), fileName);
                            try(FileWriter writer = new FileWriter(file)){
                                writer.write(ls_r());
                            }catch(IOException e){
                                System.err.println("Failed to create the file for output");
                            }
                        }
                        else System.out.println(ls_r());
                    }
                    case "mkdir" -> mkdir(args);
                    case "touch" -> touch(args[0]);
                    case "rmdir" -> rmdir(args[0]);
                    case "rm" -> rm(args[0]);
                    case "cp -r"-> cp_r(args);
                    case "wc"-> {
                        if(writeToFile){
                            File file = new File(pwd(), fileName);
                            try(FileWriter writer = new FileWriter(file)){
                                writer.write(wc(args[0]));
                            }catch(IOException e){
                                System.err.println("Failed to create the file for output");
                            }
                        }
                        else System.out.println(wc(args[0]));
                    }
                    case "cat"-> {
                        if(writeToFile){
                            File file = new File(pwd(), fileName);
                            try(FileWriter writer = new FileWriter(file)){
                                writer.write(cat(args));
                            }catch(IOException e){
                                System.err.println("Failed to create the file for output");
                            }
                        }
                        else System.out.println(cat(args));
                    }
                    case "history"-> {
                        CommandHistory.add("history");
                        if(writeToFile){
                            File file = new File(pwd(), fileName);
                            try(FileWriter writer = new FileWriter(file)){
                                for (String str:CommandHistory) writer.write(str + '\n');
                            }catch(IOException e){
                                System.err.println("Failed to create the file for output");
                            }
                        }
                        else
                            for (String str:CommandHistory) System.out.println(str);
                    }
                }
                if(Objects.equals(commandName, "exit"))break;
            }
        }
    }
}
