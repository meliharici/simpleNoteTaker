import java.io.File;

public class Note {
	private static final String TERMINATE_WORD = "#END";

	protected static boolean isTitleValid(String title) {
		File f = new File(title + ".ncat");
		if (f.exists()) {
			System.out.println("Sorry. file: " + title + ".ncat already exist.");
			return false;
		} else if (!isSingleWord(title)) {
			System.out.println("Invalid title.");
			return false;
		}
		return true;
	}

	protected static boolean isSingleWord(String str) {
		boolean flag = false;
		if(str.length() > 0)
		{
			if (!str.substring(1, str.length()).contains(" "))
				flag =  true;
		}
		
		return flag;
	}

	protected static void saveValidNote(String name, String text) throws Exception {
		text = validNote(text);
		new Files(name, text);
		Files.save();
	}

	private static String validNote(String str) {
		String[] words = str.split(" ");
		int lastIndex = words.length;
		for (int i = 0; i < words.length; i++) {
			if (words[i].equals(TERMINATE_WORD))
				lastIndex = i;
		}
		String validNote = "";
		for (int j = 0; j < lastIndex; j++) {
			if (j == 0)
				validNote += words[j];
			else
				validNote += " " + words[j];
		}
		return validNote;
	}

	protected static String optimizeNote(String name, String text) throws Exception {
		text = "";
		new Files(name, text);
		if (Files.read(name)!= null) {
			text = Files.read(name);
			if (text.contains("null"))
				text = text.replace("null", " ");
		} else {
			System.out.println("File not found.");
		}
		return text;
	}
}
