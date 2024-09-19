# AuthRequest
The `AuthRequest` class is a simple Java class used to encapsulate authentication request data, specifically the user's email and password. This class is part of a Spring Boot application and is likely used to transfer authentication data from the client to the server.

### Key Annotations and Imports
- `@Data`: A Lombok annotation that generates getters, setters, `toString()`, `equals()`, and `hashCode()` methods.
- `@AllArgsConstructor`: Generates a constructor with one parameter for each field in the class.
- `@NoArgsConstructor`: Generates a no-argument constructor.

### Class Definition
```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    private String email;
    private String password;
}
```

### Fields
- `private String email;`: Stores the user's email.
- `private String password;`: Stores the user's password.

### Usefulness
This class is useful for our application as it provides a structured way to handle authentication data. It simplifies the process of binding request data to a Java object, which can then be easily validated and processed by the authentication logic in the application.

# UserInfo

The `UserInfo` class is a JPA entity representing user information in the application. It is annotated with `@Entity`, indicating that it is a JPA entity and will be mapped to a database table.
### Key Annotations
```java
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
```

The class uses Lombok annotations `@Data`, `@AllArgsConstructor`, and `@NoArgsConstructor` to automatically generate boilerplate code such as getters, setters, constructors, `toString()`, `equals()`, and `hashCode()` methods.

The `@Id` and `@GeneratedValue` annotations specify that the `id` field is the primary key and its value will be automatically generated.

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
```
### Fields
The class contains fields for storing user details such as `name`, `email`, `password`, and `roles`.

```java
private String name;
private String email;
private String password;
private String roles;
```
### Usefulness
This class is useful for the application as it defines the structure of user data that will be stored in the database. It can be used in various parts of the application, such as authentication, authorization, and user management functionalities.