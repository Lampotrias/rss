package com.example.rss.domain.exception;

/**
 * Interface to represent a wrapper around an {@link java.lang.Exception} to manage errors.
 */

public interface IErrorBundle {
	Exception getException();

	String getErrorMessage();
}
