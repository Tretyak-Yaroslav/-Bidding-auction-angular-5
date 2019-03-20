package uk.co.afe.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * Entity that contains info about P2P/CF platform
 *
 * @author Sergey Teryoshin
 * 20.03.2018 14:48
 */
@Data
@ToString
@Entity
@Table(name = "tb_afe_platform")
@ApiModel(description = "Entity that contains info about P2P/CF platform")
public class Platform {

    /**
     * Platform identifier
     */
    @ApiModelProperty("Platform identifier")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "afe_platform_id")
    private Long platformId;
    /**
     * Platform name
     */
    @ApiModelProperty("Platform name")
    @Column(name = "name")
    private String name;
    /**
     * Flag of platform hidden for user
     */
    @ApiModelProperty("Flag of platform hidden for user")
    @Column(name = "hidden")
    private Boolean hidden;

}
