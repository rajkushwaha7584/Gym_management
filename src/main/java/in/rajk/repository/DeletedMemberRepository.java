package in.rajk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import in.rajk.model.DeletedMember;

public interface DeletedMemberRepository extends JpaRepository<DeletedMember, Long> {
}
