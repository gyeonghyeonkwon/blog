package com.ll.blog.domain.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {
    /**
     * 필요한 권한이 없이 접근하려 할때 403
     * ex) User권한을 가진 사람이 Admin 권한이 있는 곳에 접근하려 할 때 발생
     */
    response.sendError(HttpServletResponse.SC_FORBIDDEN);
  }
}
