package com.agustosoftware.storechecker.domain.entity;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Store extends ResourceSupport{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "name")
    @NotEmpty(message = "*Please provide a store name")
    private String name;

    @Column(name = "address")
    @NotEmpty(message = "*Please provide a store address")
    private String address;

    private String image;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "store_checklist", joinColumns = @JoinColumn(name = "store_id"), inverseJoinColumns = @JoinColumn(name = "checklist_id"))
    private Set<Checklist> checklists;

    public void addChecklist(Checklist checklist) {
        if(checklists == null){
            checklists = new HashSet<>();
        }
        checklists.add(checklist);
    }
}
