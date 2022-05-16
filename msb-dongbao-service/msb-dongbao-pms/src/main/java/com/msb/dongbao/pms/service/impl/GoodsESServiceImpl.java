package com.msb.dongbao.pms.service.impl;

import com.msb.dongbao.pms.db.dao.GoodESDao;
import com.msb.dongbao.pms.db.dao.SpecificationDao;
import com.msb.dongbao.pms.model.dto.GoodDTO;
import com.msb.dongbao.pms.model.dto.ProductSearchConditionDTO;
import com.msb.dongbao.pms.model.dto.ProductSearchESDTO;
import com.msb.dongbao.pms.service.IGoodsESService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;


@Service
@Slf4j
public class GoodsESServiceImpl implements IGoodsESService {

    @Autowired
    private GoodESDao goodESDao;
    @Autowired
    private SpecificationDao specificationDao;



    private QueryBuilder getQueryBuilder(ProductSearchESDTO productSearchESDTO){
        if (productSearchESDTO == null){
            return null;
        }
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        
        try {
            for (Field field : ProductSearchESDTO.class.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(productSearchESDTO);
                if (value == null){
                    continue;
                }
                if(value.getClass() == String.class && StringUtils.isEmpty((String)value)){
                    continue;
                }
                org.springframework.data.elasticsearch.annotations.Field annotation = field.getAnnotation(org.springframework.data.elasticsearch.annotations.Field.class);
                if(annotation != null){
                    String nameES = annotation.value();
                    FieldType type = annotation.type();
                    if(type.equals(FieldType.Long) || type.equals(FieldType.Keyword) || type.equals(FieldType.Boolean)){
                        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(nameES, value);
                        boolQueryBuilder.must(termQueryBuilder);
                    }
                    else if(type.equals(FieldType.Text)){
                        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(nameES, value);
                        boolQueryBuilder.must(matchQueryBuilder);
                    }
                    else if(type.equals(FieldType.Float)){
                        if (productSearchESDTO.getPriceLower() != null && productSearchESDTO.getPriceUpper() != null){
                            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(nameES)
                                    .from(productSearchESDTO.getPriceLower())
                                    .to(productSearchESDTO.getPriceUpper());
                            boolQueryBuilder.must(rangeQueryBuilder);
                        }
                    }
                    else if (type.equals(FieldType.Nested)){
                        if (!CollectionUtils.isEmpty(productSearchESDTO.getSpecOptions())){
                            for (String specOption  : productSearchESDTO.getSpecOptions()) {
                                TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(nameES, specOption);
                                boolQueryBuilder.must(termQueryBuilder);
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            return null;
        }
        return boolQueryBuilder;
    }

    private FieldSortBuilder getSortBuild(ProductSearchESDTO productSearchESDTO){
        if(productSearchESDTO.getSortField() == null){
            return null;
        }
        FieldSortBuilder fieldSortBuilder = null;
        try {
            Field sortField = ProductSearchESDTO.class.getDeclaredField(productSearchESDTO.getSortField());
            String fieldName = sortField.getAnnotation(org.springframework.data.elasticsearch.annotations.Field.class).value();
            fieldSortBuilder = SortBuilders.fieldSort(fieldName);
            SortOrder sort = SortOrder.DESC;
            if(productSearchESDTO.getSortDesc() != null && !productSearchESDTO.getSortDesc()){
                sort = SortOrder.ASC;
            }
            fieldSortBuilder.order(sort);
        } catch (NoSuchFieldException e) {
            log.error(" >>>>>>>>>没有字段 {}", productSearchESDTO.getSortField());
            return null;
        }
        return fieldSortBuilder;
    }

    @Override
    public Page<GoodDTO> pageQueryByCondition(ProductSearchESDTO productSearchESDTO) {
        Integer pageNo = 0;
        Integer pageSize = 8;
        return pageQueryByCondition(productSearchESDTO, pageNo, pageSize);
    }

    @Override
    public Page<GoodDTO> pageQueryByCondition(ProductSearchESDTO productSearchESDTO, Integer pageNo, Integer pageSize) {
        QueryBuilder queryBuilder = getQueryBuilder(productSearchESDTO);
        FieldSortBuilder sortBuild = getSortBuild(productSearchESDTO);
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder
                .withQuery(queryBuilder)
                .withPageable(PageRequest.of(pageNo, pageSize));
        if (sortBuild != null){
            nativeSearchQueryBuilder.withSort(sortBuild);
        }
        SearchQuery searchQuery = nativeSearchQueryBuilder.build();
        return goodESDao.search(searchQuery);
    }


    @Override
    public Page<GoodDTO> pageQueryByName(String name, Integer pageNo, Integer pageSize) {
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("productName", name);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().
                withQuery(matchQueryBuilder).
                withPageable(PageRequest.of(pageNo, pageSize)).
                build();
        return goodESDao.search(searchQuery);
    }



    @Override
    public List<ProductSearchConditionDTO> findSearchCondition(ProductSearchESDTO searchESDTO) throws NoSuchFieldException {
        QueryBuilder queryBuilder = getQueryBuilder(searchESDTO);
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(
                boolQuery()
                        .should(queryBuilder))
                        .scoreMode(FunctionScoreQuery.ScoreMode.FIRST).boostMode(CombineFunction.REPLACE);

        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(functionScoreQueryBuilder)
                .withPageable(PageRequest.of(0, 1)).withSort(SortBuilders.scoreSort()).build();
        //
        Page<GoodDTO> search = goodESDao.search(searchQuery);
        log.info("查询数据: " + search);
        List<GoodDTO> content = search.getContent();
        if(CollectionUtils.isEmpty(content)){
            // 返回空的条件
            return null;
        }
        List<ProductSearchConditionDTO> result = new ArrayList<>();
        for(GoodDTO goodDTO:content){
            Long categoryId = goodDTO.getRelCategory3Id();
            List<ProductSearchConditionDTO> condition = specificationDao.findWithOptionByCategoryId(categoryId);
            result.addAll(condition);
        }
        log.info("查询数据: " + result);
        return result;
    }

}
