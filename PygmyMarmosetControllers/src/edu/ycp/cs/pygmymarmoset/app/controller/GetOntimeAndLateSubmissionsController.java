// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;

import edu.ycp.cs.pygmymarmoset.app.model.ISubmissionCollector;
import edu.ycp.cs.pygmymarmoset.app.model.PersistenceException;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.Submission;
import edu.ycp.cs.pygmymarmoset.app.model.SubmissionStatus;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseProvider;
import edu.ycp.cs.pygmymarmoset.model.persist.IDatabase;

public class GetOntimeAndLateSubmissionsController {
	private static class SubmissionArchiveCollector implements ISubmissionCollector {
		private ZipOutputStream zout;

		public SubmissionArchiveCollector(ZipOutputStream zout) {
			this.zout = zout;
		}

		@Override
		public void collect(User user, Submission submission, SubmissionStatus status, InputStream data) {
			try {
				doCollect(user, submission, status, data);
			} catch (IOException e) {
				throw new PersistenceException("Error sending project zipfile", e);
			}
		}
		
		public void doCollect(User user, Submission submission, SubmissionStatus status, InputStream data) throws IOException {
			// The naming conventions match Marmoset's naming conventions
			StringBuilder buf = new StringBuilder();
			buf.append(user.getUsername());
			if (status == SubmissionStatus.LATE) {
				buf.append("-late");
			}
			buf.append("__");
			buf.append(submission.getSubmissionNumber());
			String baseDir = buf.toString();

			if (submission.isZip()) {
				// Copy zip contents to output archive
				ZipInputStream zin = new ZipInputStream(data);
				for (;;) {
					ZipEntry entry = zin.getNextEntry();
					if (entry == null) {
						break;
					}
					copyEntry(baseDir, entry.getName(), zin);
				}
			} else {
				// Copy file contents to output archive
				copyEntry(baseDir, submission.getFileName(), data);
			}
		}
		
		public void copyEntry(String baseDir, String name, InputStream data) throws IOException {
			name = ZipUtil.sanitizeName(name);
			String fullName = baseDir + "/" + name;
			//System.out.println("Copying entry " + name + " as " + fullName);
			ZipEntry entry = new ZipEntry(fullName);
			zout.putNextEntry(entry);
			IOUtils.copy(data, zout);
		}
	}

	public void execute(Project project, ZipOutputStream zout) {
		ISubmissionCollector collector = new SubmissionArchiveCollector(zout);
		IDatabase db = DatabaseProvider.getInstance();
		db.getOntimeAndLateSubmissions(project, collector);
	}
}
