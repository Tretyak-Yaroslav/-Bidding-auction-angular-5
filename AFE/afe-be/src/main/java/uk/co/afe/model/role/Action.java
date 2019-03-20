package uk.co.afe.model.role;

import lombok.Data;
import lombok.ToString;
import uk.co.afe.model.client.Client;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Actions is a permission to call some service
 *
 * @author Sergey Teryoshin
 * 17.03.2018 20:29
 * @see Client
 */
@Data
@ToString
@Entity
@Table(name = "tb_afe_action")
public class Action {

    /**
     * Action identifier
     */
    @Id
    @Column(name = "afe_action_id")
    private Long actionId;

    /**
     * Action name
     */
    @Column(name = "name")
    private String name;
}
