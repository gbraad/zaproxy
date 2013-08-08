package org.zaproxy.zap.userauth.session;

import org.apache.log4j.Logger;
import org.parosproxy.paros.Constant;
import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.extension.httpsessions.HttpSession;
import org.zaproxy.zap.model.Context;
import org.zaproxy.zap.userauth.session.CookieBasedSessionManagementMethodType.CookieBasedSessionManagementMethod;

/**
 * The type corresponding to a {@link SessionManagementMethod} for web applications that use cookies
 * for session management.
 */
public class CookieBasedSessionManagementMethodType extends
		SessionManagementMethodType<CookieBasedSessionManagementMethod> {

	/** The Constant METHOD_NAME. */
	private static final String METHOD_NAME = Constant.messages.getString("sessionmanagement.method.cb.name");

	/**
	 * The implementation for a {@link SessionManagementMethod} that for web applications that use
	 * cookies for session management.
	 */
	public static class CookieBasedSessionManagementMethod implements SessionManagementMethod {

		private static final Logger log = Logger.getLogger(CookieBasedSessionManagementMethod.class);

		@Override
		public String toString() {
			return CookieBasedSessionManagementMethodType.METHOD_NAME;
		}

		@Override
		public boolean isConfigured() {
			// Always configured
			return true;
		}

		@Override
		public WebSession extractWebSession(HttpMessage msg) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void processMessageToMatchSession(HttpMessage message, WebSession session)
				throws UnsupportedWebSessionException {

			if (message.getHttpSession() != session) {
				if (log.isDebugEnabled()) {
					log.debug("Modifying message to match session: " + session);
				}

				// make sure it's the right type
				if (!(session instanceof HttpSession)) {
					throw new UnsupportedWebSessionException(
							"The WebSession type provided is unsupported. Cookie based session management only supports "
									+ HttpSession.class + " type of WebSession.");
				}

				CookieBasedSessionManagementHelper.processMessageToMatchSession(message,
						(HttpSession) session);
			}
		}
	}

	@Override
	public CookieBasedSessionManagementMethod createSessionManagementMethod(int contextId) {
		return new CookieBasedSessionManagementMethod();
	}

	@Override
	public String getName() {
		return METHOD_NAME;
	}

	@Override
	public AbstractSessionManagementMethodOptionsPanel<CookieBasedSessionManagementMethod> buildOptionsPanel(
			CookieBasedSessionManagementMethod existingMethod, Context uiSharedContext) {
		// No need for a configuration panel yet
		return null;
	}

	@Override
	public boolean hasOptionsPanel() {
		return false;
	}

	@Override
	public boolean isFactoryForMethod(Class<? extends SessionManagementMethod> methodClass) {
		return CookieBasedSessionManagementMethod.class == methodClass;
	}

}