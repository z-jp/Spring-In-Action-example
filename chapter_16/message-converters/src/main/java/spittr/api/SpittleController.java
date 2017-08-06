package spittr.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import spittr.Spittle;
import spittr.data.Error;
import spittr.data.SpittleNotFoundException;
import spittr.data.SpittleRepository;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/spittles")
public class SpittleController {

    private static final String MAX_INTEGER_AS_STRING = "2147483647";

    private SpittleRepository repository;

    @Autowired
    public void setRepository(SpittleRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<Spittle> spittles(@RequestParam(value = "max", defaultValue = MAX_INTEGER_AS_STRING) int max,
                                  @RequestParam(value = "count", defaultValue = "20") int count) {
        return repository.findRecent(max, count);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Spittle spittleById(@PathVariable Long id) {
        return repository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Spittle> saveSpittle(@RequestBody Spittle spittle, UriComponentsBuilder ucb) {
        Spittle saved = repository.save(spittle);

        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path("/spittles/")
                .path(String.valueOf(saved.getId()))
                .build()
                .toUri();
        headers.setLocation(locationUri);

        return new ResponseEntity<>(saved, headers, HttpStatus.CREATED);
    }

    @ExceptionHandler(SpittleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody
    Error spittleNotFound(SpittleNotFoundException e) {
        long spittleId = e.getSpittleId();
        return new Error(4, "Spittle [" + spittleId + "] not found");
    }

}
