package com.crud_tutospring.demo.controller;

//le controller est une classe qui permet de gérer les requêtes HTTP, c'est-à-dire les requêtes des clients(du frontend)
//et test(comme Postman) et de retourner les réponses appropriées.

import com.crud_tutospring.demo.common.PersonNotFoundException;
import com.crud_tutospring.demo.modelEntity.Person;
import com.crud_tutospring.demo.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/persons")
public class PersonController {
    //pour pouvoir gerer nos requetes, on doit injecter notre repository afin de pouvoir manipuler les données de la base de données.
    //pour cela on va utiliser l'annotation @Autowired
    //@Autowired,mais afin eviter les problemes de dependance, on va utiliser final
    final PersonRepository personRepository;//on injecte notre repository


    //on va creer une methode qui va permettre de recuperer tous les elements de la base de données
    //pour cela on va utiliser l'annotation @GetMapping
    @GetMapping//je pouvais ecris un chemin ,mais dans ce cas c est pas obligatoire
    public ResponseEntity<List<Person>> getAllPersons(){
        //afin de savoir que tous s est bien passer et voir un resulter clair et net, on va utiliser l'objet ResponseEntity
        //qui permet de voir  une reponse HTTP
        return new ResponseEntity<>(personRepository.findAll(), HttpStatus.OK);//on retourne tous les elements de la base de données,findAll() est une methode de JpaRepository(JPA)
    }

   @PostMapping
   //on va creer une methode qui va permettre d'ajouter un element dans la base de données
   //loerqu  on cree a la difference de get ,on envoie une information body qui contient les informations de l'element a ajouter
   //pour cela pour recuperer le corps de la requete, on va utiliser l'annotation @RequestBody
    public ResponseEntity<Person> addPerson(@RequestBody Person person){
        Person personCreated = personRepository.save(person);//on ajoute l'element dans la base de données
        return new ResponseEntity<>(personCreated,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    //on va creer une methode qui va permettre de recuperer un element de la base de données
    //pour cela on va utiliser l'annotation @GetMapping
    //mais cette fois ci on va ajouter un chemin qui va permettre de recuperer un element en fonction de son id
    //pour cela on va utiliser l'annotation @PathVariable, qui permet de recuperer un element de l'url connu
    public ResponseEntity<Person> getPersonById(@PathVariable Long id){
        //on va utiliser un optional ddans le cas si l'element n'existe pas
        Optional<Person> person = personRepository.findById(id);
        return person.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseThrow(() -> new PersonNotFoundException("Person not found with"));
    }

    @PutMapping("/{id}")
    //on va creer une methode qui va permettre de modifier un element de la base de données
    //pour cela on va utiliser l'annotation @PutMapping
    //mais cette fois ci on va ajouter un chemin qui va permettre de modifier un element en fonction de son id
    //pour cela on va utiliser l'annotation @PathVariable, qui permet de recuperer un element de l'url connu,
    //et l'annotation @RequestBody, qui permet de recuperer le corps de la requete
    public ResponseEntity<Person> updatePerson(@PathVariable Long id,@RequestBody Person personDetails){
        Optional<Person> personData = personRepository.findById(id);
        if(personData.isPresent()){
            Person existingPerson = personData.get();
            existingPerson.setCity(personDetails.getCity());
            existingPerson.setPhoneNumber(personDetails.getPhoneNumber());
            Person updatedPerson = personRepository.save(existingPerson);
            return new ResponseEntity<>(updatedPerson,HttpStatus.OK);
        }
           throw  new   PersonNotFoundException("Person not found");

    }

    @DeleteMapping("/{id}")
    //on va creer une methode qui va permettre de supprimer un element de la base de données
    //pour cela on va utiliser l'annotation @DeleteMapping
    //mais cette fois ci on va ajouter un chemin qui va permettre de supprimer un element en fonction de son id
    //pour cela on va utiliser l'annotation @PathVariable, qui permet de recuperer un element de l'url connu
    public ResponseEntity<Void> deletePerson(@PathVariable Long id){
        Optional<Person> person = personRepository.findById(id);
        if(person.isPresent()){
            personRepository.delete(person.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        throw  new  PersonNotFoundException("Person not found");
    }



}
