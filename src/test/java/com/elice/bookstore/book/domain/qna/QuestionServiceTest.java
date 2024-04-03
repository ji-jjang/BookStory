package com.elice.bookstore.book.domain.qna;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.book.domain.BookRepository;
import com.elice.bookstore.book.domain.qna.dto.QuestionRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;


    @DisplayName("질문 생성 성공")
    @Test
    void createQuestion() {
        // Given
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);

        QuestionRequest request = new QuestionRequest();
        request.setBookId(bookId);
        request.setContent("Test question content");
        request.setCreatedBy("Test user");

        Question question = request.toEntity(book);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(questionRepository.save(any(Question.class))).thenReturn(question);

        // When
        Question result = questionService.createQuestion(request);

        // Then
        assertThat(result.getBook().getId()).isEqualTo(bookId);
        assertThat(result.getContent()).isEqualTo(request.getContent());
        assertThat(result.getCreatedBy()).isEqualTo(request.getCreatedBy());
        assertThat(result.getStatus()).isEqualTo(QuestionStatus.ANSWER_PENDING);
    }

    @DisplayName("질문 전체 찾기 성공")
    @Test
    public void testFindAllQuestion() {
        // Given
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);

        QuestionRequest request = new QuestionRequest();
        request.setBookId(bookId);
        request.setContent("Test question content");
        request.setCreatedBy("Test user");

        QuestionRequest request2 = new QuestionRequest();
        request2.setBookId(bookId);
        request2.setContent("Test question content2");
        request2.setCreatedBy("Test user2");

        Question question = request.toEntity(book);
        Question question2 = request2.toEntity(book);

        // when
        when(questionRepository.findAll()).thenReturn(List.of(question, question2));
        List<Question> result = questionRepository.findAll();

        // then
        assertThat(result).hasSize(2); // 결과 리스트의 사이즈 검증
        assertThat(result).extracting(Question::getContent).containsExactlyInAnyOrder("Test question content", "Test question content2");
    }
}