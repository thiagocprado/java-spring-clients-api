package com.luizalabs.api.clients.common.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.luizalabs.api.clients.common.helper.JsonHelper;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Stream;

public class BaseMapper extends JsonHelper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public <D, M, C> List<D> serializeToDto(final M model, final Class<C> clazz) throws JsonProcessingException {
        return this.serializeToDto(Stream.of(model).toList(), clazz);
    }

    public <D, M, C> List<D> serializeToDto(final List<M> model, final Class<C> clazz) throws JsonProcessingException {
        return this.buildClassPojoList(model, clazz);
    }

    public <D, M> M deserializeToModel(final D dto, final Class<M> clazz) throws JsonProcessingException {
        return this.buildClassPojo(dto, clazz);
    }

    public <M, C> C modelMapper(final M model, final Class<C> clazz) {
        return modelMapper.map(model, clazz);
    }
}
