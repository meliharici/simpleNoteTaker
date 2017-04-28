import java.io.File;
import java.util.Scanner;

public class Command {

	protected static void executeCommands(Scanner scanner) throws Exception {
		String command = scanner.next();
		String name = scanner.nextLine();
		NoteCat.checkCommonErrors(name, command); 
		if     (command.equals(NoteCat.Commands.COMMAND_HELP.toString()))     help(name);
		else if(command.equals(NoteCat.Commands.COMMAND_EXIT.toString())) 	  exit(name);
		else if(command.equals(NoteCat.Commands.COMMAND_NEW.toString())) 	  commandNew(name, scanner);
		else if(command.equals(NoteCat.Commands.COMMAND_SHOW.toString())) 	  show(name);
		else if(command.equals(NoteCat.Commands.COMMAND_APPEND.toString()))   append(name, scanner);
		else if(command.equals(NoteCat.Commands.COMMAND_DELETE.toString()))   delete(name);
		else if(command.equals(NoteCat.Commands.COMMAND_RENAME.toString()))   rename(name, scanner);
		else if(command.equals(NoteCat.Commands.COMMAND_LIST.toString())) 	  list(name);
		else { }
	}
	private static void exit(String name) {
		if (!name.matches(".* .*"))
			System.exit(0);
	}
	
	private static void help(String name) {
		if (!name.matches(".* .*")) {
			System.out.println("noteCat 2.0.0 - simple console notetaking program");
			System.out.println("USAGE: noteCat <COMMAND> ");
			System.out.println("OPTIONS: ");
			System.out.println("new  <note>   Create new note. End your note with #END ");
			System.out.println("append [note] Append to an existing note");
			System.out.println("show <note>   Display existing note ");
			System.out.println("delete [note] Delete existing note");
			System.out.println("rename [note] Rename existing note");
			System.out.println("list   [all ] List all notes taken so far.");
			System.out.println("help          Prints this help menu ");
			System.out.println("exit          Terminates NoteCat");
		}
	}
	
	private static void commandNew(String name, Scanner scanner) throws Exception {
		if (Note.isTitleValid(name)) {
			System.out.println("Enter your note:");
			String text = scanner.nextLine();
			Note.saveValidNote(name, text);
			String newNoteName = (name + ".ncat");
			saveNoteNames(newNoteName);
		} else {
			System.out.println("Please try another title.");
		}
	}
	
	private static void show(String name) throws Exception {
		String readableNote = "";
		readableNote = Note.optimizeNote(name, readableNote);
		System.out.println(readableNote);	
	}

	private static void append(String name, Scanner scanner) throws Exception {
		if (Files.isFileExist(name)) {
			String currentNote = "";
			currentNote = Note.optimizeNote(name, currentNote);
			System.out.println("Enter your note:");
			String text = scanner.nextLine();
			currentNote += text;
			Note.saveValidNote(name, currentNote);
		}else{
			System.out.println(name + ".ncat doesn't exist.");
		}
	}

	private static void delete(String name) {
		if (Files.isFileExist(name)) {
			try {
				File file = new File(name + ".ncat");
				file.delete();
				deleteNoteName(name);
				System.out.println("Completed.");
			} catch (Exception e) {
				System.out.println(e);
			}
		}else{
			System.out.println(name + ".ncat doesn't exist to delete.");
		}
	}
	
	private static void list(String name) throws Exception {
		if(name.equals("")){
			System.out.println("Invalid menu control command");
		}else if(!Note.isSingleWord(name)){
			System.out.println("Invalid number of arguments.");
		}else if (name.equals(" all")) {
			readNoteNames();
		} else {
			if (Files.isFileExist(name)){
				System.out.println(name + ".ncat");
			}else{
				System.out.println(name + ".ncat doesn't exist.");
			}
		}
	}
	
	private static void rename(String name, Scanner scanner) throws Exception {
		if(!Files.isFileExist(name)){
			System.out.println("There is no such a file.");
		}else{
			System.out.println("Enter the new note name: ");
			String newName = scanner.nextLine();
			if(!Note.isSingleWord(newName)){
				System.out.println("Invalid note name for renaming. It contains ' '.");
				rename(name, scanner);
			}else if(Files.isFileExist(" " + newName)){
				System.out.println("File already exist.");
				rename(name, scanner);
			}else{
				String currentNote = "";
				currentNote = Note.optimizeNote(name, currentNote);
				Note.saveValidNote(" " + newName, currentNote);
				String newNoteName = (newName + ".ncat");
				saveNoteNames(newNoteName);
				delete(name);
			}
		}
	}
	
	private static void deleteNoteName(String name) throws Exception {
		String readableNote = "";
		new Files("noteNames", readableNote);
		if (Files.read("noteNames") != null) {
			readableNote = Files.read("noteNames");
			if (readableNote.contains(name + ".ncat"))
				readableNote = readableNote.replace(name + ".ncat", "");
		}
		Note.saveValidNote("noteNames", readableNote);
	}
	
	private static void readNoteNames() throws Exception {
		String readableNote = "";
		new Files("noteNames", readableNote);
		if (Files.read("noteNames") != null && Files.isFileExist("noteNames")) {
			readableNote = Files.read("noteNames");
			readableNote = readableNote.replace("null", "");
			readableNote = readableNote.replace("File not found. Wrong file name. ", "  ");
			if (!readableNote.contains(".ncat"))
				System.out.println("You don’t have any notes.");
			System.out.println(readableNote);
		} else {
			System.out.println("You have no taken notes yet.");
		}
	}

	private static void saveNoteNames(String newNoteName) throws Exception {
		String currentNote = "";
		new Files("noteNames", currentNote);
		if (Files.read("noteNames") != null)
			currentNote = Files.read("noteNames");
		currentNote += " " + newNoteName + "  ";
		Note.saveValidNote("noteNames", currentNote);
	}
}

