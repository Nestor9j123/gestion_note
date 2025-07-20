package com.chance.coupchance.Repos;

import com.chance.coupchance.Entites.Bulletin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BulletinRepository extends JpaRepository<Bulletin, Long> {
    List<Bulletin> findByEleveId(Long eleveId);
    List<Bulletin> findByTrimestre(int trimestre);
    List<Bulletin> findByEleveIdAndTrimestre(Long eleveId, int trimestre);
}