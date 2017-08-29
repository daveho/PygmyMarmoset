package edu.ycp.cs.pygmymarmoset.app.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import edu.ycp.cs.pygmymarmoset.app.model.Course;

public class BulkRegistrationController {
	public void execute(Course course, InputStream in) throws IOException {
		InputStreamReader reader = new InputStreamReader(in, Charset.forName("UTF-8"));
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
		int count = 0;
		for (CSVRecord record : records) {
			count++;
		}
		System.out.println("Read " + count + " record(s)");
	}
}
