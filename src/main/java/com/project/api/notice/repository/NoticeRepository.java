package com.project.api.notice.repository;

import com.project.api.notice.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> , QuerydslPredicateExecutor {

    public List<Notice> findByTitle(String title);
}
