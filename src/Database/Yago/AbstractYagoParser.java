package Database.Yago;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import Game.GameUtils;
import config.Config;


public abstract class AbstractYagoParser implements Runnable {
	
	String filepath; //path of the yago TSV file
	String litepath; //filepath of the lite file
	Config properties;
	public static final int VARCHAR_LIMIT = 45;
	/*
	 * Gets a TSV filepath and parses all its rows to populate the DB
	 */
	public AbstractYagoParser(String filepath) {
		this.filepath = filepath;
		this.litepath = filepath.substring(0, filepath.lastIndexOf("."))+".lite";
		this.properties = GameUtils.getConfig();
	}
	
	public void populate() throws FileNotFoundException {
		File litefile = new File(litepath);
		boolean litemode = litefile.exists(), okayflag=true; //TODO: add config flag to make sure litefile is complete
		BufferedWriter bw = null;
		if(!litemode) {
			try {
				litefile.createNewFile();
			bw = new BufferedWriter(new FileWriter(litefile));
			} catch (IOException e) {
				System.out.println("Error initializing lite file!");
				okayflag=false;
			}
		}
		YagoLexer lexer = new YagoLexer(litemode?litepath:filepath);
		
		while(lexer.hasNext()) {
			YagoEntry next = lexer.next();
			if(next.lentity.length()>VARCHAR_LIMIT || next.rentity.length()>VARCHAR_LIMIT)
				continue;
			if(parse(next)&&okayflag&&!litemode&&bw!=null) {
				try {
					bw.write(next.getId()+"\t"+next.getLeftEntity()+"\t"+next.getRelation()+"\t"+next.getRightEntity()+"\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
				
		if(bw!=null)
			try {
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public void run(){
		try {
			populate();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String entity_cleaner(String entity){
		return entity.replaceAll("<", "").replaceAll(">", "").replaceAll("_", " ");
	}
	/*
	 * This function gets a yago entry and uses it to populate the database
	 */
	abstract public boolean parse(YagoEntry toParse);

}
