package com.coinwork.base.boardFile.model;

import com.coinwork.base.board.model.BoardItemVo;

import java.util.ArrayList;
import java.util.List;

public class ResBoardFile {

    List<BoardItemVo> list = new ArrayList<>();


    public List<BoardItemVo> getList() {
        return this.list;
    }

    public void setList(List<BoardItemVo> list) {
        this.list = list;
    }
}
