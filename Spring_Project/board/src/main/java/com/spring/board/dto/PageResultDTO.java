package com.spring.board.dto;


import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 페이징 처리 결과를 Page<Entity> 타입으로 반환한 후 자료구조에 담기
 * 다양한 곳에서 사용할 수 있기 위해 제네릭 타입 사용
 * Function의 역할은 엔티티 객체들을 DTO로 변환해주는 기능
 * */
@Data
public class PageResultDTO<DTO, EN> {

    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡ DTO LIST 시작 ㅡㅡㅡㅡㅡㅡㅡㅡ

    private List<DTO> dtoList;

    private int totalPage; //총 페이지 번호
    private int page; //현재 페이지 번호
    private int size; //목록 사이즈
    private int start, end; // 시작 페이지 번호, 끝 페이지 번호
    private boolean prev, next;  //이전, 다음
    private List<Integer> pageList; //페이지 번호 목록

    public PageResultDTO(Page<EN> result, Function<EN,DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber() + 1;  //paging이 0부터 시작하기 때문에 +1을 해주어 1부터 시작할 수 있도록 설정
        this.size = pageable.getPageSize();

        //일시적으로 설정한 끝 페이지
        int tempEnd = (int)(Math.ceil(page/10.0)) * 10;

        start = tempEnd -9;
        prev = start > 1;
        end = totalPage > tempEnd ? tempEnd : totalPage;
        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());
    }


}

/**PageResultDTO는 List<DTO>타입으로 DTO 객체들을 보관합니다. Page<Entity>의 내용물 중에서 엔티티 객체를 DTO로 변환하는 기능이 필요
 * 일반적으론 추상 클래스를 이용해서 처리해야하는 방식이지만, 매번 새로운 클래스가 필요한 단점이 있음
 * 엔티티 객체의 DTO 변환은 서비스 인터페이스에서 정의한 메서드(entityToDto())와 별도의 Function 객체로 만들어서 처리*/