package com._9._ss23.utill;

import com._9._ss23.product.domain.Product;

import java.util.List;

public interface TextLog<T> {

    public String getContents(List<T> contentItems);
}
