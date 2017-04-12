package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.util.ServletUtil;

public abstract class AbstractViewEntryServlet extends AbstractServlet {
	private static final long serialVersionUID = 1L;

	private final int entryIndexArg; // which path info argument is the entry index
	
	public AbstractViewEntryServlet(int entryIndexArg) {
		this.entryIndexArg = entryIndexArg;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Fifth argument is the entry index
		List<Integer> args = ServletUtil.getRequestArgs(req);
		if (args.size() < entryIndexArg + 1) {
			ServletUtil.sendBadRequest(req, resp, "Missing entry index");
			return;
		}
		
		Integer entryIndex = args.get(entryIndexArg);
		req.setAttribute("entryIndex", entryIndex);
		
		delegateToView(req, resp);
	}
}
