package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.GetOntimeAndLateSubmissionsController;
import edu.ycp.cs.pygmymarmoset.app.model.Project;

@Route(pattern="/i/downloadOntime/*")
public class InstDownloadOntime extends AbstractServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Project project = (Project) req.getAttribute("project");
		
		GetOntimeAndLateSubmissionsController getSubs = new GetOntimeAndLateSubmissionsController();
		
		OutputStream out = resp.getOutputStream();
		ZipOutputStream zout = new ZipOutputStream(out);
		
		resp.setContentType("application/zip");
		String fileName = "p" + project.getName() + ".zip"; // matches Marmoset's naming convention
		resp.addHeader("Content-Disposition", "attachment; filename=" + fileName);
		
		getSubs.execute(project, zout);
		zout.finish();
	}
}
