package com.spring.board.dto;


import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/** JPA Repository에서 페이지 처리 결과를 Page 타입으로 반환해주면 service에서 이를 처리하기 위해 만든 클래스
 * Page의 객체들을 DTO 객체로 변환해 자료구조로 담고, 화면출력에 필요한 페이지 정보들을 구성 */

@Data
public class PageResultDTO<DTO, EN> { //화면에서 필요한 결과는 PageResultDTO 클래스로 생성
    /** 다양한 곳에서 사용할 수 있도록 제네릭 타입을 이용해 DTO와 EN(entity)라는 타입으로 설정 */
    private List<DTO> dtoList;

    /** Function<EN,DTO> fn : 엔티티 객체들을 DTO로 변환해주는 기능 */
    public PageResultDTO(Page<EN> result, Function<EN,DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());
    }

}
