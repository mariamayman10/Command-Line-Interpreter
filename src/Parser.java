import java.util.ArrayList;
import java.util.List;


public class Parser {
    private String commandName;
    private String[] args;
    public boolean parse(String input) {
        if(input.startsWith("echo")){
            commandName = "echo";
            input = input.substring(4);
            if(input.length() > 0 && input.charAt(0) != ' '){
                System.err.println("Wrong Command");
                return false;
            }
            input = input.trim();

            List<String> arguments = new ArrayList<>();
            if(input.contains(">")){
                String sAfter = input.substring(input.indexOf(">")+1), sBefore = input.substring(0, input.indexOf(">"));
                sAfter = sAfter.trim();
                sBefore = sBefore.trim();
                if(sAfter.isEmpty() || sBefore.isEmpty()){
                    System.err.println("Wrong Command");
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
                System.err.println("Wrong Command");
                return false;
            }
            input = input.trim();
            if(input.contains(">")){
                String sAfter = input.substring(input.indexOf(">")+1);
                sAfter = sAfter.trim();
                if(sAfter.isEmpty()){
                    System.err.println("Wrong Command");
                    return false;
                }
            }
            return input.length() == 0 || input.contains(">");
        }

        else if(input.startsWith("ls -r")){
            commandName = "ls -r";
            input = input.substring(5);
            if(input.length() > 0 && input.charAt(0) != ' '){
                System.err.println("Wrong Command");
                return false;
            }
            input = input.trim();

            if(input.contains(">")){
                String sAfter = input.substring(input.indexOf(">")+1);
                sAfter = sAfter.trim();
                if(sAfter.isEmpty()){
                    System.err.println("Wrong Command");
                    return false;
                }
            }
            if(input.length() > 0 && !input.contains(">")){
                System.err.println("Wrong Command");
                return false;
            }
            else return true;
        }

        else if(input.startsWith("ls")){
            commandName = "ls";
            input = input.substring(2);
            if(input.length() > 0 && input.charAt(0) != ' '){
                System.err.println("Wrong Command");
                return false;
            }
            input = input.trim();

            if(input.contains(">")){
                String sAfter = input.substring(input.indexOf(">")+1);
                sAfter = sAfter.trim();
                if(sAfter.isEmpty()){
                    System.err.println("Wrong Command");
                    return false;
                }
            }
            return input.length() == 0 || input.contains(">");
        }

        else if(input.startsWith("mkdir")){
            commandName = "mkdir";
            input = input.substring(5);
            if(input.length() > 0 && input.charAt(0) != ' '){
                System.err.println("Wrong Command");
                return false;
            }
            input = input.trim();

            if(input.split(" ").length >= 1){
                args = input.split(" ");
                return true;
            }
            else{
                System.err.println("At least one argument required");
                return false;
            }
        }

        // no argument gives directory isn't empty
        else if(input.startsWith("rmdir")){
            commandName = "rmdir";
            input = input.substring(5);
            if(input.length() > 0 && input.charAt(0) != ' '){
                System.err.println("Wrong Command");
                return false;
            }
            input = input.trim();

            if(input.split(" ").length == 1){
                args = input.split(" ");
                return true;
            }
            else{
                System.err.println("Only one argument required");
                return false;
            }
        }

        else if(input.startsWith("rm")){
            commandName = "rm";
            input = input.substring(2);
            if(input.length() > 0 && input.charAt(0) != ' '){
                System.err.println("Wrong Command");
                return false;
            }
            input = input.trim();

            if(input.split(" ").length == 1){
                args = input.split(" ");
                return true;
            }
            else{
                System.err.println("Only one argument required");
                return false;
            }
        }

        else if(input.startsWith("touch")){
            commandName = "touch";
            input = input.substring(5);
            if(input.length() > 0 && input.charAt(0) != ' '){
                System.err.println("Wrong Command");
                return false;
            }
            input = input.trim();

            if(input.split(" ").length == 1){
                args = input.split(" ");
                return true;
            }
            else{
                System.err.println("Only one argument required");
                return false;
            }
        }

        else if(input.startsWith("cp -r")){
            commandName = "cp -r";
            input = input.substring(5);
            if(input.length() > 0 && input.charAt(0) != ' '){
                System.err.println("Wrong Command");
                return false;
            }
            input = input.trim();

            if(input.split(" ").length == 2){
                args = input.split(" ");
                return true;
            }
            else{
                System.err.println("Only 2 Arguments required");
                return false;
            }
        }

        else if (input.startsWith("cat")) {
            commandName = "cat";
            input = input.substring(3);
            if(input.length() > 0 && input.charAt(0) != ' '){
                System.err.println("Wrong Command");
                return false;
            }
            input = input.trim();

            List<String> arguments = new ArrayList<>();
            if(input.contains(">")){
                String sAfter = input.substring(input.indexOf(">")+1), sBefore = input.substring(0, input.indexOf(">"));
                sAfter = sAfter.trim();
                sBefore = sBefore.trim();
                if(sAfter.isEmpty() || sBefore.isEmpty()){
                    System.err.println("Wrong Command");
                    return false;
                }
                else arguments.add(input.substring(0, input.indexOf('>')));
            }
            else arguments.add(input);

            if(input.split(" ").length == 1 || input.split(" ").length == 2){
                args = arguments.toArray(new String[0]);
                return true;
            }
            else{
                System.err.println("Takes one or two arguments only");
                return false;
            }
        }

        else if(input.startsWith("wc")){
            commandName = "wc";
            input = input.substring(2);
            if(input.length() > 0 && input.charAt(0) != ' '){
                System.err.println("Wrong Command");
                return false;
            }
            input = input.trim();

            List<String> arguments = new ArrayList<>();
            if(input.contains(">")){
                String sAfter = input.substring(input.indexOf(">")+1), sBefore = input.substring(0, input.indexOf(">"));
                sAfter = sAfter.trim();
                sBefore = sBefore.trim();
                if(sAfter.isEmpty() || sBefore.isEmpty()){
                    System.err.println("Wrong Command");
                    return false;
                }
                else arguments.add(input.substring(0, input.indexOf('>')));
            }
            else arguments.add(input);

            if(input.split(" ").length == 1){
                args = arguments.toArray(new String[0]);
                return true;
            }
            else{
                System.err.println("Only one argument required");
                return false;
            }
        }

        else if(input.startsWith("history")){
            commandName = "history";
            input = input.substring(7);
            if(input.length() > 0 && input.charAt(0) != ' '){
                System.err.println("Wrong Command");
                return false;
            }
            input = input.trim();

            if(input.contains(">")){
                String sAfter = input.substring(input.indexOf(">")+1);
                sAfter = sAfter.trim();
                if(sAfter.isEmpty()){
                    System.err.println("Wrong Command");
                    return false;
                }

                if(input.split(input.substring(0, input.indexOf('>'))).length > 0){
                    System.err.println("Take no arguments");
                    return false;
                }
                else return true;
            }
            else {
                if(!input.isEmpty()){
                    System.err.println("Take no arguments");
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
            System.err.println("Wrong Command");
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
