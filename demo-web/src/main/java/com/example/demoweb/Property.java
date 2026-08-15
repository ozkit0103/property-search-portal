package com.example.demoweb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property {
    private int id;
    private String name;
    private String price;
    private String location;
    private String layout;
}