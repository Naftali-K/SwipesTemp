package com.nk.swipestemp.viewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nk.swipestemp.R;
import com.nk.swipestemp.models.Item;

import java.util.ArrayList;
import java.util.List;

public class SwipeRefreshRecyclerViewViewModel extends ViewModel {

    private Context context;

    private MutableLiveData<List<Item>> mutableListItem = new MutableLiveData<>();

    public void init(Context context) {
        this.context = context;
    }

    public LiveData<List<Item>> getListItem() {
        return mutableListItem;
    }

    public List<Item> createItemList(int numberItems) {
        List<Item> itemList = new ArrayList<>();

        for (int i = 0; i < numberItems; i++) {
            Item item = new Item(i, "Title " + i, "Description " + i, R.drawable.icon_android);
            itemList.add(item);
        }

        mutableListItem.setValue(itemList);

        return itemList;
    }
}
