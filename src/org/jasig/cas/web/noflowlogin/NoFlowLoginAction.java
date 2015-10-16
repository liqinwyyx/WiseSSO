package org.jasig.cas.web.noflowlogin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.authentication.principal.Credentials;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.jasig.cas.ticket.TicketException;
import org.jasig.cas.web.flow.InitialFlowSetupAction;
import org.jasig.cas.web.support.ArgumentExtractor;
import org.jasig.cas.web.support.CookieRetrievingCookieGenerator;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

public class NoFlowLoginAction extends AbstractController {

	@NotNull
	private CentralAuthenticationService centralAuthenticationService;
	@NotNull
	private CookieRetrievingCookieGenerator warnCookieGenerator;
	@NotNull
	private CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator;

	private InitialFlowSetupAction initialFlowSetupAction;

	/** Extractors for finding the service. */
	@NotEmpty
	private List<ArgumentExtractor> argumentExtractors;

	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String uName = request.getParameter("username");
		String password = request.getParameter("password");
		String checkCode = request.getParameter("authcode");
		System.out.println(checkCode);
		Credentials credentials = new UsernamePasswordCredentials(uName,
				password, checkCode);
		if (!this.initialFlowSetupAction.pathPopulated) {
			final String contextPath = request.getContextPath();
			final String cookiePath = StringUtils.hasText(contextPath) ? contextPath
					+ "/"
					: "/";
			logger.info("Setting path for cookies to: " + cookiePath);
			this.warnCookieGenerator.setCookiePath(cookiePath);
			this.ticketGrantingTicketCookieGenerator.setCookiePath(cookiePath);
			this.initialFlowSetupAction.pathPopulated = true;
		}
		final Service service = WebUtils.getService(this.argumentExtractors,
				request);
		String ticketGrantingTicketId = "";
		String serviceTicket = "";
		try {
			ticketGrantingTicketId = this.centralAuthenticationService
					.createTicketGrantingTicket(credentials);

			/***
			 * 产生新的票据，并将票据及服务记录在缓存中
			 */
			serviceTicket = this.centralAuthenticationService
					.grantServiceTicket(ticketGrantingTicketId, service);

			this.ticketGrantingTicketCookieGenerator.removeCookie(response);

			this.ticketGrantingTicketCookieGenerator.addCookie(request,
					response, ticketGrantingTicketId);

			this.warnCookieGenerator.addCookie(request, response, "true");

		} catch (TicketException e) {

			e.printStackTrace();
		}
		return new ModelAndView(new RedirectView(
				request.getParameter("service") + "?ticket=" + serviceTicket));
	}

	public void setWarnCookieGenerator(
			final CookieRetrievingCookieGenerator warnCookieGenerator) {
		this.warnCookieGenerator = warnCookieGenerator;
	}

	public void setArgumentExtractors(
			final List<ArgumentExtractor> argumentExtractors) {
		this.argumentExtractors = argumentExtractors;
	}

	public final void setCentralAuthenticationService(
			final CentralAuthenticationService centralAuthenticationService) {
		this.centralAuthenticationService = centralAuthenticationService;
	}

	public void setTicketGrantingTicketCookieGenerator(
			final CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator) {
		this.ticketGrantingTicketCookieGenerator = ticketGrantingTicketCookieGenerator;
	}

	public void setInitialFlowSetupAction(
			InitialFlowSetupAction initialFlowSetupAction) {
		this.initialFlowSetupAction = initialFlowSetupAction;
	}

}