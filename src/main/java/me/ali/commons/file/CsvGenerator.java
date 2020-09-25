package me.ali.commons.file;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CsvGenerator {

	public static void generateFromMap(String output, Map<String, Integer> contents) {
		try (FileWriter writer = new FileWriter(output)) {
			for (Entry<String, Integer> e : contents.entrySet()) {
				writer.append(e.getKey() + "," + e.getValue());
				writer.append('\n');
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static <T> void generateFromModel(String output, List<T> list) {
		try (FileWriter writer = new FileWriter(output)) {
			for (T e : list) {
				writer.append(e.toString());
				writer.append('\n');
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void generateFromString(String sFileName, String content) {
		try (FileWriter writer = new FileWriter(sFileName)) {
			writer.append(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
