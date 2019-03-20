package uk.co.afe.model.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import uk.co.afe.model.questionaries.Questionnaire;
import uk.co.afe.persistance.mapping.ClientStatusConverter;
import uk.co.afe.persistance.mapping.QuestionnaireConverter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity that contains information about authorization and client owner
 *
 * @author Sergey Teryoshin
 * 13.03.2018 11:55
 */
@Data
@ToString
@Entity
@Table(name = "tb_afe_client")
@ApiModel(description = "Entity that contains information about authorization and client owner")
public class Client {

    /**
     * Unique client identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "afe_client_id")
    @ApiModelProperty("Unique client identifier")
    private Long clientId;

    /**
     * Full name of client owner
     */
    @Column(name = "name")
    @ApiModelProperty("Full name of client owner")
    private String name;

    /**
     * Address of client owner
     */
    @Column(name = "address")
    @ApiModelProperty("Address of client owner")
    private String address;

    /**
     * International postcode
     */
    @Column(name = "postcode")
    @ApiModelProperty("International postcode")
    private String postcode;

    /**
     * Email of client owner
     */
    @Column(name = "email")
    @ApiModelProperty("Email of client owner")
    private String email;

    /**
     * Phone number of client owner
     */
    @Column(name = "phone_number")
    @ApiModelProperty("Phone number of client owner")
    private String phoneNumber;

    /**
     * Hash code of password of client
     */
    @Column(name = "password")
    @ApiModelProperty("Hash code of password of client")
    @JsonIgnore
    private String password;

    /**
     * Salt of password hash code
     */
    @Column(name = "salt")
    @ApiModelProperty("Salt of password hash code")
    @JsonIgnore
    private String salt;

    /**
     * Risk rank of client
     */
    @Column(name = "atr")
    @ApiModelProperty("Risk rank of client")
    private Short atr;

    /**
     * Birthday of client
     */
    @Column(name = "birthday")
    @ApiModelProperty("Birthday of client")
    private LocalDateTime birthday;

    /**
     * Questionnaire answers
     */
    @Convert(converter = QuestionnaireConverter.class)
    @Column(name = "questionnaire")
    @ApiModelProperty("Questionnaire answers")
    private Questionnaire questionnaire;

    /**
     * Hidden flag
     */
    @Column(name = "hidden")
    @ApiModelProperty("Hidden flag")
    private Boolean hidden;

    /**
     * Client status
     */
    @Column(name = "status")
    @ApiModelProperty("Client status")
    @Convert(converter = ClientStatusConverter.class)
    private ClientStatus status;

    /**
     * Avatar of client
     */
    @Column(name = "avatar")
    @ApiModelProperty("Avatar of client")
    private String avatar;

}
