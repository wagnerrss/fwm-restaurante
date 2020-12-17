package com.fwm.restaurante.api;

import com.fwm.restaurante.domain.*;
import com.fwm.restaurante.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

//eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJSRVNULTAwMDAxIiwiZXhwIjoxOTIyNDA4ODUwLCJyb2wiOlsiUk9MRV9VU0VSIl19.Gaj7j3U_5s3Ygpdf0Rn2uX6ENK5fqz8MXsW7Edev5mGbjIVv53Yf_qieg4AAMMtDJ42WZLhDGpQKZBy7FyT1eg

@RestController
@RequestMapping("/api/v1/restaurante")
public class RestauranteController {

    @Autowired
    private ProductService productService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DestinationService destinationService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private LoginService loginService;

    @GetMapping
    public String get() {
        return "Get API FWM para restaurante";
    }

    @PostMapping
    public String post() {
        return "Post API FWM para restaurante";
    }

    @PutMapping
    public String put() {
        return "Put API FWM para restaurante";
    }

    @DeleteMapping
    public String delete() {
        return "Delete API FWM para restaurante";
    }

    private URI getUri(Integer id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }

    private Map mapErro(String tipo, String mensagem) {
        switch (tipo) {
            case "GET":
                return new LinkedHashMap<String, Object>() {
                    {
                        put("status", 404);
                        put("mensagem", mensagem.trim().equals("") ? "Registro não encontrado!" : mensagem);
                    }
                };
            case "POST":
                return new LinkedHashMap<String, Object>() {
                    {
                        put("status", 400);
                        put("mensagem", mensagem.trim().equals("") ? "Não foi possível inserir o registro!" : mensagem);
                    }
                };
            case "PUT":
                return new LinkedHashMap<String, Object>() {
                    {
                        put("status", 400);
                        put("mensagem", mensagem.trim().equals("") ? "Não foi possível atualizar o registro!" : mensagem);
                    }
                };
            case "DELETE":
                return new LinkedHashMap<String, Object>() {
                    {
                        put("status", 400);
                        put("mensagem", mensagem.trim().equals("") ? "Não foi possível excluir o registro!" : mensagem);
                    }
                };
        }

        return new LinkedHashMap();
    }


//*****************************************
//    Product (Produtos
//*****************************************

