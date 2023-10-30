// import java.io.*;
// import java.nio.file.*;
// import java.util.*;
// import java.io.File;

// public class Terminal {
//     static Parser parser;
//     public static String pwd(){
//         return System.getProperty("user.dir");
//     }
//     public static StringBuilder ls_r(){
//         StringBuilder output = new StringBuilder();
//         String path = System.getProperty("user.dir");
//         File directory = new File(path);
//         if(directory.exists() && directory.isDirectory()){
//             File[] files = directory.listFiles();
//             if(files != null){
//                 List<File> filesList = Arrays.asList(files);
//                 Collections.reverse(filesList);
//                 for(File file:filesList){
//                     output.append(file.getName()).append(" ");
//                 }
//             }
//         }
//         return output;
//     }
//     public static StringBuilder mkdir(String[] args){
//         StringBuilder output = new StringBuilder();
//         for(String arg: args){
//             if(arg.contains("\\")){
//                 String newArg = arg.substring(0, arg.lastIndexOf("\\"));
//                 Path path = Paths.get(newArg);
//                 if(Files.exists(path) && Files.isDirectory(path)) {
//                     String directoryName = arg.substring(arg.lastIndexOf("\\") + 1);
//                     File directory = new File(newArg, directoryName);
//                     if (directory.mkdir()) output.append("Directory is Successfully Created" + '\n');
//                     else output.append("Directory Already Exists" + '\n');
//                 }
//                 else output.append("Invalid Path, Directory isn't created" + '\n');
//             }
//             else{
//                 File directory = new File(pwd(), arg);
//                 if(directory.mkdir()) output.append("Directory is Successfully Created" + '\n');
//                 else output.append("Directory Already Exists" + '\n');
//             }
//         }
//         return output;
//     }
//     public static String rmdir(String arg){
//         String output = "";
//         if(Objects.equals(arg, "*")){
//             System.out.println("done");
//         }
//         else{
//             Path path = Paths.get(arg);
//             if(Files.exists(path) && Files.isDirectory(path)) {
//                 File directory = new File(String.valueOf(path));
//                 File[] files = directory.listFiles();
//                 if (files != null && files.length == 0) {
//                     if(directory.delete()) output = "Directory is deleted Successfully";
//                     else output = "Failed to Delete Directory";
//                 }
//                 else output = "Directory isn't empty";
//             }
//             else output = "Invalid Path";
//         }
//         return output;
//     }
//     public static String rm(String arg){
//         String output = "";
//         File file = new File(pwd(), arg);
//         if(file.exists()){
//             if(file.delete()) output = "File is deleted Successfully";
//             else output = "Failed to delete the file";
//         }
//         else output = "File doesn't exist";
//         return output;
//     }
//     public static void chooseCommandAction() throws IOException {
//         while(true){
//             System.out.print('>');
//             String input;
//             Scanner scanner = new Scanner(System.in);
//             input = scanner.nextLine();
//             input = input.replace("\\", "\\\\");
//             if(input.contains(">")){
//                 String fileName = input.substring(input.indexOf('>')+2);
//                 File file = new File(pwd(), fileName);
//                 boolean b = file.createNewFile();
//                 if(!b){
//                     System.out.println("Couldn't create file");
//                     continue;
//                 }
//                 FileWriter writer = new FileWriter(file, false);
//                 if(input.contains("pwd")){
//                     String output = pwd();
//                     writer.write(output);
//                 }
//                 else if(input.contains("ls -r")){
//                     StringBuilder output = ls_r();
//                     writer.write(String.valueOf(output));
//                 }
//                 else{
//                     String args = input.substring(input.indexOf(" ")+1, input.indexOf('>')-1);
//                     if (input.contains("mkdir")) {
//                         String[] argsList = args.split(" ");
//                         writer.write(String.valueOf(mkdir(argsList)));
//                     } else if (input.contains("rmdir")) {
//                         writer.write(rmdir(args));
//                     } else if (input.contains("rm")) {
//                         writer.write(rm(args));
//                     }
//                 }
//                 writer.close();
//             }
//             else{
//                 if(input.contains("pwd")){
//                     System.out.println(pwd());
//                 }
//                 else if(input.contains("ls -r")){
//                     StringBuilder output = ls_r();
//                     System.out.println(String.valueOf(output));
//                 }
//                 else if(input.contains("mkdir")){
//                     String args = input.substring(input.indexOf(" ")+1);
//                     String[] argsList = args.split(" ");
//                     System.out.println(mkdir(argsList));
//                 }
//                 else if(input.contains("rmdir")){
//                     String arg = input.substring(input.indexOf(" ")+1);
//                     System.out.println(rmdir(arg));
//                 }
//                 else if(input.contains("rm")){
//                     String arg = input.substring(input.indexOf(" ")+1);
//                     System.out.println(rm(arg));
//                 }
//             }
//             if(Objects.equals(input, "exit"))break;
//         }
//     }
//     public static void main(String[] args) {
//         try{
//             chooseCommandAction();
//         }catch (IOException ex){
//             System.out.println("error");
//         }

