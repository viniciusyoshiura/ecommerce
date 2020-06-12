package com.mycompany.ecommerce.domain.enums;

public enum EProfile {

	ADMIN (1, "ROLE_ADMIN"), 
	CLIENT (2, "ROLE_CLIENT");
	
	private Integer code;
	private String description;
	
	private EProfile(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static EProfile toEnum(Integer code) {
		
		if(code == null) {
			return null;
		}
		
		for(EProfile eProfile : EProfile.values()) {
			
			if(code.equals(eProfile.getCode())) {
				return eProfile;
			}
			
		}
		
		throw new IllegalArgumentException("Invalid code: " + code);
		
	}
	
}
