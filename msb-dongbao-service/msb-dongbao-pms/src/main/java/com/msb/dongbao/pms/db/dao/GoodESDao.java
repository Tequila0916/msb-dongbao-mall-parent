package com.msb.dongbao.pms.db.dao;

import com.msb.dongbao.pms.model.dto.GoodDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodESDao extends ElasticsearchRepository<GoodDTO,Long> {

}


