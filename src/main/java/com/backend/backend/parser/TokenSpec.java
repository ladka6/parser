package com.backend.backend.parser;

import java.util.regex.Pattern;
import com.backend.backend.parser.types.TypeEnum;

public class TokenSpec {
	public final Pattern pattern;
	public final TypeEnum type;

	public TokenSpec(String regex, TypeEnum type) {
		this.pattern = Pattern.compile(regex);
		this.type = type;
	}

	public Pattern getPattern() {
		return this.pattern;
	}

	public TypeEnum getType() {
		return this.type;
	}

	@Override
	public String toString() {
		return "{" +
				" pattern:'" + getPattern() + "'" +
				", type='" + getType() + "'" +
				"}";
	}

}
