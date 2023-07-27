package com.spring.board.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 화면에서 전달되는 목록과 관련된 데이터에 대한 DTO를 PageRequestDTO라는 이름으로 생성
 * 목록 페이지를 요청할 때  사용하는 데이터를 재사용하기 쉽게 만듬
 * -> 파라미터을 DTO로 선언하고 나중에 재 사용하는 용도*/
@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO { /** 목적 : JPA 쪽에서 사용하는 Pageable 타입의 객체를 생성 */

    //화면에서 전달되는 page, size 라는 파라미터 선언
    private int page;
    private int size;

    public PageRequestDTO() {
        //페이지네이션은 기본값을 가지는 것이 좋기 때문에 1 , 10의 값을 지정
        this.page = 1;
        this.size = 10;
    }

    public Pageable getPageable(Sort sort) {

        //보통 페이지의 번호가 0부터 시작하니 1페이지의 경우 0이 될 수 있도록 page -1로 작성
        //정렬은 다양한 상황에서 불러올 수 있도록 별도의 파라미터로 받을 수 있게 설계
        return PageRequest.of(page - 1, size, sort);
    }
}
