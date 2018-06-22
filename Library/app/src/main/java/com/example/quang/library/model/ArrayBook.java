package com.example.quang.library.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ArrayBook implements Serializable{
    private ArrayList<ItemBook> arrBook;

    public ArrayBook(ArrayList<ItemBook> arrBook) {
        this.arrBook = arrBook;
    }

    public ArrayList<ItemBook> getArrBook() {
        return arrBook;
    }

}
