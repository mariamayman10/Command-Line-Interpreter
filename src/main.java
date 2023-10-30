public class Main {
    public static void main(String[] args) {
        Terminal terminal = new Terminal();

        try {
            terminal.chooseCommandAction();
        } catch (IOException ex) {
            System.out.println("An error occurred.");
        }
    }
}
