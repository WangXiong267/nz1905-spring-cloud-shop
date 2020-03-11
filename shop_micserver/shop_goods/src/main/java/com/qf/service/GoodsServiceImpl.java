package com.qf.service;

import com.qf.entity.TProduct;
import com.qf.entityvo.TproductVO;
import com.qf.mapper.TProductVOMapper;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author Wang
 * @Date 2020/3/10
 */
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private SolrClient solrClient;
    @Override
    public List<TproductVO> findAllByKeyWords(String keyword) throws IOException, SolrServerException {
        //      1）先设置查询参数==>封装一个SolrQuery对象
        SolrQuery query = new SolrQuery();
        //查询的关键字、复制域、分页信息
        query.setQuery(keyword);
        query.set("df","pdesc");
        query.set("df","pname");
        query.setStart(0);
        query.setRows(10);
        // 2）提交给solrClient执行查询并得到结果集
        QueryResponse response = solrClient.query(query);
        // 3）解析结果集
        List<TproductVO> products = new ArrayList<TproductVO>();
        SolrDocumentList results = response.getResults();
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

        for (SolrDocument doc : results) {

            TproductVO product = new TproductVO();
            //id  t_product_name  t_product_pdesc t_product_sale_price t_product_pimage
            Long pid = Long.parseLong((String)doc.getFieldValue("pid"));
            product.setPid(pid);

            //=======高亮的商品名称的封装==========
            Map<String, List<String>> stringListMap = highlighting.get(pid);
            List<String> product_names = stringListMap.get("pname");
            String t_product_name = product_names.get(0);
            product.setPname(t_product_name);
            //=======高亮end=======
            String pname = (String) doc.getFieldValue("pname");
            product.setPname(pname);
            String pdesc = (String) doc.getFieldValue("pdesc");
            product.setPdesc(pdesc);
            Double price = (Double) doc.getFieldValue("price");
            product.setPrice(Math.round(price));
            String pimage = (String) doc.getFieldValue("pimage");
            product.setPimage(pimage);

            products.add(product);
        }

        System.out.println(products);

        return products;
    }


    @Autowired
    private TProductVOMapper mapper;
    @Override
    public void initDataBaseToSolr() {
            //从数据库中获取数据
            List<TproductVO> products = mapper.selectAll();

            //存放所有的doc的集合
            List<SolrInputDocument> docs = new ArrayList<>();

            //遍历products集合，将每一个product对象封装成一个SolrInputDocument对象
            for (TproductVO product : products) {
                SolrInputDocument doc = new SolrInputDocument();
                doc.setField("pid",product.getId());
                doc.setField("pname",product.getPrice());
                doc.setField("price",product.getPrice());
                doc.setField("pimage",product.getPimage());
                doc.setField("pdesc",product.getPdesc());
                //将doc对象存入到集合中
                docs.add(doc);
            }
            //将该集合添加到solr库中
            try {
                solrClient.add(docs);
                solrClient.commit();//不要忘了
            } catch (SolrServerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

}
