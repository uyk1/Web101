package com.example.demo.dto;

import com.example.demo.model.TodoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {
	private String id;
	private String title;
	private boolean done;
	
	public TodoDTO(final TodoEntity entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.done = entity.isDone();
	}
	
	public static TodoEntity toEntity(final TodoDTO dto) {
		return TodoEntity.builder()
				.id(dto.getId())
				.title(dto.getTitle())
				.done(dto.isDone())
				.build();
	}
}
//여기 userId가 없는 것은, 이후 스프링 시큐리티를 이용해 인증을 구현할 것이기 때문이다.
//유저가 자기 아이디를 넘겨주지 않아도 인증이 가능
//userId는 애플리케이션과 데이터베이스에서 사용자를 구별하기 위한 고유 식별자로 사용하기 때문에
//숨길 수 있다면 숨기는 것이 보안상 알맞다.