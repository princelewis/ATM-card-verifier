package com.threeLine.atmcardverifier.model;

import com.threeLine.atmcardverifier.model.audit.DateAudit;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "card_info")
public class CardInfo extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "card_number")
    private String cardNumber;

    private boolean success;

    @Column(name = "card_scheme")
    private String cardScheme;

    @Column(name = "card_type")
    private String cardType;

    private String bank;


}
