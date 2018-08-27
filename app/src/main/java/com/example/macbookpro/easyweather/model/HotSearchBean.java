package com.example.macbookpro.easyweather.model;

import java.util.List;

public class HotSearchBean {

    public String er_msg;
    public List<Response> data;
    public class Response{
        public String  rank;
        public String  word;
    }

}

