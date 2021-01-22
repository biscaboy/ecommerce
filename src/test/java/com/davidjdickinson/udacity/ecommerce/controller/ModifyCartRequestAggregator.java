package com.davidjdickinson.udacity.ecommerce.controller;

import com.davidjdickinson.udacity.ecommerce.model.requests.ModifyCartRequest;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

public class ModifyCartRequestAggregator implements ArgumentsAggregator {
    @Override
    public ModifyCartRequest aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername(argumentsAccessor.getString(0));
        request.setItemId(argumentsAccessor.getLong(1).longValue());
        request.setQuantity(argumentsAccessor.getInteger(2).intValue());
        return request;
    }
}
