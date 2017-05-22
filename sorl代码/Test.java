package com.whty.cms.common.solr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;

public class Test {
	public static void main(String args[]){
		SolrServer solr = new HttpSolrServer("http://127.0.30.11:30002/solr/product");
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", "classificationIdList:jl11 AND productCode:PD0001875438");
		params.set("start", 0);
		params.set("rows", 1);
		List<ProductSolr> productList = null;
		SolrDocumentList docList = null;
		long num = 0;
		try {
			QueryResponse response = solr.query(params);
			//productList = response.getBeans(ProductSolr.class);
			docList = response.getResults();
		} catch(Exception ex) {
		}
		if(null != docList){
			num = docList.getNumFound();
			try {
				for(int i = 0; i < num; i=i+100){
					ModifiableSolrParams newParams = new ModifiableSolrParams();
					newParams.set("sort", "productCode desc");
					newParams.set("q", "classificationIdList:jl11 AND productCode:PD0001875438");
					newParams.set("start", i);
					newParams.set("rows", 100);
					QueryResponse response = solr.query(newParams);
					productList = response.getBeans(ProductSolr.class);
					//Map<String, ProductSolr> map = new HashMap<String, ProductSolr>();
					for(ProductSolr p : productList){
						String des = p.getProductDesc();
						if(StringUtils.isNotBlank(des) && des.contains(".")){
							des = des.substring(0, des.lastIndexOf("."));
							p.setProductName("2011年高一语文 1.1《沁园春 长沙》课件 人教版第一册");
						}
					}
					solr.addBeans(productList);
					solr.commit();
					System.out.println(i);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	//(productDesc:*.*.*) AND !(productName:*.*)
	//(productDesc:*.*.*) AND !(productName:*.*.*)
	//33315
	
	
}
