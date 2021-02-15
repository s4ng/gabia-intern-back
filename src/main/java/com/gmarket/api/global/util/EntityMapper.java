package com.gmarket.api.global.util;

import lombok.Builder;

import java.util.Optional;

public interface EntityMapper <D, E> {

    E toEntity(D dto);
    D toDto(E entity);
    Iterable<E> toEntity(Iterable<D> dto);
    Iterable<D> toDto(Iterable<E> entity);

//    Optional<D> toDto(Optional<E> entity);
//    Optional<E> toEntity(Optional<D> dto);
}
