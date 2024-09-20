package com.myonlineshopping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Balance {
    private Long idCuenta;
    private Long idPropietario;
    private Integer dinero;
}
