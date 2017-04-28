import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Files {
	private static final String GENERAL_ERROR = "Error, something went wrong.";
	private static String title;
	private static String content;
	
	public Files(String name, String text){
		title = name;
		content = text;
	}
	
	protected static void save() {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(title + ".ncat"), "UTF-8"));
			writer.write(content);
		} catch (IOException e) {
			System.out.println(GENERAL_ERROR);
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
				System.out.println(GENERAL_ERROR);
			}
		}
	}

	protected static String read(String title) throws Exception {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(title + ".ncat")));
			String text = null;
			StringBuilder builder = new StringBuilder();
			String separator = System.getProperty("separator");

			while ((text = reader.readLine()) != null) {
				builder.append(text);
				builder.append(separator);
			}
			return builder.toString();
		} catch (Exception e) {
			return "File not found. Wrong file name.";
		} finally {
			try {
				reader.close();
			} catch (Exception e) {

			}
		}
	}
	
	protected static boolean isFileExist(String name) {
		File f = new File(name + ".ncat");
		if (!f.exists()) 
			return false;
		return true;
	}
}

