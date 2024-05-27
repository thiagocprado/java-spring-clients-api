package com.luizalabs.api.clients.common.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.luizalabs.api.clients.common.helper.JsonHelper;

import java.util.List;

public class BaseMapper extends JsonHelper {
    public <D, M, C> List<D> serializeToDto(final List<M> model, final Class<C> clazz) throws JsonProcessingException {
        return this.buildClassPojoList(model, clazz);
    }

    public <D, M> M deserializeToModel(final D dto, final Class<M> clazz) throws JsonProcessingException {
        return this.buildClassPojo(dto, clazz);
    }
}
