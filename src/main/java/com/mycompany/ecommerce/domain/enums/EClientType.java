package com.mycompany.ecommerce.domain.enums;

public enum EClientType {

	PHYSICALPERSON (1, "Physical Person"),
	LEGALPERSON (2, "Legal Person");
	
	private Integer code;
	private String description;
	
	private EClientType(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static EClientType toEnum(Integer code) {
		
		if(code == null) {
			return null;
		}
		
		for(EClientType eClientType : EClientType.values()) {
			
			if(code.equals(eClientType.getCode())) {
				return eClientType;
			}
			
		}
		
		throw new IllegalArgumentException("Invalid code: " + code);
		
	}
	
}
