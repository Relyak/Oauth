package com.myonlineshopping.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    @Size(min = 3, max = 15, message = "Debe tener entre 3 y 15 letras ")
    private String type;
    // @NotBlank
    @NotNull
    private int balance;
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "El formato de la fecha debe ser YYYY-MM-DD")
    private String opening_date;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "owner_id")
    private Customer owner;


    public Account(int i, String personal, int i1, String date) {
    }
}
