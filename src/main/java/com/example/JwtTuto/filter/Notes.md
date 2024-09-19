# JwtAuthFilter
The `JwtAuthFilter` class is a custom filter in a Spring Boot application that processes incoming HTTP requests to authenticate users based on JWT tokens. It extends `OncePerRequestFilter`, ensuring it is executed once per request.

### Key Components:

1. **Dependencies**:
   ```java
   @Autowired
   private JwtService jwtService;

   @Autowired
   private UserInfoService userDetailsService;
   ```
   These services are injected to handle JWT operations and user details retrieval.

2. **doFilterInternal Method**:
   ```java
   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       // Retrieve the Authorization header
       String authHeader = request.getHeader("Authorization");
       String token = null;
       String username = null;

       // Check if the header starts with "Bearer "
       if (authHeader != null && authHeader.startsWith("Bearer ")) {
           token = authHeader.substring(7); // Extract token
           username = jwtService.extractUsername(token); // Extract username from token
       }

       // If the token is valid and no authentication is set in the context
       if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
           UserDetails userDetails = userDetailsService.loadUserByUsername(username);

           // Validate token and set authentication
           if (jwtService.validateToken(token, userDetails)) {
               UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                       userDetails,
                       null,
                       userDetails.getAuthorities()
               );
               authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               SecurityContextHolder.getContext().setAuthentication(authToken);
           }
       }

       // Continue the filter chain
       filterChain.doFilter(request, response);
   }
   ```
    - **Authorization Header**: Retrieves the `Authorization` header from the request.
    - **Token Extraction**: Checks if the header starts with "Bearer " and extracts the token.
    - **Username Extraction**: Uses `jwtService` to extract the username from the token.
    - **Authentication**: If the username is valid and no authentication is set, it loads user details and validates the token. If valid, it sets the authentication in the `SecurityContextHolder`.

### Usage in the Project:
This filter is part of the security configuration and is used to intercept requests to ensure that they contain a valid JWT token. It is typically registered in the Spring Security filter chain, ensuring that every request is authenticated before accessing protected resources.

### Importance:
- **Security**: Ensures that only authenticated users can access certain endpoints.
- **Stateless Authentication**: Uses JWT, which is stateless and does not require server-side sessions, making it scalable.
- **Integration**: Works seamlessly with Spring Security to provide a robust security mechanism.