package com.spring.board.service;

import com.spring.board.dto.GuestbookDTO;
import com.spring.board.entity.Guestbook;

//service계층에서는 DTO를 이용해 필요한 내용을 전달받고 반환하도록 처리
public interface GuestbookService {

    Long register(GuestbookDTO dto);

    /**service에서는 파라미터를 DTO 타입으로 받기 때문에 JPA로 처리하기 위해서는 Entity 타입의 객체로 변환해야 하는 작업이 반드시 필요
     * java 8 버전부터 인터페이스의 실제 내용을 가지는 코드는 default라는 키워드로 생성할 수 있다
     * -> 실제 코드를 인터페이스에 선언할 수 있다 => 추상클래스를 생략하는것이 가능해 졌다*/
    default Guestbook dtoToEntity(GuestbookDTO dto) {
        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();

        return entity;
    }
}