//     }
// }----------------------------------------------------------------------------------
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Terminal {
    static Parser parser;
    public Terminal() {
        parser = new Parser();
    }
    private static void removeEmptyDirectories(File directory) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    removeEmptyDirectories(file);

                    String[] fileList = file.list();
                    if (fileList != null && fileList.length == 0) {
                        System.out.println("Deleting empty directory: " + file.getAbsolutePath());

                    }
                }
            }
        }
    }
    public static String touch(String arg) {
        String output;
        File file = new File(pwd(), arg);
        if (file.exists()) {
            output = "File already exist";
        }else {
            try {
                if (file.createNewFile()) {
                    output = "File created successfully";
                } else {
                    output = "Failed to create the file";
                }
            } catch (IOException e) {
                output = "An error occurred";
            }
        }
        return output;
    }



    public static String pwd() {
        return System.getProperty("user.dir");
    }

    public static StringBuilder ls_r() {
        StringBuilder output = new StringBuilder();
        String path = System.getProperty("user.dir");
        File directory = new File(path);
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
        return output;
    }

    public static StringBuilder mkdir(String[] args) {
        StringBuilder output = new StringBuilder();
        for (String arg : args) {
            if (arg.contains("\\")) {
                String newArg = arg.substring(0, arg.lastIndexOf("\\"));
                Path path = Paths.get(newArg);
                if (Files.exists(path) && Files.isDirectory(path)) {
                    String directoryName = arg.substring(arg.lastIndexOf("\\") + 1);
                    File directory = new File(newArg, directoryName);
                    if (directory.mkdir()) output.append("Directory is Successfully Created" + '\n');
                    else output.append("Directory Already Exists" + '\n');
                } else output.append("Invalid Path, Directory isn't created" + '\n');
            } else {
                File directory = new File(pwd(), arg);
                if (directory.mkdir()) output.append("Directory is Successfully Created" + '\n');
                else output.append("Directory Already Exists" + '\n');
            }
        }
        return output;
    }

    public static String rmdir(String arg) {
        String output ;
        if (Objects.equals(arg, "*")) {
            File currentDirectory = new File(pwd());
            removeEmptyDirectories(currentDirectory);
            output = "Empty directories removed.";

        } else {
            Path path = Paths.get(arg);
            if (Files.exists(path) && Files.isDirectory(path)) {
                File directory = new File(String.valueOf(path));
                File[] files = directory.listFiles();
                if (files != null && files.length == 0) {
                    if (directory.delete()) output = "Directory is deleted Successfully";
                    else output = "Failed to Delete Directory";
                } else output = "Directory isn't empty";
            } else output = "Invalid Path";
        }
        return output;
    }

    public static String rm(String arg) {
        String output ;
        File file = new File(pwd(), arg);
        if (file.exists()) {
            if (file.delete()) output = "File is deleted Successfully";
            else output = "Failed to delete the file";
        } else output = "File doesn't exist";
        return output;
    }
    public static void echo(String text) {
        System.out.println(text);
    }


    public void chooseCommandAction() throws IOException {
        while (true) {
            System.out.print('>');
            String input;
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();
            input = input.replace("\\", "\\\\");
            if (parser.parse(input)) {
                String commandName = parser.getCommandName();
                String[] args = parser.getArgs();

                if (input.contains(">")) {
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
                        String arg = input.substring(input.indexOf(" ")+1, input.indexOf('>')-1);
                        if (input.contains("mkdir")) {
                            String[] argsList = arg.split(" ");
                            writer.write(String.valueOf(mkdir(argsList)));
                        } else if (input.contains("rmdir")) {
                            writer.write(rmdir(arg));
                        } else if (input.contains("rm")) {
                            writer.write(rm(arg));
                        }
                    }
                    writer.close();
                } else {
                    if (commandName.equals("pwd")) {
                        System.out.println(pwd());
                    } else if (commandName.equals("ls")) {
                        StringBuilder output = ls_r();
                        System.out.println(output.toString());
                    } else if (commandName.equals("mkdir")) {
                        System.out.println(mkdir(args));
                    } else if(commandName.equals("touch")) {
                        if (args.length == 1) {
                            System.out.println(touch(args[0]));
                        } else {
                            System.out.println("Error: touch takes 1 argument");
                        }
                    }
                    else if (commandName.equals("rmdir")) {
                        if (args.length == 1) {
                            if (args[0].equals("*")) {
                                System.out.println(rmdir(args[0]));
                            } else {
                                System.out.println(rmdir(args[0]));
                            }
                        } else {
                            System.out.println("Error: rmdir takes 1 argument");
                        }
                    } else if (commandName.equals("rm")) {
                        System.out.println(rm(args[0]));
                    } else if (commandName.equals("echo")) {
                        if (args.length == 1) {
                            echo(args[0]);
                        } else {
                            System.out.println("Error: echo take 1 argument ");
                        }
                    }
                }
            }

            if (Objects.equals(input, "exit")) break;
        }
    }

}
