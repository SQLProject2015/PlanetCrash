
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class parser_contries {
	public static void main(String args[]){
		parse_file();
	}
	
	public static void parse_file() {
		String yago_file_path = "D:\\db project\\yago3_tsv\\yagoTransitiveType.tsv";
		
		File yagoTansetiveTypes = new File(yago_file_path);
		if (yago_file_path == null || !yagoTansetiveTypes.exists())
			return;
			BufferedReader br = null;
			String line;
			try {
				FileReader fr = new FileReader(yago_file_path);
				br = new BufferedReader(fr);
				while((line= br.readLine())!= null)
				{
					if(line.contains("<wikicat_Countries>")){
						System.out.println(line);
					}
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			finally{
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}
}