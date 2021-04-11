package com.threeLine.atmcardverifier.repository;

import com.threeLine.atmcardverifier.model.CardInfo;
import com.threeLine.atmcardverifier.payload.CardInfoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardInfoRepository extends JpaRepository<CardInfo, Long> {

}
