package com.qf.service;

import com.qf.entity.TProduct;
import com.qf.entityvo.TproductVO;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.List;

/**
 * @Author Wang
 * @Date 2020/3/10
 */
public interface IGoodsService {

    //根据关键字到solr库中查询所有信息
    public List<TproductVO> findAllByKeyWords(String keyword) throws IOException, SolrServerException;
    //初始化数据库信息到solr库中
    public void initDataBaseToSolr();

}
