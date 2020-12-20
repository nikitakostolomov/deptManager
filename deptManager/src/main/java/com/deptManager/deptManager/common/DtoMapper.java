package com.deptManager.deptManager.common;


import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class DtoMapper {

    private static final ModelMapper modelMapper = new ModelMapper();


    private DtoMapper() {
    }

    public static <T, E> List<T> convertList(List<E> list, Class<T> type) {
        return list
                .stream()
                .map(entity -> modelMapper.map(entity, type))
                .collect(Collectors.toList());
    }

//    public static <T, E> ListForDto<T> convertList(ListForDto<E> list, Class<T> type) {
//        return new ListForDto<>(list.getCount(), list.getResults()
//                .stream()
//                .map(entity -> modelMapper.map(entity, type))
//                .collect(Collectors.toList()));
//    }

    public static <T, E> T convertToClass(E entity, Class<T> type) {
        return modelMapper.map(entity, type);
    }


}
