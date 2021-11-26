package com.gmail.pavlovsv93.Stack;

import java.util.EmptyStackException;

public class MyStack<T> {
    private int size;
    private T[] stack;
    private final int DEFAULT_CAPACITY = 20;

    public MyStack(int size) throws IllegalAccessException {
        if (size <= 0) {
            throw new IllegalAccessException();
        }
        this.stack = (T[]) new Object[size];
    }

    public MyStack() {
        this.stack = (T[]) new Object[DEFAULT_CAPACITY];
    }

    // Проверка на пустоту стека
    public boolean isEmpty() {
        return (this.size == 0);
    }

    // Проверка на заполненовть стека
    public boolean isFull() {
        return (this.size == stack.length);
    }

    //Добавление элемента в стек
    public void push(T i) {
        if (isFull()) {
            throw new StackOverflowError();
        }
        this.stack[size] = i;
        size++;
    }

    //Удаление элемента из стека
    public T pop() {
        T temp = peek();
        stack[size] = null;
        size--;
        return temp;
    }

    //Получение элемента из стека
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return this.stack[--size];
    }

    public int size() {
        return size;
    }

    public void clean() {
        while (size > 0) {
            pop();
        }
        size = 0;
    }
}
