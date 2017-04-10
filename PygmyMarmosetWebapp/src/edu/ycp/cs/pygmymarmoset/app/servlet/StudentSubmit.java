package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.model.SubmissionBlob;

@Route(pattern="/u/submit/*", view="/_view/studentSubmit.jsp")
@Navigation(parent=StudentProject.class)
@CrumbSpec(text="Upload submission")
public class StudentSubmit extends AbstractFormServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected Params createParams(HttpServletRequest req) {
		return new Params(req)
				.add("submissionBlob", SubmissionBlob.class);
	}

	@Override
	protected LogicOutcome doLogic(Params params, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
