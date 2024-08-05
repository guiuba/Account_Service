package account.security;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Logger;

@Component
//@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Access Denied!") //
@Data
public class CustomAccessDeniedHandler implements AccessDeniedHandler  { //

 //   private final LogService logService;

   /* public CustomAccessDeniedHandler(LogService logService) {
        this.logService = logService;
    }*/

    public static final Logger LOG
            = Logger.getLogger(CustomAccessDeniedHandler.class.toString());
   /* private final LoggingService loggingService;

    public CustomAccessDeniedHandler(LoggingService loggingService) {
        this.loggingService = loggingService;
    }*/

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
   //     response.getWriter().write("Access Denied... Forbidden");
     //   String path = ((ServletWebRequest) request).getRequest().getRequestURI();

   /*     ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(403);
        response.getWriter().write(mapper.writeValueAsString(new CustomErrorMessage(
                                LocalDate.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                "Access Denied!",
                                "path")
                )); */
          /*      .add("timestamp", System.currentTimeMillis())
                .add("status", 403)
                .add("message", "Access denied")));*/
        Authentication auth
                = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            LOG.warning("Access Denied!");
            /*LOG.warning("User: " + auth.getName()
                    + " attempted to access the protected URL: "
                    + request.getRequestURI());*/
        }

    //    response.sendRedirect(request.getContextPath() + "/accessDenied");

 /*       String user = request.getRemoteUser();
        if (user == null) {
            user = "Anonymous";
        }

        loggingService.saveEntry(new Log(
                "ACCESS_DENIED",
                user.toLowerCase(),
                request.getRequestURI(),
                request.getRequestURI()));*/

         //"Access Denied!"   LOG.getName()
    }
}
