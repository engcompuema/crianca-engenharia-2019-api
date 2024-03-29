package br.uema.dto;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {

	private T data;

	private List<String> errors;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<String> getErrors() {
		if (this.errors == null) {
			this.errors = new ArrayList<>();
		}

		return errors;
	}
	
	public void addError(String error) {
		if (this.errors == null) {
			this.errors = new ArrayList<>();
		}
		
		this.errors.add(error);
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
