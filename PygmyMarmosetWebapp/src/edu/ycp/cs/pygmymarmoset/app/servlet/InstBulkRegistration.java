// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import edu.ycp.cs.pygmymarmoset.app.controller.BulkRegistrationController;
import edu.ycp.cs.pygmymarmoset.app.model.BulkRegistrationOutcome;
import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.PygmyMarmosetException;
import edu.ycp.cs.pygmymarmoset.app.model.SectionNumber;

@Route(pattern="/i/bulkReg/*", view="/_view/instBulkRegistration.jsp")
@Navigation(parent=InstCourse.class)
@CrumbSpec(text="Bulk registration", items={PathInfoItem.COURSE_ID})
@MultipartConfig(location="/tmp", fileSizeThreshold=1024*1024, maxFileSize=16*1024*1024)
public class InstBulkRegistration extends AbstractFormServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected Params createParams(HttpServletRequest req) {
		return new Params(req)
				.add("sectionNumber", SectionNumber.class);
	}

	@Override
	protected LogicOutcome doLogic(Params params, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// TODO: logic
		
		SectionNumber secNum = params.get("sectionNumber");
		Course course = (Course) req.getAttribute("course");
		
		req.setAttribute("resultmsg", "Need to register students in section " + secNum.getSection());

		// Read the roster CSV
		Part filePart;
		try {
			filePart = req.getPart("uploadData");
		} catch (ServletException e) {
			throw new PygmyMarmosetException("Could not retrieve file upload data", e);
		}
		
		BulkRegistrationController bulkReg = new BulkRegistrationController();
		InputStream in = filePart.getInputStream();
		
		// Do the bulk registration!
		List<BulkRegistrationOutcome> outcomes = bulkReg.execute(course, in, secNum);
		req.setAttribute("outcomes", outcomes);
		
		req.setAttribute("resultmsg", "Registered " + outcomes.size() + " student(s)");
		
		// Clear form data
		secNum.setSection(0);
		
		return LogicOutcome.STAY_ON_PAGE;
	}
}
