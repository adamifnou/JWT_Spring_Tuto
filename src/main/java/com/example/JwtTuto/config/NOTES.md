# SecurityConfig

The `SecurityConfig` class configures Spring Security for a Spring Boot application. It includes several key components:

1. **Annotations**:
    - `@Configuration`, `@EnableWebSecurity`, and `@EnableMethodSecurity` enable Spring Security and method-level security.

2. **Beans**:
    - `UserDetailsService` bean (`userDetailsService` method) returns an instance of `UserInfoService`, which should implement `UserDetailsService`.
      ```java
      @Bean
      public UserDetailsService userDetailsService() {
          return new UserInfoService();
      }
      ```
    - `PasswordEncoder` bean (`passwordEncoder` method) returns a `BCryptPasswordEncoder` for password hashing.
      ```java
      @Bean
      public PasswordEncoder passwordEncoder() {
          return new BCryptPasswordEncoder();
      }
      ```
    - `AuthenticationProvider` bean (`authenticationProvider` method) configures a `DaoAuthenticationProvider` with the custom `UserDetailsService` and `PasswordEncoder`.
      ```java
      @Bean
      public AuthenticationProvider authenticationProvider() {
          DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
          authenticationProvider.setUserDetailsService(userDetailsService());
          authenticationProvider.setPasswordEncoder(passwordEncoder());
          return authenticationProvider;
      }
      ```
    - `AuthenticationManager` bean (`authenticationManager` method) retrieves the `AuthenticationManager` from the `AuthenticationConfiguration`.
      ```java
      @Bean
      public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
          return config.getAuthenticationManager();
      }
      ```

3. **Security Filter Chain**:
    - Configures HTTP security settings in the `securityFilterChain` method.
      ```java
      @Bean
      public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
          http
              .csrf(AbstractHttpConfigurer::disable)
              .authorizeHttpRequests(auth -> auth
                  .requestMatchers("/auth/welcome", "/auth/addNewUser", "/auth/generateToken").permitAll()
                  .requestMatchers("/auth/user/**").hasAuthority("ROLE_USER")
                  .requestMatchers("/auth/admin/**").hasAuthority("ROLE_ADMIN")
                  .anyRequest().authenticated()
              )
              .sessionManagement(sess -> sess
                  .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
              )
              .authenticationProvider(authenticationProvider())
              .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
 
          return http.build();
      }
      ```
    - Disables CSRF protection for stateless APIs.
    - Configures URL authorization rules.
    - Sets session management to stateless.
    - Adds a custom `JwtAuthFilter` before the `UsernamePasswordAuthenticationFilter`.

The `JwtAuthFilter` is a custom filter that likely handles JWT authentication, ensuring that requests are authenticated using JWT tokens.