package com.example.myapp.domain.entities;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@Table(name = "tbl_image")
@NoArgsConstructor
@AllArgsConstructor
public class Image implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String url;
    private String uid;
}

