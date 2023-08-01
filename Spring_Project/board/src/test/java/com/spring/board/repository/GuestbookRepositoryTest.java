package com.spring.board.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.spring.board.entity.Guestbook;
import com.spring.board.entity.QGuestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class GuestbookRepositoryTest {

    @Autowired private GuestbookRepository guestbookRepository;

    @Test //더미데이터 만들기
    public void insertDumies() {
        IntStream.rangeClosed(1,300).forEach(i -> {
            Guestbook guestbook = Guestbook.builder()
                    .title("제목은..." + i)
                    .content("내용은..." + i)
                    .writer("작성자" + (i % 10))
                    .build();
            guestbookRepository.save(guestbook);
        });
    }

    @Test /**modDate는 최종 수정 시간이 반영 되기 때문에 특정한 엔티티를 수정한 후 save() 했을 경우 동작 (정상 동작 확인 test)*/
    public void updateTest() {
        //300번 선택 (존재하는 번호로 test)
        Optional<Guestbook> result = guestbookRepository.findById(300L);

        //존재하는 번호로 테스트 , isPresent() : !=null과 같은 의미
        if (result.isPresent()) {
            Guestbook guestbook = result.get();

            guestbook.changeTitle("변경된 제목...");
            guestbook.changeContent("변경된 내용...");

            guestbookRepository.save(guestbook);
        }
    }

    /**
     * 1. 제목/내용/작성자와 같이 단 하나의 항목으로 검색하는 경우
     * 2. 제목 + 내용 / 내용 + 작성자 / 제목 + 작성자와 같이 2개의 항목으로 검색하는 경우
     * 3. 제목 + 내용 + 작성자와 같이 3개의 항목으로 검색하는 경우*/
    @Test //Querydsl 테스트 - 단일 항목 테스트 (title에 1이 포함된 내용을 출력)
    public void testQuery1() {

        //페이징 처리
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        //findAll -> JpaRepository but querydsl1 -> QuerydslPredicateExecutor 사용

        //Predicate
        //Q도메인 클래스를 사용하면 엔티티 클래스에 선언된 필드를 변수로 사용할 수 있음
        QGuestbook qGuestbook = QGuestbook.guestbook;
        String keyword = "1";

        //BooleanBuilder : 쿼리의 where 문에 들어가는 조건을 넣어주는 컨테이너
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        //title like %1% 쿼리를 코드로 구현
        BooleanExpression expression = qGuestbook.title.contains(keyword);

        //만들어진 쿼리 조건들의 결합
        booleanBuilder.and(expression);

        //BooleanBuilder는 guestbookRepository에 추가된 QuerydslPredicateExecutor 인터페이스의 findAll()을 사용
        Page<Guestbook> result = guestbookRepository.findAll(booleanBuilder, pageable);
        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }

    @Test
    public void testQuery2() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook;
        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression exTitle = qGuestbook.title.contains(keyword);
        BooleanExpression exContent = qGuestbook.content.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent); //exTitle과 exContent라는 BooleanExpression을 결합하는 부분
        builder.and(exAll); //위에서 결합한 exAll을 BooleanBuilder에 추가
        builder.and(qGuestbook.gno.gt(0L)); //이후 gno가 0보다 크다라는 조건을 추가한 부분에서 차이가 발생

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }
}

/** Querydsl 사용법
 * BooleanBuilder를 생성
 * 조건에 맞는 구문은 Querydsl에서 사용하는 Predicate 타입의 함수를 생성
 * BooleanBuilder에 작성된 Predicate를 추가하고 실행*/