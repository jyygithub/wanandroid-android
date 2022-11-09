package com.jiangyy.wanandroid.adapter;

import com.google.gson.Gson;
import com.jiangyy.wanandroid.entity.Article;
import com.jiangyy.wanandroid.entity.ArticlesBean;
import ohos.agp.components.*;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.Context;
import ohos.eventhandler.Logger;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainPageProvider extends PageSliderProvider {

    private Context context;

    public MainPageProvider(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object createPageInContainer(ComponentContainer componentContainer, int i) {

        ListContainer listContainer = new ListContainer(null);
        listContainer.setLayoutConfig(
                new StackLayout.LayoutConfig(
                        ComponentContainer.LayoutConfig.MATCH_PARENT,
                        ComponentContainer.LayoutConfig.MATCH_PARENT
                ));

        List<Article> articles = new ArrayList<>();
        articles.add(new Article("Test1"));
        articles.add(new Article("Test2"));
        articles.add(new Article("Test3"));

        ArticleItemProvider provider = new ArticleItemProvider(context, articles);
        listContainer.setItemProvider(provider);

        componentContainer.addComponent(listContainer);

        return listContainer;
    }

    @Override
    public void destroyPageFromContainer(ComponentContainer componentContainer, int i, Object o) {
        componentContainer.removeComponent((Component) o);
    }

    @Override
    public boolean isPageMatchToObject(Component component, Object o) {

        return false;
    }

}
