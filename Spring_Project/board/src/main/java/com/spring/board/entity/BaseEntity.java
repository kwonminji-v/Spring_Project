package com.spring.board.entity;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass //테이블로 생성되지 않도록 해주는 어노테이션
@EntityListeners(value = {AuditingEntityListener.class})
//AuditingEntityListener : JPA 내부에서 엔티티 객체가 생성/변경 되는 것을 감지하는 역할
@Getter
abstract class BaseEntity {

    @CreatedDate //엔티티의 생성시간을 처리
    @Column(name="regdate", updatable = false) // update=false :객체를 DB에 반영할 때 regdate 컬럼 값은 변경되지 않음
    private LocalDateTime regDate;

    @LastModifiedDate //엔티티의 최종 수정 시간을 자동으로 처리
    @Column(name ="moddate")
    private LocalDateTime modDate;
}

/** JPA에서는 사용하는 엔티티 객체들은 영속성 컨텍스트라는 곳에서 관리되는 객체입니다.
 * 이 객체들이 변경되면 결과적으로 데이터 베이스에 이를 반영하는 방식입니다.
 * Querydsl을 이용하면 복잡한 조합을 이용하는 경우의 수가 많은 상황에서 동적으로 쿼리를 생성해서 처리
 * 작성된 엔티티 클래스를 그대로 이용하는 것이 아닌 Q도메인을 이용해야 함
 * */