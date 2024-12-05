package backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long profileId;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "real_name")
    private String realName;

    @Column(name = "bio")
    private String bio;

    @Column(name = "location")
    private String location;

    @Column(name = "link_website")
    private String linkWebsite;

    @Column(name = "avatar")
    private String avatar;


    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;
}
