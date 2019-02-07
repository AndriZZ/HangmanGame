package junit;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import server.DictionaryDealer;

public class DictionaryDealerTest {
	DictionaryDealer dictionaryDealer = new DictionaryDealer();

	@Test
	public void testDictionaryDealerDasher() throws IOException {
		String k="";
		String a = dictionaryDealer.dashString("dasdasdasdasdasadsdas");
		for (int i = 0; i < a.length(); i++) {
			k=k+"_";
		}
		assertEquals(a, k);

	}
	@Test
	public void testDictionaryDealerTakeCategory() throws IOException {
		HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();
		List<String> list = Arrays.asList("A", "B", "C");
		hashMap.put("jokers", list);
		hashMap.put("monkeys",list);
		hashMap.put("joggers",list);
		
		String a=dictionaryDealer.takeCategories(hashMap);
		
		String k = "1jokers\n"+"2monkeys\n"+"3joggers\n";
		assertEquals(a,k);
	}
}
