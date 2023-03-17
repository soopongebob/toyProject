package toyproject.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toyproject.community.domain.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserIdAndPassword(String userId, String Password);
    User findByUserIdx(Long userIdx);
    User findByUserId(String userId);
    User findByUserName(String userName);
}
