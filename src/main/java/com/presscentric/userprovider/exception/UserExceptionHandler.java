package com.presscentric.userprovider.exception;

import com.netflix.graphql.types.errors.TypedGraphQLError;
import graphql.GraphQLError;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class UserExceptionHandler implements DataFetcherExceptionHandler {

	private static final String ARGUMENT_VALUES = "argumentValues";

	@Override
	public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(final DataFetcherExceptionHandlerParameters handlerParameters) {
		final Throwable exception = handlerParameters.getException();

		final GraphQLError graphqlError;
		if (exception instanceof NotFoundException || exception instanceof ConstraintViolationException) {
			final Map<String, Object> debugInfo = new HashMap<>();
			debugInfo.put(ARGUMENT_VALUES, handlerParameters.getArgumentValues());
			graphqlError = constructBadRequest(handlerParameters, debugInfo);
		} else {
			graphqlError = constructInternalServerError(handlerParameters);
		}
		final DataFetcherExceptionHandlerResult result = DataFetcherExceptionHandlerResult.newResult()
				.error(graphqlError)
				.build();

		return CompletableFuture.completedFuture(result);
	}

	private static GraphQLError constructBadRequest(final DataFetcherExceptionHandlerParameters handlerParameters, final Map<String, Object> debugInfo) {
		return TypedGraphQLError.newBadRequestBuilder()
				.message(handlerParameters.getException().getMessage())
				.debugInfo(debugInfo)
				.path(handlerParameters.getPath()).build();
	}

	private static GraphQLError constructInternalServerError(final DataFetcherExceptionHandlerParameters handlerParameters) {
		return TypedGraphQLError.newInternalErrorBuilder()
				.message(handlerParameters.getException().getMessage())
				.path(handlerParameters.getPath()).build();
	}
}
