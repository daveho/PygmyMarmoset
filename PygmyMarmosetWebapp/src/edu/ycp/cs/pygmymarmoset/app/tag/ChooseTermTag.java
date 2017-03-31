package edu.ycp.cs.pygmymarmoset.app.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.beanutils.PropertyUtils;

import edu.ycp.cs.pygmymarmoset.app.model.Term;

public class ChooseTermTag extends BeanTag {
	@Override
	public void doTag() throws JspException, IOException {
		super.doTag();

		Object bean = getBean();
		String obj = getObj();
		String field = getField();
		
		// Get already-selected term id from model object
		Integer termId;
		try {
			termId = (Integer) PropertyUtils.getProperty(bean, field);
		} catch (Exception e) {
			throw new IllegalStateException("Couldn't get " + field + " property as term id", e);
		}
		
		// We assume there is a request attribute "terms" that is the
		// list of Terms.
		@SuppressWarnings("unchecked")
		List<Term> terms = (List<Term>) TagUtil.getRequestAttribute(getJspContext(), "terms");
		
		// Make sure the current termId identifies an actual
		// Term object.  If not, select the first one.
		boolean selectedOk = false;
		for (Term term : terms) {
			if (termId.equals(term.getId())) {
				selectedOk = true;
			}
		}
		if (!selectedOk) {
			termId = terms.get(0).getId();
		}
		
		String propName = obj + "." + field;
		
		JspWriter out = getJspContext().getOut();
		
		out.print("<select name=\"");
		out.print(propName);
		out.print("\">");
		for (Term term : terms) {
			out.print("<option value=\"");
			out.print(term.getId());
			out.print("\"");
			if (termId.equals(term.getId())) {
				out.print(" selected");
			}
			out.print(">");
			out.print(term.getName());
			out.print("</option>");
		}
		out.print("</select>");
	}
}
