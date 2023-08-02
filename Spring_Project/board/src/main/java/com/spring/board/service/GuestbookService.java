package com.spring.board.service;

import com.spring.board.dto.GuestbookDTO;
import com.spring.board.dto.PageRequestDTO;
import com.spring.board.dto.PageResultDTO;
import com.spring.board.entity.Guestbook;

/**
 * GuestbookDTO를 이용해서 필요한 내용을 전달받고, 반환하도록 처리하는데 Guestbookservice 인터페이스와 GuestbookServiceImpl 클래스를 작성*/
public interface GuestbookService {

    /**service에서는 파라미터를 DTO 타입으로 받기 때문에 JPA로 처리하기 위해서는 Entity 타입의 객체로 변환해야 하는 작업이 반드시 필요
     * java 8 버전부터 인터페이스의 실제 내용을 가지는 코드는 default라는 키워드로 생성할 수 있다
     * -> 실제 코드를 인터페이스에 선언할 수 있다 => 추상클래스를 생략하는것이 가능해 졌다*/

    Long register(GuestbookDTO dto);
    PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO);
    /**default 기능을 활용, 구현클래스에서 동작 할 수 있는 dtoToEntity 메서드 구성*/
    default Guestbook dtoToEntity(GuestbookDTO dto) {
        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();

        return entity;
    }

    default GuestbookDTO entityToDto(Guestbook entity) {

        GuestbookDTO dto = GuestbookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

        return dto;
    }


}
