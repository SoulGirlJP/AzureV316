package client.Commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import constants.Data.AccountType;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
	String[] names();
	String parameters();
	AccountType requiredType();
}