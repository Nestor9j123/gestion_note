package com.chance.coupchance.Repos;

import com.chance.coupchance.Entites.SavedState;
import com.chance.coupchance.Entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SavedStateRepository extends JpaRepository<SavedState, Long> {
    List<SavedState> findByUser(User user);
    
    @Query("SELECT s FROM SavedState s WHERE s.isShared = true ORDER BY s.lastModified DESC")
    List<SavedState> findAllSharedStates();
    
    List<SavedState> findByUserAndIsShared(User user, boolean isShared);
}