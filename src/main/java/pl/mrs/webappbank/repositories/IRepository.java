package pl.mrs.webappbank.repositories;

import java.util.ArrayList;
import java.util.List;

public interface IRepository<T, U> {
    void add(T element);
    void remove(T element);
    List<T> findAll();
    int find(U identifier);
}
