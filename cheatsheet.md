
# Cheat Sheet

## Controller

A controller is a class that handles HTTP requests.

```java
@RestController
public class MyController {
    
    private final MyService myService;
    
    public MyController(MyService myService) {
        this.myService = myService;
    }

    @GetMapping("/hello/{pseudo}")
    public ResponseEntity<Object> hello(@PathVariable String pseudo) {
        Strin helloString = myService.hello(pseudo);
        return new ResponseEntity<>(helloString, HttpStatus.OK);
    }
}
```

Each Controller class should be annotated with `@RestController`.
A methode return a `ResponseEntity` object with the response body and the HTTP status code.
And each method that handles an HTTP request should be annotated with :
- `@GetMapping`
- `@PostMapping`
- `@PutMapping`
- `@PatchMapping`
- `@DeleteMapping`

## Service

A service is a class that contains the business logic of your application. (called by the controller)

```java
@Service
public class MyService {

    private final UsersProxy usersProxy;
    private final MyRepository myRepository;
    
    public MyService(UsersProxy usersProxy, MyRepository myRepository) {
        this.usersProxy = usersProxy;
        this.myRepository = myRepository;
    }

    public String hello(String pseudo) {
        if (usersProxy.exists(pseudo)) {
            myRepository.addOneToQuantity(pseudo);
            return "Hello " + pseudo;
        } else {
            return "User not found";
        }
    }
}
```

Each Service class should be annotated with `@Service`.
A service dans use other services or proxies to do its job.
In this case, the service `MyService` uses the proxy `UsersProxy` to check if a user exists.

## Repository

A repository is a class that contains the logic to access the database. (called by the service

```java
@Repository
public interface MyRepository extends CrudRepository<Wishlist, String> {
    
    boolean addOneToQuantity(String pseudo);
}
```

Each Repository class should be annotated with `@Repository`.
And the class extends `CrudRepository` with the entity class and the type of the primary key.
CRUD means `Create`, `Read`, `Update`, `Delete`.

JPA automatically generates the SQL queries for you if the name of the method follows the naming convention.

## Entity (model)

An entity is a class that represents a table in the database.

```java
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "my_table")
public class MyEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    
    @Column(nullable = false)
    private String pseudo;
    
    @Column(nullable = false)
    private int quantity;
}
```

Each Entity class should be annotated with `@Entity`.

Each field should be annotated with `@Id` if it's the primary key.
`@GeneratedValue` to specify the generation strategy.
And `@Column` to specify the column name and if it's nullable.

`@Getter`, `@Setter`, `@ToString`, `@NoArgsConstructor`, `@AllArgsConstructor` are Lombok annotations to generate getters, setters, toString, constructors.

## Proxy

A proxy is a class that contains the logic to call another microservice.

```java
@Repository
@FeignClient(name = "users", url = "http://localhost:9002")
public interface UsersProxy {

    @GetMapping("/users/{pseudo}")
    boolean exists(@PathVariable String pseudo);
}
```

Each Proxy class should be annotated with `@Repository` and `@FeignClient`.
`@FeignClient` is used to specify the name of the microservice and its URL.

The name don't need to be the same as the microservice name but the URL should be the same.