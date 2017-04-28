import java.util.Scanner;

public class NoteCat {
	private static final String COMMAND_ICON = ">";
	
	public enum Commands{
		COMMAND_HELP("help"), COMMAND_EXIT("exit"), COMMAND_NEW("new"), COMMAND_SHOW("show"), COMMAND_APPEND("append"),
		COMMAND_DELETE("delete"), COMMAND_LIST("list"), COMMAND_RENAME("rename");
		private final String text;
		private Commands(final String text) { this.text = text;}
		public String toString() { return text; }
	}

	protected static void checkCommonErrors(String name, String command) {
		if (command.equals("nw") || command.equals("create") || command.equals("write"))
			System.out.println("Invalid note taking command.");
		else if (  !command.equals(Commands.COMMAND_HELP.toString()) && !command.equals(Commands.COMMAND_EXIT.toString())   && !command.equals(Commands.COMMAND_NEW.toString()) 
				&& !command.equals(Commands.COMMAND_SHOW.toString()) && !command.equals(Commands.COMMAND_APPEND.toString()) && !command.equals(Commands.COMMAND_DELETE.toString()) 
				&& !command.equals(Commands.COMMAND_LIST.toString()) && !command.equals(Commands.COMMAND_RENAME.toString()))
			System.out.println("Invalid menu control command.");
		else if (name.matches(".* .*") && command.equals(Commands.COMMAND_HELP.toString()) || name.matches(".* .*") && command.equals(Commands.COMMAND_EXIT.toString()))
			System.out.println("Invalid number of arguments.");
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("Welcome to NoteCat -- version 2.0.0");
		while (true) {
			Scanner sc = new Scanner(System.in);
			System.out.print(COMMAND_ICON);
			new Command();
			Command.executeCommands(sc);
		}
	}
}
