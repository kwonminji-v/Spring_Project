package com.spring.board.dto;


import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** JPA Repository에서 페이지 처리 결과를 Page 타입으로 반환해주면 service에서 이를 처리하기 위해 만든 클래스
 * Page의 객체들을 DTO 객체로 변환해 자료구조로 담고, 화면출력에 필요한 페이지 정보들을 구성 */

@Data
public class PageResultDTO<DTO, EN> { //화면에서 필요한 결과는 PageResultDTO 클래스로 생성
    /** 다양한 곳에서 사용할 수 있도록 제네릭 타입을 이용해 DTO와 EN(entity)라는 타입으로 설정 */
    private List<DTO> dtoList;

    private int totalpage; //총 페이지 번호
    private int page;      //현재 페이지 번호
    private int size;      //목록 사이즈
    private int start,end;  //시작페이지, 끝 페이지 번호
    private boolean prev, next;  //이전과 다음 버튼
    private List<Integer> pageList;  //페이지 번호 목록


    /** Function<EN,DTO> fn : 엔티티 객체들을 DTO로 변환해주는 기능 */
    public PageResultDTO(Page<EN> result, Function<EN,DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalpage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {
            this.page = pageable.getPageNumber() +1;  //페이징의 시작 숫자가 0부터 시작하므로 1을 더하기
            this.page = pageable.getPageSize();

            //temp end page
            //끝 번호를 미리 계산하는 이유는 시작번호의 계산을 수월하게 하기 위함
            //↓ 끝 번호를 구하는 공식
            int tempEnd = (int)(Math.ceil(page / 10.0)) * 10;

            start = tempEnd - 9; //화면에 10페이지 씩 보여주려면 시작번호는 무조건 끝번호에서 9를 뺀 값
            prev = start > 1;
            end = totalpage > tempEnd ? tempEnd : totalpage;
            next = totalpage > tempEnd;
            pageList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());
    }

}
