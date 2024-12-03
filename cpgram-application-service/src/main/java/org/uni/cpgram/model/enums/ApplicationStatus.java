package org.uni.cpgram.model.enums;

import lombok.Getter;

@Getter
public enum ApplicationStatus {
	
	NEW("New");

	private final String displayName;

	ApplicationStatus(String displayName) {
		this.displayName = displayName;
	}

}
