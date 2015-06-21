package PlanetCrash.parsing.Yago;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

public class YagoLexer implements Iterator<YagoEntry> {

	private YagoEntry next=null;
	private BufferedReader br;

	public YagoLexer(String filepath) throws FileNotFoundException {
		this.br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filepath))));
		next();
	}

	@Override
	public boolean hasNext() {
		return this.next != null;
	}

	@Override
	public YagoEntry next() {
		YagoEntry ret = null;
		String line ="";
		try {
			ret = this.next;
			line = br.readLine();
			if (line!=null) {
				String[] split = line.split("\\s+"); //split by whitespaces
				this.next = new YagoEntry(split[0],split[1],split[2],split[3]);
			} else {
				this.next = null;
			}
		} catch (IOException e) {
			System.out.println("Could not parse line: "+line);
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
