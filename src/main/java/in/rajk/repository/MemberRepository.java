package in.rajk.repository;

import in.rajk.model.Member;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
    Member findByPhone(String phone);
    Optional<Member> findByEmail(String email);
    // 🔍 Custom search query (name or phone)
    @Query("SELECT m FROM Member m WHERE LOWER(m.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR m.phone LIKE CONCAT('%', :keyword, '%') OR LOWER(m.email) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Member> searchByNameOrPhone(String keyword);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    List<Member> findByExpiryDateBetween(LocalDate start, LocalDate end);

    List<Member> findByExpiryDateBefore(LocalDate date);
    @Query("SELECT COUNT(m) FROM Member m")
    long countAllMembers();
}
