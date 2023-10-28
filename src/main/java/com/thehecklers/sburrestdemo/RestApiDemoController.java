package main.java.com.thehecklers.sburrestdemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

@RestController
@RequestMapping("/coffees")
class RestApiDemoController
{ 
    private List<Coffee> coffees = new ArrayList<>();

    public RestApiDemoController()
    { 
        coffees.addAll(
        List.of( 
            new Coffee("Café Cereza"),
            new Coffee("Café Ganador"),
            new Coffee("Café Lareño"),
            new Coffee("Café Três Pontas")
        ));
    }

    /*@RequestMapping(value = "/coffes", method = RequestMethod.GET)
    Iterable<Coffee> getCoffees() { 
        return coffee;
    }*/

    @GetMapping
    Iterable<Coffee> getCoffees() { 
        return coffees;
    }

    @GetMapping("/{id}")
    Optional<Coffee> getCoffeeById(@PathVariable String id) { 
        for (Coffee c : coffees) 
        { 
            if (c.getId().equals(id))
            { 
                return Optional.of(c);
            }
        }

        return Optional.empty();
    }

    @PostMapping
    Coffee postCoffee(@RequestBody Coffee coffee) { 
        coffees.add(coffee);
        return coffee;
    }

    /*@PutMapping("/{id}")
    Coffee putCoffee(@PathVariable String id, @RequestBody Coffee coffee) {
        int coffeeIndex = -1; 

        for (Coffee c : coffees) { 
            if (c.getId().equals(id)) {
                coffeeIndex = coffees.indexOf(c); coffees.set(coffeeIndex, coffee);
            }
        }

        return (coffeeIndex == -1) ? postCoffee(coffee) : coffee;
    }*/

    @PutMapping("/{id}")
    ResponseEntity<Coffee> putCoffee(@PathVariable String id, @RequestBody Coffee coffee) {
        int coffeeIndex = -1;
        for (Coffee c: coffees) {
            if (c.getId().equals(id)) {
                coffeeIndex = coffees.indexOf(c);
                coffees.set(coffeeIndex, coffee);
            }
        }
        return (coffeeIndex == -1) ?
        new ResponseEntity<>(postCoffee(coffee), HttpStatus.CREATED) :
        new ResponseEntity<>(coffee, HttpStatus.OK);
    }

    @DeleteMapping("/{id}") void
    deleteCoffee(@PathVariable String id) {
        coffees.removeIf(c -> c.getId().equals(id));
    }       

}
