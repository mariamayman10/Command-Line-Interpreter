import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;
import java.util.*;

public class Terminal
{
    static Parser parser;
    Set<String> CommandHistory = new HashSet<>();
    public Terminal() {
        parser = new Parser();
    }
    public static void echo(String text) {
        System.out.println(text);
    }
    public static String pwd() {
        return System.getProperty("user.dir");
    }
    public static StringBuilder ls_r()
    {
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
    public static StringBuilder mkdir(String[] args)
    {//handle forward slash
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
    public static String rmdir(String arg)
    {
        StringBuilder output = new StringBuilder();
        if (Objects.equals(arg, "*")) {
            File directory = new File(pwd());
            File[] files = directory.listFiles();
            if (files != null) {
                for(File file: files){
                    if(file.isDirectory() && Objects.requireNonNull(file.list()).length == 0){
                        output.append("Deleting empty directory: ").append(file.getName()).append("...").append('\n');
                        if (file.delete()) output.append("Directory is deleted Successfully\n");
                        else output.append("Failed to Delete Directory\n");
                    }
                }
            }
        }
        else
        {
            Path path = Paths.get(arg);
            if (Files.exists(path) && Files.isDirectory(path))
            {
                File directory = new File(String.valueOf(path));
                File[] files = directory.listFiles();
                if (files != null && files.length == 0)
                {
                    output.append("Deleting empty directory: ").append(directory.getName()).append("...").append('\n');
                    if (directory.delete()) output.append("Directory is deleted Successfully");
                    else output.append("Failed to Delete Directory");
                }
                else
                    output.append("Directory isn't empty");
            }
            else
                output.append("Invalid Path");
        }
        return output.toString();
    }
    public static String rm(String arg)
    {
        String output ;
        File file = new File(pwd(), arg);
        if (file.exists())
        {
            if (file.delete()) output = "File is deleted Successfully";
            else
                output = "Failed to delete the file";
        }
        else
            output = "File doesn't exist";
        return output;
    }
    public static String touch(String arg)
    {
        String output;
        File file = new File(pwd(), arg);
        if (file.exists())
        {
            output = "File already exist";
        }
        else
        {
            try {
                if (file.createNewFile())
                {
                    output = "File created successfully";
                }
                else
                {
                    output = "Failed to create the file";
                }
            } catch (IOException e) {
                output = "An error occurred";
            }
        }
        return output;
    }
    public static void cp_r (String F1,String F2) throws IOException
    {
        File Dir1=new File(F1);
        File Dir2=new File(F2);
        if (!Dir1.exists() || !Dir1.canRead() || !Dir1.isDirectory())
            System.err.println("The first Dir name you entered either doesn't exist or not readable or isn't a Dir,please enter a correct one");
        else if (!Dir2.exists() || !Dir1.canRead() || !Dir1.isDirectory())
            System.err.println("The second Dir name you entered either doesn't exist or not readable or isn't a Dir,please enter a correct one");
        else
        {
            if (Dir1.isDirectory())  //ie: you're copying SubDir not a file
            {
                //we've firstly to know the names of the files and subdir in D1 in order to make files and subDir by their names in Dir2, so we use .list() to get the names and put them in FileNames array
                String[] FirstDirNames=Dir1.list();
                if (FirstDirNames != null)
                {
                    for (String Name:FirstDirNames)
                    {
                        File srcFile = new File(Dir1, Name);
                        String srcFileName = srcFile.getName();
                        File destFile = new File(Dir2, Name);
                        String destFileName = destFile.getName();

                        // Recursively copy subdirectories and their contents
                        cp_r(srcFileName, destFileName);
                    }
                }
            }
            else       //ie: you're copying a fi;e
            {
                Files.copy(Dir1.toPath(), Dir2.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
    public static void cat_V1 (String InputFile)         //this function takes ONE file name and print its content
    {
        //we make an object of type File named "file" & put "InputFile" in it in order to make sure of its existency later
        File file = new File(InputFile);
        //Check if the file exists and is readable
        if (file.exists() && file.isFile() && file.canRead())
        {
            try
            {
                //here, reader is an object of "BufferedReader" and it's function is to read content of file "file"
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                //we reach null if you reached the end of the file,so while the line is keeping reading "ie:not null" ,keep printing
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                reader.close();
            }
            catch (IOException e)
            {
                System.err.println("An error occurred while reading the file: " + e.getMessage());
            }
        }
        else
        {
            System.err.println("The file name you entered either doesn't exist or not readable or isn't a file,please enter a correct one");
        }
    }
    public static void cat_V2 (String File1,String File2) //this function takes TWO file names,concat them and print them
    {
        File MyFirstFile = new File(File1);
        File MySectFile = new File(File2);
        if (!MyFirstFile.exists() || !MyFirstFile.canRead() || !MyFirstFile.isFile())
            System.err.println("The first file name you entered either doesn't exist or not readable or isn't a file,please enter a correct one");
        else if (!MySectFile.exists() || !MySectFile.canRead() || !MySectFile.isFile())
            System.err.println("The second file name you entered either doesn't exist or not readable or isn't a file,please enter a correct one");
        else
        {
            try
            {
                BufferedReader Reader1 = new BufferedReader(new FileReader(File1));
                BufferedReader Reader2 = new BufferedReader(new FileReader(File2));
                String line1,line2;
                while ((line1=Reader1.readLine())!=null)
                {
                    System.out.println(line1);
                }
                Reader1.close();

                while ((line2=Reader2.readLine())!=null)
                {
                    System.out.println(line2);
                }
                Reader2.close();
            }
            catch (IOException e)
            {
                System.err.println("An error occurred while reading the file: " + e.getMessage());
            }
        }
    }
    public void chooseCommandAction() throws IOException
    {
        while (true)
        {
            System.out.print('>');
            String input;
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();
            input = input.replace("\\", "\\\\");
            if (parser.parse(input))
            {
                String commandName = parser.getCommandName();
                String[] args = parser.getArgs();

                if (input.contains(">"))
                {
                    String fileName = input.substring(input.indexOf('>')+2);
                    File file = new File(pwd(), fileName);
                    boolean b = file.createNewFile();
                    if(!b)
                    {
                        System.out.println("Couldn't create file");
                        continue;
                    }
                    FileWriter writer = new FileWriter(file, false);
                    if(input.contains("pwd"))
                    {
                        String output = pwd();
                        writer.write(output);
                    }
                    else if(input.contains("ls -r"))
                    {
                        StringBuilder output = ls_r();
                        writer.write(String.valueOf(output));
                    }
                    else
                    {
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
                }
                else
                {
                    switch (commandName)
                    {
                        case "pwd" ->{
                            System.out.println(pwd());
                            CommandHistory.add("pwd");
                        }
                        case "ls" -> {
                            StringBuilder output = ls_r();
                            System.out.println(output);
                            CommandHistory.add("ls -r");
                        }
                        case "mkdir" -> {
                            System.out.println(mkdir(args));
                            CommandHistory.add("mkdir");
                        }
                        case "touch" -> {
                            if (args.length == 1) {
                                System.out.println(touch(args[0]));
                            } else {
                                System.out.println("Error: touch takes 1 argument");
                            }
                            CommandHistory.add("touch");
                        }
                        case "rmdir" -> {
                            if (args.length == 1) {
                                if (args[0].equals("*")) {
                                    System.out.println(rmdir(args[0]));
                                } else {
                                    System.out.println(rmdir(args[0]));
                                }
                            } else {
                                System.out.println("Error: rmdir takes 1 argument");
                            }
                            CommandHistory.add("rmdir");
                        }
                        case "rm" -> {
                            System.out.println(rm(args[0]));
                            CommandHistory.add("rm");
                        }
                        case "echo" -> {
                            if (args.length == 1) {
                                echo(args[0]);
                            } else {
                                System.out.println("Error: echo take 1 argument ");
                            }
                            CommandHistory.add("echo");
                        }
                        case "cp_r"->{
                            CommandHistory.add("cp -r");
                            if (args.length==2)
                                cp_r(args[0],args[1]);
                            else
                                System.out.println("Error: cp -r takes ONLY 2 arguments ");

                        }
                        case "cat"-> {
                            CommandHistory.add("cat");
                            if (args.length==1)
                                cat_V1(args[0]);
                            else if (args.length==2)
                                cat_V2(args[0],args[1]);
                            else
                                System.out.println("Error: cat takes EITHER 1 OR 2 arguments ONLY ");
                        }
                        case "history"->{
                            CommandHistory.add("history");
                            int idx=1;
                            for (String Command : CommandHistory)
                            {
                                System.out.print(idx + ": ");
                                System.out.println(Command);
                                idx++;
                            }
                        }
                    }
                }
            }

            if (Objects.equals(input, "exit")) break;
        }
    }
}
