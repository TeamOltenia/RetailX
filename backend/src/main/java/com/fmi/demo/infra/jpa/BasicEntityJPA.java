package com.fmi.demo.infra.jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
public abstract class BasicEntityJPA {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2")
    @Column(name= "id" , unique = true , length = 36)
    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicEntityJPA that = (BasicEntityJPA) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        if( id == null || id.equals("")){
            return super.hashCode();
        }
        return Objects.hash(getId());
    }
}
