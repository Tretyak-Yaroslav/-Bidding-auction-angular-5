package uk.co.afe.model.role;

import lombok.Data;
import lombok.ToString;
import uk.co.afe.model.client.Client;

import javax.persistence.*;

/**
 * Permission is a list of roles of client
 *
 * @author Sergey Teryoshin
 * 17.03.2018 20:28
 * @see Client
 * @see Role
 */
@Data
@ToString
@Entity
@Table(name = "tb_afe_client_role")
public class Permission {

    /**
     * Permission identifier
     */
    @Id
    @Column(name = "afe_client_role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientRoleId;

    /**
     * Client identifier
     *
     * @see Client
     */
    @Column(name = "client_id")
    private Long clientId;

    /**
     * List of roles
     *
     * @see Role
     */
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    public Permission() {
    }

    public Permission(Long clientId, Role role) {
        this.clientId = clientId;
        this.role = role;
    }
}
