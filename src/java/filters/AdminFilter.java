/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import dataaccess.UserDB;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Role;
import models.User;

/**
 *
 * @author 840979
 */
public class AdminFilter implements Filter {
      @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpSession session = httpRequest.getSession();
        String sessionEmail = (String)session.getAttribute("email");
        UserDB ud= new UserDB();
        if(sessionEmail != null){
            User sessionUser = ud.get(sessionEmail);
            Role sessionUserRole = sessionUser.getRole();
            int sessionRoleId = sessionUserRole.getRoleId();
            
            if (sessionRoleId != 1){
            HttpServletResponse httpResponse = (HttpServletResponse)response;
            httpResponse.sendRedirect("notes");
            return;
            }
            else {
                HttpServletResponse httpResponse = (HttpServletResponse)response;
                httpRequest.getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
                return;
            }
        }
        
        // code before chain.doFilter will execute before the servlet
        
        chain.doFilter(request, response); // execute the servlet, or the next filter in the chain
        
        // code after chain.doFilter will execute after the servlet, during the response
        
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    
    @Override
    public void destroy() {}
    
  
}
