package com.example.dev.domain.posts;

import com.example.dev.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @NoArgsConstructor: 기본 생성자 자동 추가 (public Posts(){})
 * @Entity: 테이블과 링크될 클래스임을 나타냅니다.
 * Entity 클래스에서는 절대 Setter 메소드를 만들지 않는다.
 * 대신 해당 필드의 값 변경이 필요하면 명확히 그 목적과 의도를 나타낼 수 있는 메소드를 추가해야 한다.
 */
@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

  @Id //해당 테이블의 PK 필드
  @GeneratedValue(strategy = GenerationType.IDENTITY) //PK의 생성 규칙
  private Long id;

  //@Column: 테이블의 컬럼 문자열의 경우 VARCHAR(255)가 기본값
  @Column(length = 500, nullable = false)
  private String title;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String content;

  private String author;

  //@Builder: 해당 클래스의 빌더 패턴 클래스를 생성
  @Builder
  public Posts(Long id, String title, String content, String author) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.author = author;
  }

  public void update(String title, String content) {
    this.title = title;
    this.content = content;
  }
}
