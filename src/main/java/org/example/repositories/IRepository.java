package org.example.repositories;

import java.util.ArrayList;

public interface IRepository<T, U> {
    void add(T element);
    void remove(int index);
    ArrayList<T> getList();
    int find(U identifier);
}
