package edu.ycp.cs.pygmymarmoset.app.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ycp.cs.pygmymarmoset.app.model.IReadBlob;
import edu.ycp.cs.pygmymarmoset.app.model.Submission;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseProvider;
import edu.ycp.cs.pygmymarmoset.model.persist.IDatabase;

public class GetSubmissionEntryController {
	private final class ReadEntry implements IReadBlob {
		private Submission submission;
		private int entryIndex;
		private IReadBlob entryReader;
		
		public ReadEntry(Submission submission, int entryIndex, IReadBlob entryReader) {
			this.submission = submission;
			this.entryIndex = entryIndex;
			this.entryReader = entryReader;
		}
		
		@Override
		public void readBlob(InputStream blobIn, String name) {
			try {
				int index = 1;
				ZipInputStream zin = new ZipInputStream(blobIn);
				String entryName = null;
				for (;;) {
					ZipEntry entry = zin.getNextEntry();
					// Did we find another entry
					if (entry == null) {
						break;
					}
					// Did we find the entry we're looking for?
					if (index == entryIndex) {
						entryName = entry.getName();
						break;
					}
					// Try next entry
					index++;
				}
				if (entryName != null) {
					// Let the entry reader read the entry data
					entryReader.readBlob(zin, entryName);
				} else {
					logger.error("Entry {} in submission {} not found", entryIndex, submission.getId());
				}
			} catch (IOException e) {
				logger.error("Error reading entry {} in submission {}", entryIndex, submission.getId());
			}
		}
	}

	private static final Logger logger = LoggerFactory.getLogger(GetSubmissionEntryController.class);
	
	public boolean execute(Submission submission, int entryIndex, IReadBlob entryReader) {
		if (entryIndex > 0) {
			// Reading one entry rather than the entire blob
			entryReader = new ReadEntry(submission, entryIndex, entryReader);
		}
		IDatabase db = DatabaseProvider.getInstance();
		db.readSubmissionBlob(submission, entryReader);
		return true;
	}
}
