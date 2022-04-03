package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.example.demo.entity.Animal;
import com.example.demo.repository.AnimalRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.demo.responses.Response;

@RestController
public class AnimalController {

    @Autowired
    private AnimalRepository animalRepository;

    @RequestMapping("/")
    public String home(){
        return "Hello World!";
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Localiza todos os animais", notes = "Localiza todos os animais", response = Animal.class )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Animais do Zoológico listados com sucesso"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
    @RequestMapping(value = "/animal", method = RequestMethod.GET)
    public List<Animal> Get() {
        return animalRepository.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Localiza um Animal pelo Código", notes = "Localiza um Animal pelo Código", response = Animal.class )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Animal do Zoológico localizado com sucesso")
    })
    @RequestMapping(value = "/animal/{id}", method = RequestMethod.GET)
    public ResponseEntity<Animal> GetById(@PathVariable(value = "id") long id) {

        Optional<Animal> animal = animalRepository.findById(id);
        return animal.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Insere um Animal", notes = "Insere um Animal", response = Animal.class )
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Inserção do Animal do Zoológico feita com sucesso")
    })
    @RequestMapping(value = "/animal", method = RequestMethod.POST)
    public ResponseEntity<Response<Animal>> Post(@Valid @RequestBody Animal animal, BindingResult result) {
        Response<Animal> response = new Response<Animal>();
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }
        animalRepository.save(animal);
        response.setData(animal);
        return ResponseEntity.ok(response);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Atualizar um Animal", notes = "Atualizar um Animal", response = Animal.class )
    @ApiResponses(value = {
            @ApiResponse(code = 203, message = "Atualização do Animal do Zoológico feita com sucesso"),
    })
    @RequestMapping(value = "/animal/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Response<Animal>> Put(@PathVariable(value = "id") long id, @Valid @RequestBody
            Animal newAnimal, BindingResult result) {
        Optional<Animal> oldAnimal = animalRepository.findById(id);
        Response<Animal> response = new Response<Animal>();
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }
        if (oldAnimal.isPresent()) {
            Animal animal = oldAnimal.get();
            animal.setNomeAnimal(newAnimal.getNomeAnimal());
            response.setData(animal);
            animalRepository.save(animal);
            return ResponseEntity.ok(response);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Exclui um Animal", notes = "Exclui um Animal", response = Animal.class )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Exclusão do Animal do Zoológico feita com sucesso"),
    })
    @RequestMapping(value = "/animal/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id) {
        Optional<Animal> animal = animalRepository.findById(id);
        if (animal.isPresent()) {
            animalRepository.delete(animal.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}	