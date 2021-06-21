package com.NeverStarve.member.response;

import java.util.Map;

public class MemberResponse {
	
	private boolean validated;
	private Map<String, String> errorMessages;
	
	public boolean isValidated() {
		return validated;
	}
	
	public void setValidated(boolean validated) {
		this.validated = validated;
	}
	
	public void setErrorMessages(Map<String, String> errorMessages) {
		this.errorMessages= errorMessages;
	}
	
	public Map<String, String> getErrorMessages(){
		return errorMessages;
		
	}
}
