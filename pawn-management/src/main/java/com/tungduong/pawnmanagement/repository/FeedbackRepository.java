package com.tungduong.pawnmanagement.repository;

import com.tungduong.pawnmanagement.model.Feedback;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tungduong.pawnmanagement.model.enums.FeedBackStatus;

import java.util.Optional;

public interface FeedbackRepository  extends JpaRepository<Feedback, Long> , JpaSpecificationExecutor<Feedback> {

    Optional<Feedback> findByIdAndRecordStatusNotAndStatusNot(Long id, RecordStatus recordStatus, FeedBackStatus feedBackStatus);
}
