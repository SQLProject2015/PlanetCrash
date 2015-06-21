package PlanetCrash.parsing.Yago;

import java.io.BufferedReader;
<<<<<<< HEAD
=======
import java.io.File;
>>>>>>> parent of 7bcdd4d... lexer
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

public class YagoLexer implements Iterator<YagoEntry> {

	private YagoEntry next=null;
	private BufferedReader br;
	private TsvParser parser;

	public YagoLexer(String filepath) throws FileNotFoundException {
<<<<<<< HEAD
		//		try {
		//			this.br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath),"UTF-8"));
		//		} catch (UnsupportedEncodingException e) {
		//			System.out.println(e.getMessage());
		//			return;
		//		}
		TsvParserSettings settings = new TsvParserSettings();
		settings.getFormat().setLineSeparator(System.lineSeparator());
		parser = new TsvParser(settings);
		parser.beginParsing(new FileReader(filepath));
=======
		this.br = new BufferedReader(new FileReader(new File(filepath)));
>>>>>>> parent of 7bcdd4d... lexer
		next();
	}

	@Override
	public boolean hasNext() {
		return this.next != null;
	}

	@Override
	public YagoEntry next() {
		YagoEntry ret = null;
		String[] line = {};
		ret = this.next;
		line = parser.parseNext();
		if (line!=null) {
			//String[] split = line.trim().split("\\s+"); //split by whitespaces
			this.next = new YagoEntry(line[0],line[1],line[2],line[3]);
		} else {
			this.next = null;
		}
		return ret;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	public void close() {
		if(br!=null)
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
