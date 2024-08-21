package com.andres.agricultura.v1.service;

import java.util.List;

public interface IService<E> {
    E save(E e);

    List<E> list();

    public E findById(Long id);

    public void delete(Long id);

    public E update(Long id,E e);

}