    @GetMapping("/product")
    public ResponseEntity<Iterable<Product>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity getProducts(@PathVariable("id") Integer id) {
        Optional<Product> product = productService.getById(id);

        return product.isPresent() ?
                ResponseEntity.ok(product.get()) :
                new ResponseEntity<>(mapErro("GET", ""), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/product")
    public ResponseEntity postProduct(@RequestBody Product product) {
        try {
            Product p = productService.insert(product);

            URI location = getUri(p.getId());
            return ResponseEntity.created(location).build();
        } catch (Exception ex) {
            return new ResponseEntity<>(mapErro("POST", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/product/{id}")
    public ResponseEntity putProduct(@PathVariable("id") Integer id, @RequestBody Product product) {
        try {
            product.setId(id);
            Product p = productService.update(id, product);

            return p != null ?
                    ResponseEntity.ok(p) :
                    new ResponseEntity<>(mapErro("PUT", ""), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(mapErro("PUT", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") Integer id) {
        boolean ok = productService.delete(id);

        return ok ?
                ResponseEntity.ok().build() :
                new ResponseEntity<>(mapErro("DELETE", ""), HttpStatus.BAD_REQUEST);
    }

//*****************************************
//Category (Categoria)
//*****************************************

    @GetMapping("/category")
    public ResponseEntity<Iterable<Category>> getCategory() {
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @GetMapping("/category/{id}")
    public ResponseEntity getCategoryById(@PathVariable("id") Integer id) {
        Optional<Category> category = categoryService.getById(id);

        return category.isPresent() ?
                ResponseEntity.ok(category.get()) :
                new ResponseEntity<>(mapErro("GET", ""), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/category")
    public ResponseEntity postCategory(@RequestBody Category category) {
        try {
            Category c = categoryService.insert(category);

            URI location = getUri(c.getId());
            return ResponseEntity.created(location).build();
        } catch (Exception ex) {
            return new ResponseEntity<>(mapErro("POST", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/category/{id}")
    public ResponseEntity putCategory(@PathVariable("id") Integer id, @RequestBody Category category) {
        try {
            category.setId(id);
            Category c = categoryService.update(id, category);

            return c != null ?
                    ResponseEntity.ok(c) :
                    new ResponseEntity<>(mapErro("PUT", ""), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(mapErro("PUT", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity deleteCategory(@PathVariable("id") Integer id) {
        boolean ok = categoryService.delete(id);

        return ok ?
                ResponseEntity.ok().build() :
                new ResponseEntity<>(mapErro("DELETE", ""), HttpStatus.BAD_REQUEST);
    }

//*****************************************
//Destination (Destino)
//*****************************************

    @GetMapping("/destination")
    public ResponseEntity<Iterable<Destination>> getDestination() {
        return ResponseEntity.ok(destinationService.getDestinations());
    }

    @GetMapping("/destination/{id}")
    public ResponseEntity getDestinationById(@PathVariable("id") Integer id) {
        Optional<Destination> destination = destinationService.getById(id);

        return destination.isPresent() ?
                ResponseEntity.ok(destination.get()) :
                new ResponseEntity<>(mapErro("GET", ""), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/destination")
    public ResponseEntity postDestination(@RequestBody Destination destination) {
        try {
            Destination d = destinationService.insert(destination);

            URI location = getUri(d.getId());
            return ResponseEntity.created(location).build();
        } catch (Exception ex) {
            return new ResponseEntity<>(mapErro("POST", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/destination/{id}")
    public ResponseEntity putDestination(@PathVariable("id") Integer id, @RequestBody Destination destination) {
        try {
            destination.setId(id);
            Destination d = destinationService.update(id, destination);

            return d != null ?
                    ResponseEntity.ok(d) :
                    new ResponseEntity<>(mapErro("PUT", ""), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(mapErro("PUT", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/destination/{id}")
    public ResponseEntity deleteDestination(@PathVariable("id") Integer id) {
        boolean ok = destinationService.delete(id);

        return ok ?
                ResponseEntity.ok().build() :
                new ResponseEntity<>(mapErro("DELETE", ""), HttpStatus.BAD_REQUEST);
    }


//*****************************************
//Type (Tipo)
//*****************************************

    @GetMapping("/type")
    public ResponseEntity<Iterable<Type>> getType() {
        return ResponseEntity.ok(typeService.getTypes());
    }

    @GetMapping("/type/{id}")
    public ResponseEntity getTypeById(@PathVariable("id") Integer id) {
        Optional<Type> type = typeService.getById(id);

        return type.isPresent() ?
                ResponseEntity.ok(type.get()) :
                new ResponseEntity<>(mapErro("GET", ""), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/type")
    public ResponseEntity postType(@RequestBody Type type) {
        try {
            Type t = typeService.insert(type);

            URI location = getUri(t.getId());
            return ResponseEntity.created(location).build();
        } catch (Exception ex) {
            return new ResponseEntity<>(mapErro("POST", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/type/{id}")
    public ResponseEntity putType(@PathVariable("id") Integer id, @RequestBody Type type) {
        try {
            type.setId(id);
            Type t = typeService.update(id, type);

            return t != null ?
                    ResponseEntity.ok(t) :
                    new ResponseEntity<>(mapErro("PUT", ""), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(mapErro("PUT", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/type/{id}")
    public ResponseEntity deleteType(@PathVariable("id") Integer id) {
        boolean ok = typeService.delete(id);

        return ok ?
                ResponseEntity.ok().build() :
                new ResponseEntity<>(mapErro("DELETE", ""), HttpStatus.BAD_REQUEST);
    }

//*****************************************
//Employee (Funcionario)
//*****************************************

    @GetMapping("/employee")
    public ResponseEntity<Iterable<Employee>> getEmployee() {

        return ResponseEntity.ok(employeeService.getEmployees());
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity getEmployeeById(@PathVariable("id") Integer id) {
        Optional<Employee> employee = employeeService.getById(id);

        return employee.isPresent() ?
                ResponseEntity.ok(employee.get()) :
                new ResponseEntity<>(mapErro("GET", ""), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/employee")
    public ResponseEntity postEmployee(@RequestBody Employee employee) {
        try {
            Employee e = employeeService.insert(employee);

            URI location = getUri(e.getId());
            return ResponseEntity.created(location).build();
        } catch (Exception ex) {
            return new ResponseEntity<>(mapErro("POST", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity putEmployee(@PathVariable("id") Integer id, @RequestBody Employee employee) {
        try {
            employee.setId(id);
            Employee e = employeeService.update(id, employee);

            return e != null ?
                    ResponseEntity.ok(e) :
                    new ResponseEntity<>(mapErro("PUT", ""), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(mapErro("PUT", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity deleteEmployee(@PathVariable("id") Integer id) {
        boolean ok = employeeService.delete(id);

        return ok ?
                ResponseEntity.ok().build() :
                new ResponseEntity<>(mapErro("DELETE", ""), HttpStatus.BAD_REQUEST);
    }

//*****************************************
//Payment (Pagamentos)
//*****************************************

    @GetMapping("/payment")
    public ResponseEntity<Iterable<Payment>> getPayment() {

        return ResponseEntity.ok(paymentService.getPayments());
    }

    @GetMapping("/payment/{id}")
    public ResponseEntity getPaymentById(@PathVariable("id") Integer id) {
        Optional<Payment> payment = paymentService.getById(id);

        return payment.isPresent() ?
                ResponseEntity.ok(payment.get()) :
                new ResponseEntity<>(mapErro("GET", ""), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/payment")
    public ResponseEntity postPayment(@RequestBody Payment payment) {
        try {
            Payment p = paymentService.insert(payment);

            URI location = getUri(p.getId());
            return ResponseEntity.created(location).build();
        } catch (Exception ex) {
            return new ResponseEntity<>(mapErro("POST", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/payment/{id}")
    public ResponseEntity putPayment(@PathVariable("id") Integer id, @RequestBody Payment payment) {
        try {
            payment.setId(id);
            Payment p = paymentService.update(id, payment);

            return p != null ?
                    ResponseEntity.ok(p) :
                    new ResponseEntity<>(mapErro("PUT", ""), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(mapErro("PUT", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/payment/{id}")
    public ResponseEntity deletePayment(@PathVariable("id") Integer id) {
        boolean ok = paymentService.delete(id);

        return ok ?
                ResponseEntity.ok().build() :
                new ResponseEntity<>(mapErro("DELETE", ""), HttpStatus.BAD_REQUEST);
    }

    //*****************************************
//Login
//*****************************************
    @GetMapping("/login")
    public ResponseEntity getLogin(@RequestBody Map login) {
        try {
            Login l = loginService.findUser(login);

            if ((l == null) || (l.getId() <= 0)) {
                return ResponseEntity.badRequest().body("Usuário ou Grupo não encontrado!");
            }

            return ResponseEntity.ok(l);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Usuário ou Grupo não encontrado!");
        }
    }

//*****************************************
//SystemUser (Usuário do sistema)
//*****************************************

    @GetMapping("/systemUser")
    public ResponseEntity<Iterable<SystemUser>> getSystemUser() {

        return ResponseEntity.ok(systemUserService.getSystemUsers());
    }

    @GetMapping("/systemUser/{id}")
    public ResponseEntity getSystemUserById(@PathVariable("id") Integer id) {
        Optional<SystemUser> systemUser = systemUserService.getById(id);

        return systemUser.isPresent() ?
                ResponseEntity.ok(systemUser.get()) :
                new ResponseEntity<>(mapErro("GET", ""), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/systemUser")
    public ResponseEntity postSystemUser(@RequestBody SystemUser systemUser) {
        try {
            SystemUser s = systemUserService.insert(systemUser);

            URI location = getUri(s.getId());
            return ResponseEntity.created(location).build();
        } catch (Exception ex) {
            return new ResponseEntity<>(mapErro("POST", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/systemUser/{id}")
    public ResponseEntity putSystemUser(@PathVariable("id") Integer id, @RequestBody SystemUser systemUser) {

        try {
            systemUser.setId(id);
            SystemUser s = systemUserService.update(id, systemUser);

            return s != null ?
                    ResponseEntity.ok(s) :
                    new ResponseEntity<>(mapErro("PUT", ""), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(mapErro("PUT", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/systemUser/{id}")
    public ResponseEntity deleteSystemUser(@PathVariable("id") Integer id) {
        boolean ok = systemUserService.delete(id);

        return ok ?
                ResponseEntity.ok().build() :
                new ResponseEntity<>(mapErro("DELETE", ""), HttpStatus.BAD_REQUEST);
    }

}
