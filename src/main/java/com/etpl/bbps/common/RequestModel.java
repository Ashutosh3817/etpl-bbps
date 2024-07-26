package com.etpl.bbps.common;

public class RequestModel {


	private Object data;
	
	
	public RequestModel(){
		
	}
	
	public RequestModel(Object ent){

		this.data=ent;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
	
	
}
