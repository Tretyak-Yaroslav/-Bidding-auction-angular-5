package uk.co.afe.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * Client email verification token
 *
 * @author Sergey Teryoshin
 * 31.03.2018 18:23
 */
@Data
@ToString
@Entity
@Table(name = "tb_afe_email_token")
public class EmailToken {

    /**
     * Identifier of verification
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "afe_email_token_id")
    private Long emailTokenId;
    /**
     * Identifier of client
     */
    @Column(name = "client_id")
    private Long clientId;
    /**
     * Verification token
     */
    @Column(name = "token")
    private String token;
}
