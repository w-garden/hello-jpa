package hellojpa.valueType.attributeOverride;

import hellojpa.valueType.embedded.Address;

import javax.persistence.*;

@Entity
public class Member11 {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Embedded
    Address11 homeAddress;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "city", column = @Column(name = "COMPANY_CITY"))
            , @AttributeOverride(name = "street", column = @Column(name = "COMPANY_STREET"))
            , @AttributeOverride(name = "zipcode", column = @Column(name = "COMPANY_ZIPCODE"))})
    Address11 companyAddress;
}
