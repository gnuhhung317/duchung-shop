package net.duchung.shop_app.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import net.duchung.shop_app.component.JwtUtil;
import net.duchung.shop_app.entity.User;
import net.duchung.shop_app.exception.JwtAuthenticationException;
import net.duchung.shop_app.security.CustomUserDetailsService;
import net.duchung.shop_app.service.UserService;
import org.springframework.data.util.Pair;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
//    private UserService userService;
    private JwtUtil jwtUtil;
    private CustomUserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {

//        String authHeader = request.getHeader("Authorization");
//        if(authHeader==null||!authHeader.startsWith("Bearer ")){
//            filterChain.doFilter(request,response);
//            return;
//        }
//        String jwt = authHeader.substring(7);
//        String phoneNumber = jwtUtil.extractPhoneNumber(jwt);
//        if(phoneNumber!=null && SecurityContextHolder.getContext().getAuthentication()==null){
//            UserDetails userDetails = userDetailsService.loadUserByUsername(phoneNumber);
//            if(jwtUtil.isTokenExpired(jwt)){
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//        filterChain.doFilter(request,response);


        try{
            if(isBypassToken(request)){
                filterChain.doFilter(request,response);
                return;
            }
            final String authHeader = request.getHeader("Authorization");
            if(authHeader!=null && authHeader.startsWith("Bearer ")){
                String token = authHeader.substring(7);

                String phoneNumber = jwtUtil.extractPhoneNumber(token);
                if (phoneNumber !=null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(phoneNumber);
                    if (jwtUtil.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);

                    }
                }
                filterChain.doFilter(request,response);

            }else {
                throw new JwtAuthenticationException("Token not found");
            }
        }catch (JwtAuthenticationException e){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }

    }

    public boolean isBypassToken(@NonNull HttpServletRequest request){

        final List<Pair<String,String>> bypassTokens = Arrays.asList(
                Pair.of("/users/login","POST"),
                Pair.of("/users/register","POST"),
                Pair.of("/products","GET"),
                Pair.of("/categories","GET")

        );
        for (Pair<String,String> p : bypassTokens){
            if(request.getServletPath().contains(p.getFirst())&&request.getMethod().equals(p.getSecond())){
                return true;
            }
        }
        return false;
    }

}
