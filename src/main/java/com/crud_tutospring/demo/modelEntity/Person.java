package com.crud_tutospring.demo.modelEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity//l annotation @Entity permet de dire à Spring que cette classe est une entité, c'est-à-dire une classe qui sera mappée à une table dans la base de données.
@Table(name = "persons")//l'annotation @Table permet de spécifier le nom de la table dans la base de données à laquelle la classe sera mappée.
@NoArgsConstructor//l'annotation @NoArgsConstructor permet de générer un constructeur sans argument.
@AllArgsConstructor//l'annotation @AllArgsConstructor permet de générer un constructeur avec tous les arguments.
@Getter//l'annotation @Getter permet de générer automatiquement les getters pour tous les attributs de la classe.
@Setter//l'annotation @Setter permet de générer automatiquement les setters pour tous les attributs de la classe.
public class Person {

    @Id//l'annotation @Id permet de spécifier que l'attribut qui la précède est une clé primaire.
    @GeneratedValue(strategy = GenerationType.IDENTITY)//l'annotation @GeneratedValue permet de spécifier que la valeur de l'attribut qui la précède sera générée automatiquement par la base de données.
    private Long id;

    private String name;

    private String city;

    private String phoneNumber;


}
