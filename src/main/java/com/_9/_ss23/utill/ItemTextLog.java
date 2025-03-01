package com._9._ss23.utill;

import com._9._ss23.product.domain.Product;

import java.util.List;

public abstract class ItemTextLog<T> implements TextLog<T> {

    public abstract String getTitle();
    public abstract String getContent(T content);

    @Override
    public String getContents(List<T> contentItems) {
        StringBuilder contents = new StringBuilder();
        String title = getTitle();

        System.out.print(title + "\n");

        contentItems.stream().forEach(c->{
            String content = getContent(c);
            contents.append(content + "\n");
        });
        return contents.toString();
    }
}
