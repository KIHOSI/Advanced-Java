import java.io.Serializable;

public class GameSerializable implements Serializable{
	
	private String gameName = "";
	private String gamePath = "";
	
	public GameSerializable() {
		this("", "");
	}
	public GameSerializable(String gameName, String gamePath){
		this.gameName = gameName;
		this.gamePath = gamePath;
	}
	
	public void setGameName(String new_gamename){
		this.gameName = new_gamename;
	}
	public void setGamePath(String new_gamepath){
		this.gamePath = new_gamepath;
	}
	
	public String getGameName(){
		return this.gameName;
	}
	public String getGamePath(){
		return this.gamePath;
	}
	
}