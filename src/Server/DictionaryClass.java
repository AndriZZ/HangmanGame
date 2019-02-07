package server;

public class DictionaryClass {

	private String categories;

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}
	public String dashString(String word) {
		int wordLength = word.length();
		return new String(new char[wordLength]).replace('\0', '_');
	}


}
