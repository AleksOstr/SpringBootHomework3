//package ru.geekbrains.springboothomework3.security;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.util.List;
//
//@Entity
//@Data
//@Table(name = "roles")
//public class Role {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @Column(name = "name")
//    private String name;
//
//    @ManyToMany(mappedBy = "roles")
//    private List<User> users;
//
//    public Role(String name) {
//        this.name = name;
//    }
//
//    public Role() {
//    }
//}
