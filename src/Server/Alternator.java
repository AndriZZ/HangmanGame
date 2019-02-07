package server;


public class Alternator {
	public String makeStringSpreadbySpaces(String string) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < string.length(); i++) {

			result.append(string.charAt(i));
			if (i != string.length() - 1) {
				result.append(" ");
			}
		}
		return result.toString();
	}
}
