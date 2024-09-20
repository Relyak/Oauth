package com.myonlineshopping.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="customers")
public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String email;

  @ToString.Exclude
  @JsonIgnore
  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
  private List<Account> accountList;

  public Customer(Long id) {
    this.id = id;
  }
}
