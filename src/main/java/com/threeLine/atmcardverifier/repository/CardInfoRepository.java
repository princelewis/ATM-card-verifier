package com.threeLine.atmcardverifier.repository;

import com.threeLine.atmcardverifier.model.CardInfo;
import com.threeLine.atmcardverifier.model.CardNumAndCount;
import com.threeLine.atmcardverifier.payload.CardInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardInfoRepository extends JpaRepository<CardInfo, Long> {

//    @Query("SELECT new com.threeLine.atmcardverifier.model.CardNumAndCount( c.cardNumber, count(c.success)) FROM CardInfo c " +
//            "WHERE c.success = true GROUP BY c.cardNumber ORDER BY COUNT (c.success) DESC ")
//    Page<CardNumAndCount> getSortedCardCount(Pageable pageable);

    @Query("SELECT new com.threeLine.atmcardverifier.model.CardNumAndCount( c.cardNumber , COUNT (c.success))  FROM CardInfo  c WHERE c.success = true " +
            "GROUP BY c.cardNumber ORDER BY COUNT (c.success) DESC ")
    Page<CardNumAndCount> getSortedCardCount(Pageable pageable);
}
