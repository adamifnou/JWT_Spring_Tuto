# JwtService
The `JwtService` class is a Spring component responsible for handling JWT (JSON Web Token) operations such as token generation, validation, and extraction of claims. Here's a breakdown of its fields and methods:

### Fields
- `@Value("${JWT.SECRET}") private String SECRET;`
    - This field holds the secret key used for signing the JWT. It is injected from the application properties.

### Methods
- `public String generateToken(String userEmail)`
    - Generates a JWT token for a given user email. It creates an empty claims map and calls `createToken`.

- `private String createToken(Map<String, Object> claims, String userEmail)`
    - Creates a JWT token with the specified claims and subject (user email). It sets the issued date, expiration date (24 hours), and signs the token using the `getSignKey` method.
  ```java
  return Jwts.builder()
      .setClaims(claims)
      .setSubject(userEmail)
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
      .signWith(getSignKey(), SignatureAlgorithm.HS256)
      .compact();
  ```

- `private Key getSignKey()`
    - Decodes the secret key from Base64 and returns a `Key` object suitable for HMAC SHA-256 signing.
  ```java
  byte[] keyBytes = Decoders.BASE64.decode(SECRET);
  return Keys.hmacShaKeyFor(keyBytes);
  ```

- `public String extractUsername(String token)`
    - Extracts the username (subject) from the JWT token by calling `extractClaim` with `Claims::getSubject`.

- `public Date extractExpiration(String token)`
    - Extracts the expiration date from the JWT token by calling `extractClaim` with `Claims::getExpiration`.

- `public <T> T extractClaim(String token, Function<Claims, T> claimsResolver)`
    - Extracts a specific claim from the JWT token using a provided function to resolve the claim.
  ```java
  final Claims claims = extractAllClaims(token);
  return claimsResolver.apply(claims);
  ```

- `private Claims extractAllClaims(String token)`
    - Parses the JWT token to extract all claims using the signing key.
  ```java
  return Jwts.parserBuilder()
      .setSigningKey(getSignKey())
      .build()
      .parseClaimsJws(token)
      .getBody();
  ```

- `private Boolean isTokenExpired(String token)`
    - Checks if the JWT token is expired by comparing the expiration date with the current date.

- `public Boolean validateToken(String token, UserDetails userDetails)`
    - Validates the JWT token by checking if the username matches and if the token is not expired.
  ```java
  final String username = extractUsername(token);
  return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  ```

This class is useful for managing JWT tokens in a Spring Boot application, providing methods to generate, validate, and extract information from tokens securely.

# UserInfoDetails
The `UserInfoDetails` class implements the `UserDetails` interface from Spring Security, which is used to provide user information to the Spring Security framework.

### Fields
- `private String email;`
- `private String password;`
- `private List<GrantedAuthority> authorities;`

These fields store the user's email, password, and authorities (roles) respectively.

### Constructor
```java
public UserInfoDetails(UserInfo userInfo) {
    this.email = userInfo.getEmail();
    this.password = userInfo.getPassword();
    this.authorities = List.of(userInfo.getRoles().split(","))
            .stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
}
```
The constructor initializes the fields using a `UserInfo` object. It splits the roles string by commas and maps them to `SimpleGrantedAuthority` objects.

### Methods
- `getAuthorities()`
  ```java
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
      return authorities;
  }
  ```
  Returns the list of authorities (roles) assigned to the user.

- `getPassword()`
  ```java
  @Override
  public String getPassword() {
      return password;
  }
  ```
  Returns the user's password.

- `getUsername()`
  ```java
  @Override
  public String getUsername() {
      return email;
  }
  ```
  Returns the user's email, which is used as the username.

- `isAccountNonExpired()`, `isAccountNonLocked()`, `isCredentialsNonExpired()`, `isEnabled()`
  ```java
  @Override
  public boolean isAccountNonExpired() {
      return true; // Implement your logic if you need this
  }

  @Override
  public boolean isAccountNonLocked() {
      return true; // Implement your logic if you need this
  }

  @Override
  public boolean isCredentialsNonExpired() {
      return true; // Implement your logic if you need this
  }

  @Override
  public boolean isEnabled() {
      return true; // Implement your logic if you need this
  }
  ```
  These methods return `true` by default, indicating that the account is not expired, not locked, credentials are not expired, and the account is enabled. You can implement custom logic if needed.

### Summary
The `UserInfoDetails` class is essential for integrating user information with Spring Security, providing necessary details like username, password, and authorities.

# UserInfoService
The `UserInfoService` class is a Spring Boot service that implements the `UserDetailsService` interface to manage user-related operations, such as loading user details, adding users, and deleting users. This class is essential for handling user authentication and management in the application.

### Key Components:

1. **Annotations and Dependencies**:
    - `@Service`: Marks the class as a Spring service.
    - `@Autowired`: Injects dependencies like `UserInfoRepository` and `PasswordEncoder`.

    ```java
    @Service
    public class UserInfoService implements UserDetailsService {
        @Autowired
        private UserInfoRepository repository;

        @Autowired
        private PasswordEncoder encoder;
    ```

2. **Loading User by Username**:
    - Implements `loadUserByUsername` method from `UserDetailsService`.
    - Fetches user details using the email (assumed as username) and converts `UserInfo` to `UserDetails`.

    ```java
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail = repository.findByEmail(email);
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }
    ```

3. **Adding a User**:
    - Encodes the user's password before saving.
    - Saves the user to the repository.

    ```java
    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User Added Successfully";
    }
    ```

4. **Deleting a User**:
    - Checks if the user exists by ID.
    - Deletes the user if found, otherwise throws an exception.

    ```java
    public void deleteUser(int id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("User not found");
        }
        repository.deleteById(id);
    }
    ```

### Why It's Useful:

- **Authentication**: Implements `UserDetailsService` to integrate with Spring Security for user authentication.
- **User Management**: Provides methods to add and delete users, ensuring proper handling of user data.
- **Security**: Encodes passwords before saving, enhancing security.