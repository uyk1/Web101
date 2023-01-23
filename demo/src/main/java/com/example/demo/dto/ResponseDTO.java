package com.example.demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO<T> {
	private String error;
	private List<T> data;
}
//TodoDTO 뿐만 아니라 이후 다른 모델의 DTO도 ResponseDTO를 이욯해 리턴할 수 있도록 자바  Generic을 이용.
//또한 이 프로젝트의 경우 리스트를 반환하는 경우가 많으므로 데이터를 리스트로 반환하도록 함.