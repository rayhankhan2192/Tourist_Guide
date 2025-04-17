package com.TouristNest.travelGuide.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Entity
@Data
@Getter
public class User {
    
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generate id
    // private Long id;

    @Column(unique = true)
    private String email;

    private String name;
    private int number;
    private String password;
    private String confirm_password;
    private String role;

    public boolean equals(String password, String confirmPassword) {
        return true;
    }

    public User(){
        super();
    }


    public User(String name, String email, int number, String password, String confirm_password, String role){
        this.name = name;
        this.email = email;
        this.number = number;
        this.password = password;
        this.confirm_password = confirm_password;
        this.role = role;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setEmail(String email){
        this.email = email;
    }
   public void  setNumber(int number){
        this.number = number;
   }
    public void setPassword(String password){
        this.password = password;
    }
    public void setConfirm_pass(String confirm_password){
        this.confirm_password = confirm_password;
    }
    public void setRole(String role){
        this.role = role;
    }

}
