public class Parser {
    String commandName;
    String[] args;

    public void split(String command){
        String[] command_arg = command.split(" ");
        commandName = command_arg[0];
        System.arraycopy(command_arg, 1, args, 0, command_arg.length - 1);
    }
    public String getCommandName(){
        return commandName;
    }
    public String[] getArgs(){
        return args;
    }
}
