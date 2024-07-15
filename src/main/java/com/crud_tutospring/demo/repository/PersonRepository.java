package com.crud_tutospring.demo.repository;
//pour pouvoir manipuler les données de la base de données,on va utiliser JPA (Java Persistence API)
// qui est une spécification Java pour la gestion de la persistance des objets.
// Spring Data JPA est une extension de Spring Data qui permet de faciliter l'utilisation de JPA dans les applications Spring.
//Pour pouvoir manipuler les elements(comment les stokes,recuperer,trouver etc...),
// nous devons créer ce qu on appelle un repository.
// une repository reprensente une Entity dans la base de données.

//ici on va creer une interface PersonRepository pour la classe Person

import com.crud_tutospring.demo.modelEntity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
