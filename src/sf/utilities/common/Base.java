package sf.utilities.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Base {
	
	public static String findAndReplace(String pattern, String text, 
			String replacement)
	{
		Pattern pat = Pattern.compile(pattern);
		Matcher mat = pat.matcher(text);
		
		if(mat.find())
		{
			return mat.replaceAll(replacement);
		}
		
		return text;
	}
}
