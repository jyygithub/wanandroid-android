package com.jiangyy.wanandroid.adapter;

import com.jiangyy.wanandroid.ResourceTable;
import com.jiangyy.wanandroid.entity.Article;
import ohos.agp.components.*;
import ohos.app.Context;

import java.util.List;

public class ArticleItemProvider extends BaseItemProvider {

    private Context context;
    private List<Article> articles;

    public ArticleItemProvider(Context context,List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public int getCount() {
        return articles == null ? 0 : articles.size();
    }

    @Override
    public Object getItem(int position) {
        if (articles != null && position >= 0 && position < articles.size()) {
            return articles.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Component getComponent(int position, Component convertComponent, ComponentContainer componentContainer) {
        final Component cpt;
        if (convertComponent == null) {
            cpt = LayoutScatter.getInstance(context).parse(ResourceTable.Layout_item_article, null, false);
        } else {
            cpt = convertComponent;
        }
        Article article = articles.get(position);
        Text text = (Text) cpt.findComponentById(ResourceTable.Id_articleTitle);
        text.setText(article.getTitle());
        return cpt;
    }
}
