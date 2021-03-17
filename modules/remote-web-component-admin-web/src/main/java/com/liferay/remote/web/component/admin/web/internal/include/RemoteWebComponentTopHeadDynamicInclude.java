package com.liferay.remote.web.component.admin.web.internal.include;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.remote.web.component.admin.web.internal.util.LastModified;

@Component(
	service = DynamicInclude.class,
	property = {
		"service.ranking:Integer=1000"
	}
)
public class RemoteWebComponentTopHeadDynamicInclude extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			String key)
		throws IOException {

		PrintWriter printWriter = httpServletResponse.getWriter();

		printWriter.append("<link href=\"");
		printWriter.append(_servletContext.getContextPath());
		printWriter.append("/css/rwc.css?ts=");
		printWriter.append(LastModified.getString());
		printWriter.append("\" rel=\"stylesheet\" type=\"text/css\" />");
		printWriter.println();
		printWriter.append("<script src=\"");
		printWriter.append(_servletContext.getContextPath());
		printWriter.append("/remote-web-component-admin-web.js?ts=");
		printWriter.append(LastModified.getString());
		printWriter.append("\" type=\"text/javascript\" data-senna-track=\"permanent\"></script>");

		printWriter.flush();
	}

	@Override
	public void register(
		DynamicIncludeRegistry dynamicIncludeRegistry) {

		dynamicIncludeRegistry.register("/html/common/themes/top_head.jsp#post");
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.desjardins.fptc-webc-remote-integration)"
	)
	private ServletContext _servletContext;

}
