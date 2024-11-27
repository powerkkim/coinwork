package com.coinwork.base.board.model;

import java.util.ArrayList;
import java.util.List;

public class ResBoard {

    List<BoardItemVo> list = new ArrayList<>();


    public List<BoardItemVo> getList() {
        return this.list;
    }

    public void setList(List<BoardItemVo> list) {
        this.list = list;
    }
}
