import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.io.File;

public class Terminal {
    static Parser parser;
    public static String pwd(){
        return System.getProperty("user.dir");
    }
    public static StringBuilder ls_r(){
        StringBuilder output = new StringBuilder();
        String path = System.getProperty("user.dir");
        File directory = new File(path);
        if(directory.exists() && directory.isDirectory()){
            File[] files = directory.listFiles();
            if(files != null){
                List<File> filesList = Arrays.asList(files);
                Collections.reverse(filesList);
                for(File file:filesList){
                    output.append(file.getName()).append(" ");
                }
            }
        }
        return output;
    }
    public static StringBuilder mkdir(String[] args){
        StringBuilder output = new StringBuilder();
        for(String arg: args){
            if(arg.contains("\\")){
                String newArg = arg.substring(0, arg.lastIndexOf("\\"));
                Path path = Paths.get(newArg);
                if(Files.exists(path) && Files.isDirectory(path)) {
                    String directoryName = arg.substring(arg.lastIndexOf("\\") + 1);
                    File directory = new File(newArg, directoryName);
                    if (directory.mkdir()) output.append("Directory is Successfully Created" + '\n');
                    else output.append("Directory Already Exists" + '\n');
                }
                else output.append("Invalid Path, Directory isn't created" + '\n');
            }
            else{
                File directory = new File(pwd(), arg);
                if(directory.mkdir()) output.append("Directory is Successfully Created" + '\n');
                else output.append("Directory Already Exists" + '\n');
            }
        }
        return output;
    }
    public static String rmdir(String arg){
        String output = "";
        if(Objects.equals(arg, "*")){
            System.out.println("done");
        }
        else{
            Path path = Paths.get(arg);
            if(Files.exists(path) && Files.isDirectory(path)) {
                File directory = new File(String.valueOf(path));
                File[] files = directory.listFiles();
                if (files != null && files.length == 0) {
                    if(directory.delete()) output = "Directory is deleted Successfully";
                    else output = "Failed to Delete Directory";
                }
                else output = "Directory isn't empty";
            }
            else output = "Invalid Path";
        }
        return output;
    }
    public static String rm(String arg){
        String output = "";
        File file = new File(pwd(), arg);
        if(file.exists()){
            if(file.delete()) output = "File is deleted Successfully";
            else output = "Failed to delete the file";
        }
        else output = "File doesn't exist";
        return output;
    }
    public static void chooseCommandAction() throws IOException {
        while(true){
            System.out.print('>');
            String input;
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();
            input = input.replace("\\", "\\\\");
            if(input.contains(">")){
                String fileName = input.substring(input.indexOf('>')+2);
                File file = new File(pwd(), fileName);
                boolean b = file.createNewFile();
                if(!b){
                    System.out.println("Couldn't create file");
                    continue;
                }
                FileWriter writer = new FileWriter(file, false);
                if(input.contains("pwd")){
                    String output = pwd();
                    writer.write(output);
                }
                else if(input.contains("ls -r")){
                    StringBuilder output = ls_r();
                    writer.write(String.valueOf(output));
                }
                else{
                    String args = input.substring(input.indexOf(" ")+1, input.indexOf('>')-1);
                    if (input.contains("mkdir")) {
                        String[] argsList = args.split(" ");
                        writer.write(String.valueOf(mkdir(argsList)));
                    } else if (input.contains("rmdir")) {
                        writer.write(rmdir(args));
                    } else if (input.contains("rm")) {
                        writer.write(rm(args));
                    }
                }
                writer.close();
            }
            else{
                if(input.contains("pwd")){
                    System.out.println(pwd());
                }
                else if(input.contains("ls -r")){
                    StringBuilder output = ls_r();
                    System.out.println(String.valueOf(output));
                }
                else if(input.contains("mkdir")){
                    String args = input.substring(input.indexOf(" ")+1);
                    String[] argsList = args.split(" ");
                    System.out.println(mkdir(argsList));
                }
                else if(input.contains("rmdir")){
                    String arg = input.substring(input.indexOf(" ")+1);
                    System.out.println(rmdir(arg));
                }
                else if(input.contains("rm")){
                    String arg = input.substring(input.indexOf(" ")+1);
                    System.out.println(rm(arg));
                }
            }
            if(Objects.equals(input, "exit"))break;
        }
    }
    public static void main(String[] args) {
        try{
            chooseCommandAction();
        }catch (IOException ex){
            System.out.println("error");
        }

    }
}