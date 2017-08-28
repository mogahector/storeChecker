package com.agustosoftware.storechecker.domain.entity;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "item_id")
    private Long itemId;


    @Column(name = "question")
    private String question;

    @Column(name = "value")
    private String value;
}
