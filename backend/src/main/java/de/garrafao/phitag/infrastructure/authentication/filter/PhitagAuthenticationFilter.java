package de.garrafao.phitag.infrastructure.authentication.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import de.garrafao.phitag.domain.error.CustomRuntimeException;
import de.garrafao.phitag.infrastructure.authentication.service.AuthenticationTokenAuthenticationService;

public class PhitagAuthenticationFilter extends GenericFilterBean {

    private static final Logger APP_LOGGER = LoggerFactory.getLogger(PhitagAuthenticationFilter.class);

    private final AuthenticationTokenAuthenticationService authenticationTokenAuthenticationService;

    public PhitagAuthenticationFilter(
            AuthenticationTokenAuthenticationService authenticationTokenAuthenticationService) {
        this.authenticationTokenAuthenticationService = authenticationTokenAuthenticationService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            Authentication authentication = authenticationTokenAuthenticationService
                    .getAuthenticationFromRequest((HttpServletRequest) request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } catch (Exception ex) {
            APP_LOGGER.error("Exception thrown on request", ex);
            if (CustomRuntimeException.class.isAssignableFrom(ex.getCause().getClass())) {
                ((HttpServletResponse) response).sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        ex.getCause().getMessage());
            } else {
                ((HttpServletResponse) response).sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Internal server error");
            }
        }

    }

}
