// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.GetSelectedSubmissionsController;
import edu.ycp.cs.pygmymarmoset.app.model.GetSubmissionsMode;
import edu.ycp.cs.pygmymarmoset.app.model.Project;

/**
 * Common implementation superclass for servlets that do
 * bulk submissions download for a {@link Project}.
 */
public abstract class AbstractDownloadSubmissionsServlet extends AbstractServlet {
	private static final long serialVersionUID = 1L;

	private GetSubmissionsMode mode;
	private String fnamePrefix;
	private String fnameSuffix;
	
	/**
	 * Constructor.
	 * 
	 * @param mode         the {@link GetSubmissionsMode} specifying which submissions to download
	 * @param fnamePrefix  filename prefix
	 * @param fnameSuffix  filename suffix (appended just before the ".zip" file extension)
	 */
	public AbstractDownloadSubmissionsServlet(GetSubmissionsMode mode, String fnamePrefix, String fnameSuffix) {
		this.mode = mode;
		this.fnamePrefix = fnamePrefix;
		this.fnameSuffix = fnameSuffix;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Project project = (Project) req.getAttribute("project");
		
		GetSelectedSubmissionsController getSubs = new GetSelectedSubmissionsController();
		
		OutputStream out = resp.getOutputStream();
		ZipOutputStream zout = new ZipOutputStream(out);
		
		resp.setContentType("application/zip");
		String fileName = fnamePrefix + project.getName() + fnameSuffix + ".zip";
		resp.addHeader("Content-Disposition", "attachment; filename=" + fileName);
		
		getSubs.execute(project, zout, mode);
		zout.finish();
	}
}
