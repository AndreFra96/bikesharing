/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andreabrioschi.bikesharing.database;

import java.util.List;

/**
 *
 * @author andreabrioschi
 */
//public interface Dao<T> {
//    public List<T> getAll();
//    public T getById(final int id);
//    public T add(T t);
//}

public interface Dao<T>{
    public abstract List<T> getAll();
    public abstract T getById(final int id);
    public abstract T add(T t);
    public abstract void delete(final T t);
}
