package org.siaron.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.siaron.jpa.enums.GenderEnum;

import javax.persistence.*;

/**
 * @author xielongwang
 * @create 2018-11-189:07 PM
 * @description
 */
@Data
@Entity
@Table(name = "user", schema = "example_jpa",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"ssn"})},
        indexes = {@Index(name = "user_user_name_index", columnList = "userName"),
                @Index(name = "user_ssn_index", columnList = "ssn")})
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    private Long id;

    private String ssn;

    private String userName;

    private String passWord;

    @Enumerated(EnumType.ORDINAL)
    private GenderEnum gender = GenderEnum.UN_KNOWN;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private TenantEntity tenantId;

    public UserEntity(Long id, String ssn, String passWord, String userName) {
        this.id = id;
        this.ssn = ssn;
        this.passWord = passWord;
        this.userName = userName;
    }

    public UserEntity(String ssn, String passWord, String userName) {
        this.ssn = ssn;
        this.passWord = passWord;
        this.userName = userName;
    }

}