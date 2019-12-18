package com.example.myapp.myapp.ui.recycler;

public interface AdapterCallback<Data> {


    void update(Data data, NiceRecyclerAdapter.NiceRecyclerHolder<Data> holder);


}
