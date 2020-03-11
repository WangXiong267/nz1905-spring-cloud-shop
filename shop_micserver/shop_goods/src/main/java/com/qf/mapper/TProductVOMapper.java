package com.qf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entityvo.TproductVO;

import java.util.List;

/**
 * @Author Wang
 * @Date 2020/3/10
 */
public interface TProductVOMapper extends BaseMapper<TproductVO> {

    public List<TproductVO> selectAll();
}
