package service.redirectStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.sessionValidator.SessionValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by kfrak on 06.01.2019.
 */
@Service
public class ToIndexRedirectStrategy implements RedirectStrategy {

    @Override
    public boolean supports(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getRequestURL().equals("/") || httpServletRequest.getRequestURL().equals("/index");
    }

    @Override
    public boolean execute(SessionValidator sessionValidator, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        if (supports(httpServletRequest)) {
            return doExecute(sessionValidator, httpServletRequest, httpServletResponse);
        }
        return true;
    }

    @Override
    public boolean doExecute(SessionValidator sessionValidator, HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse) throws IOException {
        HttpSession httpSession = httpServletRequest.getSession();

        if (sessionValidator.isSessionActive(httpSession)) {
            httpServletResponse.sendRedirect("userInfo");
        }
        httpServletResponse.sendRedirect("index");
        return true;
    }
}