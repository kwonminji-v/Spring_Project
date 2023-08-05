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

/**
 * 조회 조건이 복잡한 화면은 Querydsl을 이용헤 조건에 맞는 쿼리를 동적으로 쉽게 생성
 *
 * Querydsl을 사용하면 비슷한 쿼리를 재활용 할 수 있습니다. Querydsl을 사용하기 위해서는 Qdomain을 생성해야 합니다.
 * target/generated-sources 폴더에 QItem 클래스를 생성 후 Querydsl을 사용
 * Entity 클래스들을 계속 생성 및 변경하였으므로 QDomain을 생성하기 위해서 메이븐의 컴파일 명령을 실행합니다.
 *
 * 복잡한 조회 조건에 대해 동적 쿼리를 생성하려면 1.의존성을 추가해주고 2. 코드 생성 설정을 진행합니다.
 * Querydsl을 사용하기 위해서는 먼저 프로젝트에 Querydsl 라이브러리를 추가합니다.
 * maven-apt-plugin을 설정하면 됩니다.
 *
 * 페이징 기능을 통해 일정 개수의 상품만 불러오며, 선택한 상품 상세 페이지로 이동할 수 있는 기능까지 구현합니다.
 *
 * 의존성을 추가한 후 코드생성 설정을 만들어 놓고 다음 과정을 진행하면 됩니다.
 * Querydsl을 Spring Data Jpa와 함께 사용하기 위해서는 사용자 정의 리포지토리를 정의해야 합니다. 이 과정은 3단계로 나눌 수 있습니다.
 * 1. 사용자 정의 인터페이스 작성 ... 사용자 정의 리포지토리 인터페이스를 만듭니다.
 * 2. 사용자 정의 인터페이스 구현 ... 사용자 정의 리포지토리 인터페이스의 구현 클래스를 작성합니다.
 * 3. Spring Data JPA 리포지토리와 연결 ... Spring Data Jpa 리포지토리에서 사용자 정의 리포지토리 인터페이스를 상속받아야 합니다.
 * */