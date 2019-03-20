package uk.co.afe.model.role;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

/**
 * Role is a group for list of actions
 *
 * @author Sergey Teryoshin
 * 17.03.2018 20:26
 * @see Action
 */
@Data
@ToString
@Entity
@Table(name = "tb_afe_role")
public class Role {

    /**
     * Role identifier
     */
    @Id
    @Column(name = "afe_role_id")
    private Long roleId;

    /**
     * Role name
     */
    @Column(name = "name")
    private String name;

    /**
     * List of actions
     *
     * @see Action
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "tb_afe_role_action",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "action_id"))
    private List<Action> actions = Collections.emptyList();

    public Role() {
    }

    public Role(Long roleId, String name) {
        this.roleId = roleId;
        this.name = name;
    }
}
