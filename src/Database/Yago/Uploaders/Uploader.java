package Database.Yago.Uploaders;

public interface Uploader {
	public static final int BATCHSIZE=500;
	
	public void upload();
}
