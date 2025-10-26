package in.nikita.filters;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import in.nikita.entity.UserRegisterEntity;
import in.nikita.repository.UserRegisterRepository;
import in.nikita.util.JWTUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class JWTFilters extends OncePerRequestFilter {

    @Autowired
    private JWTUtils jwtUtil;

    @Autowired
    private UserRegisterRepository userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.startsWith("/login") || path.startsWith("/register") 
        	    || path.startsWith("/index") || path.startsWith("/logout") 
        	    || path.startsWith("/styles/") || path.startsWith("/js/") || path.startsWith("/images/") 
        	    || path.startsWith("/saveMessage")  
        	) {
        	    filterChain.doFilter(request, response);
        	    return;
        	}
        System.out.println("JWT Filter checking path: " + path);
        String authHeader = request.getHeader("Authorization");

        // If no header, try session token
        if (authHeader == null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                String sessionToken = (String) session.getAttribute("token");
                if (sessionToken != null) {
                    authHeader = "Bearer " + sessionToken;
                }
            }
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                String username = jwtUtil.extractUsername(token);

                // Fetch user from DB to check token
                UserRegisterEntity user = userRepo.findByUserEmail(username).orElse(null);
                if (user == null || user.getToken() == null || !user.getToken().equals(token)) {
                    // Token is invalid or cleared (logout)
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Invalid or expired token. Please login again.");
                    return;
                }

                String role = jwtUtil.extractRole(token);
                String name = jwtUtil.extractName(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    if (jwtUtil.validateToken(token, username)) {

                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                Collections.singletonList(new SimpleGrantedAuthority(role))
                        );

                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(auth);

                        HttpSession session = request.getSession(true);
                        session.setAttribute("userName", name);
                        session.setAttribute("userRole", role);
                        session.setAttribute("username", username);
                        session.setAttribute("token", token);
                    }
                }
            } catch (ExpiredJwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token expired. Please login again.");
                return;
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid token");
                return;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing or invalid Authorization header");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
