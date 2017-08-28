package com.agustosoftware.storechecker.domain.entity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;

@Data
@Entity
public class Store extends ResourceSupport{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long storeId;

    @Column(name = "name")
    @NotEmpty(message = "*Please provide a store name")
    private String name;

    @Column(name = "address")
    @NotEmpty(message = "*Please provide a store address")
    private String address;

    private String image;

}
