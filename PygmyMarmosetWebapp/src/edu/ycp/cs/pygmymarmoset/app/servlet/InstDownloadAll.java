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

import edu.ycp.cs.pygmymarmoset.app.controller.GetAllSubmissionsController;
import edu.ycp.cs.pygmymarmoset.app.model.Project;

@Route(pattern="/i/downloadAll/*")
public class InstDownloadAll extends AbstractServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Project project = (Project) req.getAttribute("project");
		if (project == null) {
			throw new ServletException("No project!");
		}
		
		GetAllSubmissionsController getSubs = new GetAllSubmissionsController();
		
		OutputStream out = resp.getOutputStream();
		ZipOutputStream zout = new ZipOutputStream(out);
		
		resp.setContentType("application/zip");
		String fileName = project.getName() + "-all.zip"; // probably doesn't match Marmoset's naming convention
		resp.addHeader("Content-Disposition", "attachment; filename=" + fileName);
		
		getSubs.execute(project, zout);
		zout.finish();
	}
}
