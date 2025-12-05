package com.example.game.security;

import com.example.game.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(
           @NonNull HttpServletRequest request,
           @NonNull HttpServletResponse response,
           @NonNull  FilterChain filterChain) throws ServletException, IOException {

                // 1. Lấy header Authorization từ request
                final String authHeader = request.getHeader("authorization");
                final String jwt;
                final String username;
                
                // 2. Kiểm tra xem header có bắt đầu bằng "Bearer " không
                if (authHeader == null || !authHeader.startsWith("Bearer ")){
                    filterChain.doFilter(request, response);
                    return;
                }

                // 3. Cắt bỏ chữ "Bearer " để lấy Token gốc

                jwt = authHeader.substring(7);
                // 4. Lấy username từ Token
                // (Nếu token đểu hoặc hết hạn, code sẽ văng lỗi ở đây
                username = jwtUtils.extractUsername(jwt);

                // Nếu lấy được username và chưa được xác thực trong Context hiện tại
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    // Load thông tin từ DB ln
                    UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
                    // 6. Validate Token lần cuối
                    if (jwtUtils.validateToken(jwt,userDetails)){
                        // 7. Tạo đối tượng Authentication và set vào Context
                        // (Đây chính là bước "đóng dấu" là đã đăng nhập)
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                        authToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }

                // 8. Cho phép đi tiếp sang filter tiếp theo
                filterChain.doFilter(request,response);
    }
}
