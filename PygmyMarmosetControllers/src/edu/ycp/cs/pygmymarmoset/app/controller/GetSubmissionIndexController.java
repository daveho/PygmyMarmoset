package edu.ycp.cs.pygmymarmoset.app.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ycp.cs.pygmymarmoset.app.model.IndexEntry;
import edu.ycp.cs.pygmymarmoset.app.model.Submission;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseProvider;
import edu.ycp.cs.pygmymarmoset.model.persist.IDatabase;
import edu.ycp.cs.pygmymarmoset.model.persist.IReadBlob;

public class GetSubmissionIndexController {
	private static Logger logger = LoggerFactory.getLogger(GetSubmissionIndexController.class);
	
	private static class IndexReader implements IReadBlob {
		final int submissionId;
		final List<IndexEntry> entries = new ArrayList<>();
		
		public IndexReader(int submissionId) {
			this.submissionId = submissionId;
		}
		
		@Override
		public void readBlob(InputStream blobIn) {
			try {
				ZipInputStream zin = new ZipInputStream(blobIn);
				int index = 0;
				for (;;) {
					ZipEntry entry = zin.getNextEntry();
					if (entry == null) {
						break;
					}
					IndexEntry e = new IndexEntry();
					e.setIndex(index++);
					e.setName(entry.getName());
					e.setSize(entry.getSize());
				}
			} catch (IOException e) {
				// This is probably not a zip file
				logger.info("Submission {} doesn't appear to be a zip file", submissionId);
			}
		}
	}
	
	public List<IndexEntry> execute(Submission submission) {
		IDatabase db = DatabaseProvider.getInstance();
		IndexReader reader = new IndexReader(submission.getId());
		db.readSubmissionBlob(submission, reader);
		return reader.entries;
	}
}
