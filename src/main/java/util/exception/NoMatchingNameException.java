package util.exception;

public class NoMatchingNameException extends Exception{
	public NoMatchingNameException(String type, String nameStr)
	{
		super("No matching "+type+" name was found : "+ nameStr);
		
	}
	
	

}
