package com.jiangyy.wanandroid.slice;

import com.jiangyy.wanandroid.adapter.MainPageProvider;
import com.jiangyy.wanandroid.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.PageSlider;

public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        initPageSlider();

    }

    private void initPageSlider() {
        PageSlider pageSlider = (PageSlider) findComponentById(ResourceTable.Id_pageSlider);
        pageSlider.setProvider(new MainPageProvider(this));
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
