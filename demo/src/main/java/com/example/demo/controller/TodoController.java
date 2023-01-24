package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;

@RestController
@RequestMapping("todo")
public class TodoController {
	//testTodo 메서드 작성
	
	@Autowired
	private TodoService service;
	
	@GetMapping("/test")
	public ResponseEntity<?> testTodo() {
		String str = service.testService();
		List<String> list = new ArrayList<>();
		list.add(str);
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
		try {
			String temporaryUserId = "temporary-user";//temporary user id.
			//TodoEntity로 변환
			TodoEntity entity = TodoDTO.toEntity(dto);
			//id를 null로 초기화. 생성 당시에는 id가 없어야 함.
			entity.setId(null);
			//임시 유저 아이디 설정. 아직 인증과 인가 기능이 없으므로, 한 유저만 로그인 없이 사용 가능한 상태.
			entity.setUserId(temporaryUserId);
			//서비스를 이용해 Todo 엔티티를 생성
			List<TodoEntity> entities = service.create(entity);
			//자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			//변환된 TOdoDTO 리스트를 이용해 ResponseDTO를 초기화 한다.
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			//ResponseDTO를 리턴
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			// TODO: handle exception
			//예외가 나는 경우 dto 대신 error에 메시지를 넣어 리턴
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping
	public ResponseEntity<?> retrieveTodoList() {
		String temporaryUserId = "temporary-user"; //temporary user id.
		
		//서비스 메서드의 retrieve 메서드를 사용해 Todo 리스트를 가져온다
		List<TodoEntity> entities = service.retrieve(temporaryUserId);
		//자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		//변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		return ResponseEntity.ok().body(response);
	}
	
	@PutMapping
	public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto) {
		String temporaryUserId = "temporary-user"; //temporary user id
		//dto를 entity로 변환
		TodoEntity entity = TodoDTO.toEntity(dto);
		//id를 temporaryUserId로 초기화. 이 부분은 4장 인증과 인가에서 수정.
		entity.setUserId(temporaryUserId);
		//서비스를 이용해 entity를 업데이트
		List<TodoEntity> entities = service.update(entity);
		//자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		//변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		//ResponbseDTO를 리턴
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto) {
		try {
			String temporaryUserId = "temporary-user"; //temporary user id
			//TodoEntity로 반환
			TodoEntity entity = TodoDTO.toEntity(dto);
			//임시 유저 아이디를 설정. 4장 인증과 인가에서 수정 예정.
			entity.setUserId(temporaryUserId);
			//서비스를 이용해 entity를 삭제
			List<TodoEntity> entities = service.delete(entity);
			//자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			//변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			//ResponseDTO를 리턴
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			// TODO: handle exception
			//예외가 발생하는 경우 dto 대신 error에 메시지를 넣어 리턴
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
}




























