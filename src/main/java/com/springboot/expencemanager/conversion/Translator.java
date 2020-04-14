package com.springboot.expencemanager.conversion;

public interface Translator<E, V> {

    public E translateToEntity(E entity, V dto);

    public V translateToDTO(E entity, V dto);

}
