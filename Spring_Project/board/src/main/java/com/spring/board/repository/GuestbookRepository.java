package com.spring.board.repository;

import com.spring.board.entity.Guestbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

//JPA Repository를 상속받음
public interface GuestbookRepository extends JpaRepository<Guestbook, Long>, QuerydslPredicateExecutor<Guestbook> {
}
