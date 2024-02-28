package hellojpa.valueType.embedded;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.util.Objects;

@Embeddable
@Getter
public class Address {
    @Column(name = "CITY_NAME")
    private String city;

    private String street;

    @Embedded
    private Zipcode zipcode; //임베디드 타입 포함

    public Address() {
    }

    public Address(String city, String street, Zipcode zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }


}
