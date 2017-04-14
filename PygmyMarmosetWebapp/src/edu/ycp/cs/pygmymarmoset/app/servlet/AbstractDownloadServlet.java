// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import edu.ycp.cs.pygmymarmoset.app.controller.GetSubmissionDataController;
import edu.ycp.cs.pygmymarmoset.app.controller.ZipUtil;
import edu.ycp.cs.pygmymarmoset.app.model.IReadBlob;
import edu.ycp.cs.pygmymarmoset.app.model.PersistenceException;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.Submission;
import edu.ycp.cs.pygmymarmoset.app.model.User;

public abstract class AbstractDownloadServlet extends AbstractServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User student = (User) req.getAttribute("student");
		Project project = (Project) req.getAttribute("project");
		Submission submission = (Submission) req.getAttribute("submission");
		
		resp.setContentType("application/zip");
		String fileName = student.getUsername() + "-" + project.getName() + "-" + submission.getId() + ".zip";
		resp.addHeader("Content-Disposition", "attachment; filename=" + fileName);
		
		OutputStream out;
		
		if (submission.isZip()) {
			out = resp.getOutputStream();
		} else {
			// Wrap contents as a zip file entry
			ZipOutputStream zout = new ZipOutputStream(resp.getOutputStream());
			String entryName = ZipUtil.sanitizeName(submission.getFileName());
			zout.putNextEntry(new ZipEntry(entryName));
			out = zout;
		}
		
		GetSubmissionDataController getSubmissionData = new GetSubmissionDataController();

		getSubmissionData.execute(submission, new IReadBlob() {
			@Override
			public void readBlob(InputStream blobIn, String name) {
				try {
					IOUtils.copy(blobIn, out);
				} catch (IOException e) {
					throw new PersistenceException("Error sending submission data", e);
				}
			}
		});
		
		if (!submission.isZip()) {
			((ZipOutputStream)out).finish();
		}
	}
}
