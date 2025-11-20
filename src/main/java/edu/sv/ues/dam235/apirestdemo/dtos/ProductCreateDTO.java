package edu.sv.ues.dam235.apirestdemo.dtos;

import lombok.Data;

@Data
public class ProductCreateDTO {
    private String name;
    private boolean status; // true/false
}