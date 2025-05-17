package studio.thinkground.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studio.thinkground.project.entity.MemberEntity;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByMemberEmail(String email);
}
