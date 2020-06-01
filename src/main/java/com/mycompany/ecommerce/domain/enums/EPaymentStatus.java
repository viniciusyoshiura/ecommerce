package com.mycompany.ecommerce.domain.enums;

public enum EPaymentStatus {

	PENDING (1, "Pending"), 
	SETTLED (2, "Settled"), 
	CANCELED (3,"Canceled");
	
	private Integer code;
	private String description;
	
	private EPaymentStatus(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static EPaymentStatus toEnum(Integer code) {
		
		if(code == null) {
			return null;
		}
		
		for(EPaymentStatus ePaymentState : EPaymentStatus.values()) {
			
			if(code.equals(ePaymentState.getCode())) {
				return ePaymentState;
			}
			
		}
		
		throw new IllegalArgumentException("Invalid code: " + code);
		
	}
	
}
