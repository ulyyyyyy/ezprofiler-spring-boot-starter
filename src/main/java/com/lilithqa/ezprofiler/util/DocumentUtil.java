package com.lilithqa.ezprofiler.util;

import org.bson.Document;


/**
 * @apiNote Mongo储存Document工具类
 * @author 黑黑
 * @date 2021-03-09
 */
public class DocumentUtil {
    public static <T> Document toDocument(T object){
        return Document.parse(object.toString());
    }


}


