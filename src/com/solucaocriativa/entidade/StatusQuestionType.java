package com.solucaocriativa.entidade;

import java.io.IOException;

import com.solucaocriativa.util.MessageUtil;

public enum StatusQuestionType {

    YES('S', "statusQuestionType.yes"), NO('N', "statusQuestionType.no");

    private char character;
    private String status;

    private StatusQuestionType(char character, String status) {
	this.character = character;
	this.status = status;
    }

    public String getDescription() throws IOException {
	return MessageUtil.getMessage(status, new Object());
    }

    public char toValue() {
	return character;
    }

    public static StatusQuestionType fromValue(char character) {
	for (StatusQuestionType statusQuestionType : values()) {
	    if (character == statusQuestionType.toValue()) {
		return statusQuestionType;
	    }
	}
	return null;
    }
}
