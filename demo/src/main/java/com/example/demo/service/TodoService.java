package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TodoService {
	@Autowired
	public TodoRepository repository;
	
	public String testService() {
		TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
		repository.save(entity);
		TodoEntity savedEntity = repository.findById(entity.getId()).get();
		return savedEntity.getTitle();
	}
	
	public List<TodoEntity> create(final TodoEntity entity) {
		//검증
		validate(entity);
		
		repository.save(entity);
		
		log.info("Entity Id : {} is saved.", entity.getId());
		
		return repository.findByUserId(entity.getUserId());
	}
	
	public List<TodoEntity> retrieve(final String userId) {
		return repository.findByUserId(userId);
	}
	
	public List<TodoEntity> update(final TodoEntity entity) {
		//저장할 엔티티가 유효한지 확인한다.
		validate(entity);
		//넘겨받은 엔티티 id를 이용해 TodoEntity를 가져온다. 존재하지 않는 엔티티는 업데이트할 수 없기 때문.
		final Optional<TodoEntity> original = repository.findById(entity.getId());
		
		original.ifPresent(todo -> {
			//반환된 TodoEntity가 존재하면 값을 새 entity의 값으로 덮어 씌운다.
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());
			//데이터베이스에서 새 값을 저장
			repository.save(todo);
		});
//		if(original.isPresent()) {
//			final TodoEntity todo = original.get();
//			todo.setTitle(entity.getTitle());
//			todo.setDone(entity.isDone());
//			repository.save(todo);
//		} //위와 동일한 의미를 가짐.
		// 유저의 모든 Todo 리스트를 리턴
		return retrieve(entity.getUserId());
	}
	
	public List<TodoEntity> delete(final TodoEntity entity) {
		//저장할 엔티티가 유효한지 확인
		validate(entity);
		
		try {
			//엔티티를 삭제
			repository.delete(entity);
		} catch (Exception e) {
			// TODO: handle exception
			//exception 발생 시 id와 exception을 로깅
			log.error("error deleting entity", entity.getId(), e);
			//컨트롤러로 exception을 날린다.
			//데이터베이스 내부 로직을 캡슐화하기 위해 e를 리턴하지 않고 새 exception 오브젝트를 리턴
			throw new RuntimeException("error deleting entity " + entity.getId());
		}
		//새 Todo 리스트를 가져와 리턴
		return retrieve(entity.getUserId());
	}
	
	//리팩토링한 메서드 //검증
	private void validate(final TodoEntity entity) {
		if(entity == null) {
			log.warn("Entity cannot be null.");
			throw new RuntimeException("Entity cannot be null.");
		}
		
		if(entity.getUserId() == null) {
			log.warn("Unknown user.");
			throw new RuntimeException("Unknown user.");
		}
	}
}
