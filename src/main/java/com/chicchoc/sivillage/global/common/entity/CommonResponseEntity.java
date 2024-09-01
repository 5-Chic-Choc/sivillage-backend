package com.chicchoc.sivillage.global.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommonResponseEntity<T> {

  private HttpStatus status;
  private String message;
  private T data;
}
