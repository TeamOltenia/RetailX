package com.fmi.demo.exposition.ICommand;


public interface ICommand <T> {
    String save(T body , String username);
    String update(T body, String id ,String username);
    void delete(String id , String username);
}
