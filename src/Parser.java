import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private String commandName;
    private String[] args;
    public boolean parse(String input) {
        if(input.startsWith("echo")){
            commandName = "echo";
            input = input.substring(4);
            if(input.length() > 0 && input.charAt(0) != ' '){
                System.out.println("Wrong Command");
                return false;
            }
            input = input.trim();

            List<String> arguments = new ArrayList<>();
            if(input.contains(">")){
                String sAfter = input.substring(input.indexOf(">")+1);
                sAfter = sAfter.trim();
                if(sAfter.isEmpty()){
                    System.out.println("Wrong Command");
                    return false;
                }
                else arguments.add(input.substring(0, input.indexOf('>')));
            }
            else arguments.add(input);
            args = arguments.toArray(new String[0]);
            return true;
        }

        else if(input.startsWith("pwd")){
            commandName = "pwd";
            input = input.substring(3);
            if(input.length() > 0 && input.charAt(0) != ' '){
                System.out.println("Wrong Command");
                return false;
            }
            input = input.trim();
            if(input.contains(">")){
                String sAfter = input.substring(input.indexOf(">")+1);
                sAfter = sAfter.trim();
                if(sAfter.isEmpty()){
                    System.out.println("Wrong Command");
                    return false;
                }
            }
            return input.length() == 0 || input.contains(">");
        }

        else if(input.startsWith("ls -r")){
            commandName = "ls -r";
            input = input.substring(5);
            if(input.length() > 0 && input.charAt(0) != ' '){
                System.out.println("Wrong Command");
                return false;
            }
            input = input.trim();

            if(input.contains(">")){
                String sAfter = input.substring(input.indexOf(">")+1);
                sAfter = sAfter.trim();
                if(sAfter.isEmpty()){
                    System.out.println("Wrong Command");
                    return false;
                }
            }
            if(input.length() > 0 && !input.contains(">")){
                System.out.println("Wrong Command");
                return false;
            }
            else return true;
        }

        else if(input.startsWith("ls")){
            commandName = "ls";
            input = input.substring(2);
            if((input.length() > 0 && input.charAt(0) != ' ')){
                System.out.println("Wrong Command");
                return false;
            }
            input = input.trim();

            if(input.contains(">")){
                String sAfter = input.substring(input.indexOf(">")+1);
                sAfter = sAfter.trim();
                if(sAfter.isEmpty()){
                    System.out.println("Wrong Command");
                    return false;
                }
            }
            if(input.length() > 0 && !input.contains(">")){
                System.out.println("Wrong Command");
                return false;
            }
            else return true;
        }

        else if(input.startsWith("mkdir")){
            commandName = "mkdir";
            input = input.substring(5);
            if((input.length() > 0 && input.charAt(0) != ' ') || input.contains(">")){
                System.out.println("Wrong Command");
                return false;
            }
            input = input.trim();
            List<String> directoryList = new ArrayList<>();
            Pattern pattern = Pattern.compile("\"(.*?)\"|([^\\s]+)");
            Matcher matcher = pattern.matcher(input);

            while (matcher.find()) {
                String dir = matcher.group(1);
                if (dir == null) {
                    dir = matcher.group(2);
                }
                directoryList.add(dir);
            }
            args = directoryList.toArray(new String[0]);
            return true;
        }

        else if(input.startsWith("rmdir")){
            commandName = "rmdir";
            input = input.substring(5);
            if((input.length() > 0 && input.charAt(0) != ' ') || input.contains(">")){
                System.out.println("Wrong Command");
                return false;
            }
            input = input.trim();

            if(!input.isEmpty() && !input.equals(" ") && input.split(" ").length == 1){
                args = input.split(" ");
                return true;
            }
            else{
                System.out.println("Only one argument required");
                return false;
            }
        }

        else if(input.startsWith("rm")){
            commandName = "rm";
            input = input.substring(2);
            if((input.length() > 0 && input.charAt(0) != ' ') || input.contains(">")){
                System.out.println("Wrong Command");
                return false;
            }
            input = input.trim();

            if(!input.isEmpty() && !input.equals(" ") && input.split(" ").length == 1){
                args = input.split(" ");
                return true;
            }
            else{
                System.out.println("Only one argument required");
                return false;
            }
        }

        else if(input.startsWith("touch")){
            commandName = "touch";
            input = input.substring(5);
            if((input.length() > 0 && input.charAt(0) != ' ') || input.contains(">")){
                System.out.println("Wrong Command");
                return false;
            }
            input = input.trim();

            if(!input.isEmpty() && !input.equals(" ") && input.split(" ").length == 1){
                args = input.split(" ");
                return true;
            }
            else{
                System.out.println("Only one argument required");
                return false;
            }
        }

        else if(input.startsWith("cp -r")){
            commandName = "cp -r";
            input = input.substring(5);
            if((input.length() > 0 && input.charAt(0) != ' ') || input.contains(">")){
                System.out.println("Wrong Command");
                return false;
            }
            input = input.trim();

            if(input.split(" ").length == 2){
                args = input.split(" ");
                return true;
            }
            else{
                System.out.println("Only 2 Arguments required");
                return false;
            }
        }

        else if (input.startsWith("cat")) {
            commandName = "cat";
            input = input.substring(3);
            if(input.length() > 0 && input.charAt(0) != ' '){
                System.out.println("Wrong Command");
                return false;
            }
            input = input.trim();

            if(input.contains(">")){
                String sAfter = input.substring(input.indexOf(">")+1), sBefore = input.substring(0, input.indexOf(">"));
                sAfter = sAfter.trim();
                sBefore = sBefore.trim();
                if(sAfter.isEmpty() || sBefore.isEmpty()){
                    System.out.println("Wrong Command");
                    return false;
                }
                else args = input.substring(0, input.indexOf(">")).split(" ");
            }
            else args = input.split(" ");
            if((args.length == 1 || args.length == 2) && !input.isEmpty()){
                return true;
            }
            else{
                System.out.println("At least one argument required");
                return false;
            }
        }

        else if(input.startsWith("wc")){
            commandName = "wc";
            input = input.substring(2);
            if(input.length() > 0 && input.charAt(0) != ' '){
                System.out.println("Wrong Command");
                return false;
            }
            input = input.trim();

            if(input.contains(">")){
                String sAfter = input.substring(input.indexOf(">")+1), sBefore = input.substring(0, input.indexOf(">"));
                sAfter = sAfter.trim();
                sBefore = sBefore.trim();
                if(sAfter.isEmpty() || sBefore.isEmpty()){
                    System.out.println("Wrong Command");
                    return false;
                }
                else args = input.substring(0, input.indexOf(">")).split(" ");
            }
            else args = input.split(" ");
            if(args.length != 1 || input.isEmpty()){
                System.out.println("Only one argument required");
                return false;
            }
            else{
                return true;
            }
        }

        else if(input.startsWith("history")){
            commandName = "history";
            input = input.substring(7);
            if(input.length() > 0 && input.charAt(0) != ' '){
                System.out.println("Wrong Command");
                return false;
            }
            input = input.trim();

            if(input.contains(">")){
                String sAfter = input.substring(input.indexOf(">")+1);
                sAfter = sAfter.trim();
                if(sAfter.isEmpty()){
                    System.out.println("Wrong Command");
                    return false;
                }

                if(!input.substring(0, input.indexOf('>')).isEmpty()){
                    System.out.println("Take no arguments");
                    return false;
                }
                else return true;
            }
            else {
                if(!input.isEmpty()){
                    System.out.println("Take no arguments");
                    return false;
                }
                else return true;
            }
        }

        else if(input.startsWith("exit")){
            commandName = "exit";
            String[] strings = input.split(" ");
            if(strings.length > 1 || (strings.length == 1 && !strings[0].equals("exit"))){
                System.err.println("Wrong Command");
                return false;
            }
            return true;
        }

        else{
            System.out.println("Wrong Command");
            return false;
        }
    }

    public String getCommandName() {
        return commandName;
    }

    public String[] getArgs() {
        return args;
    }
}
