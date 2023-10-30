// public class Parser {
//     String commandName;
//     String[] args;

//     public void split(String command){
//         String[] command_arg = command.split(" ");
//         commandName = command_arg[0];
//         System.arraycopy(command_arg, 1, args, 0, command_arg.length - 1);
//     }
//     public String getCommandName(){
//         return commandName;
//     }
//     public String[] getArgs(){
//         return args;
//     }
// }
public class Parser {
    private String commandName;
    private String[] args;

    public boolean parse(String input) {
        String[] userInput = input.trim().split(" ");
        if (userInput .length > 0) {
            commandName = userInput [0];
            if (userInput .length > 1) {
                args = new String[userInput .length - 1];
                System.arraycopy(userInput , 1, args, 0, args.length);
            } else {
                args = new String[0];
            }
            return true;
        }
        return false;
    }

    public String getCommandName() {
        return commandName;
    }

    public String[] getArgs() {
        return args;
    }
}
