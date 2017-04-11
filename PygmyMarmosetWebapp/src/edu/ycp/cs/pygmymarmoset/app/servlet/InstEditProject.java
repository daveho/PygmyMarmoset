package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.UpdateProjectController;
import edu.ycp.cs.pygmymarmoset.app.model.Project;

@Route(pattern="/i/editProject/*", view="/_view/instEditProject.jsp")
@Navigation(parent=InstCourse.class)
@CrumbSpec(text="", items={PathInfoItem.COURSE_ID, PathInfoItem.PROJECT_ID})
public class InstEditProject extends AbstractFormServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected Params createParams(HttpServletRequest req) {
		// The idea is to edit the Project loaded by the
		// InstLoadProject filter
		return new Params(req)
				.addFromRequest("project", Project.class);
	}

	@Override
	protected LogicOutcome doLogic(Params params, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Project project = params.get("project");
		if (project == null) {
			throw new RuntimeException("No project in params?");
		}
		UpdateProjectController updateProject = new UpdateProjectController();
		updateProject.execute(project);
		req.setAttribute("resultmsg", "Successfully updated project " + project.getName());
		return LogicOutcome.STAY_ON_PAGE;
	}
}
