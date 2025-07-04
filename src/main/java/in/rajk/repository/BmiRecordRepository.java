package in.rajk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.rajk.model.BmiRecord;

@Repository
public interface BmiRecordRepository extends JpaRepository<BmiRecord, Long> {
    List<BmiRecord> findByMemberIdOrderByDateDesc(Long memberId);

}
