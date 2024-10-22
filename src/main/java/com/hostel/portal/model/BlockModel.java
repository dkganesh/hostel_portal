package com.hostel.portal.model;

import lombok.Builder;
import lombok.Data;

@Data
public class BlockModel {
    private Integer staff;
    private Integer blockName;
    private Integer id;

    public BlockModel(Integer x,Integer y){
        this.blockName=x;
        this.id=y;
    }
}
