package edu.ycp.cs.pygmymarmoset.app.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import edu.ycp.cs.pygmymarmoset.app.model.IReadBlob;
import edu.ycp.cs.pygmymarmoset.app.model.PersistenceException;
import edu.ycp.cs.pygmymarmoset.app.model.Submission;
import edu.ycp.cs.pygmymarmoset.app.model.SubmissionEntry;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseProvider;
import edu.ycp.cs.pygmymarmoset.model.persist.IDatabase;

public class GetSubmissionIndexController {
	private static class IndexReader implements IReadBlob {
		final int submissionId;
		final List<SubmissionEntry> entries = new ArrayList<>();
		
		public IndexReader(int submissionId) {
			this.submissionId = submissionId;
		}
		
		@Override
		public void readBlob(InputStream blobIn, String name) {
			try {
				ZipInputStream zin = new ZipInputStream(blobIn);
				int index = 1;
				for (;;) {
					ZipEntry entry = zin.getNextEntry();
					if (entry == null) {
						break;
					}
					SubmissionEntry e = new SubmissionEntry();
					e.setIndex(index++);
					e.setName(entry.getName());
					e.setSize(entry.getSize());
					entries.add(e);
				}
			} catch (IOException e) {
				// This should never happen: a blobs isn't marked as a zipfile
				// unless it can be read as a zipfile in its entirety during upload.
				throw new PersistenceException("Error reading zipfile data for submission " + submissionId, e);
			}
		}
	}
	
	public List<SubmissionEntry> execute(Submission submission) {
		// Special case: if the submission isn't a zipfile, then
		// there's just a single "entry"
		if (!submission.isZip()) {
			SubmissionEntry entry = new SubmissionEntry();
			entry.setIndex(0);
			entry.setName(submission.getFileName());
			entry.setSize(submission.getSize());
			return Arrays.asList(entry);
		}
		
		// The submission is definitely a zipfile.
		// Read its entries.
		IDatabase db = DatabaseProvider.getInstance();
		IndexReader reader = new IndexReader(submission.getId());
		db.readSubmissionBlob(submission, reader);
		return reader.entries;
	}
}
