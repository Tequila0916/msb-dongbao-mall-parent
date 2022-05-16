package com.msb.dongbao.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.pms.db.dao.CategoryDao;
import com.msb.dongbao.pms.model.dto.CategoryDTO;
import com.msb.dongbao.pms.model.dto.CategorySpecDTO;
import com.msb.dongbao.pms.model.entity.Category;
import com.msb.dongbao.pms.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 商品类目 服务实现类
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-08
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements ICategoryService {

    @Autowired
    private CategoryDao categoryDao;

    /**
     * 新增类目/规格/规格项
     * @param dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultWrapper addCategorySpec(CategorySpecDTO dto) {
        log.info("新增类目 入参:【{}】",new Gson().toJson(dto));
        //一级类目
        Category parentCate = new Category();
        parentCate.setParentId(0L);
        parentCate.setName(dto.getName());
        parentCate.setGmtCreate(System.currentTimeMillis());
        parentCate.setEnabled(0);
        categoryDao.insert(parentCate);
        insertCate(parentCate.getId(),dto.getChildCateLists());

        return ResultWrapper.getSuccessBuilder().msg("新增类目成功").build();
    }

    /**
     * 循环入库
     * */
    private void insertCate(Long id ,List<CategorySpecDTO> categorySpecList){
        if (categorySpecList == null){
            return ;
        }
        categorySpecList.stream().forEach(cateDto -> {
            Category childCate= new Category();
            childCate.setParentId(id);
            childCate.setName(cateDto.getName());
            childCate.setGmtCreate(System.currentTimeMillis());
            childCate.setEnabled(0);
            categoryDao.insert(childCate);
            insertCate(childCate.getId(),cateDto.getChildCateLists());
        });
    }



    /**
     * 查询类目列表
     */
    @Override
    public List<CategoryDTO> listCategory(int parentNum, int childNum) {
        QueryWrapper<Category> query = new QueryWrapper<Category>();
        query.lambda().eq(Category::getParentId,0).eq(Category::getEnabled,false).orderByAsc(Category::getSort);
        Page<Category> page = new  Page<Category>(1,parentNum);
        this.page(page,query);
        if(page.getRecords() == null && page.getRecords().isEmpty()){
            return null;
        }

        List<CategoryDTO> categoryDTOS = Lists.newArrayList();
        for (Category category : page.getRecords()) {
            CategoryDTO dto = new CategoryDTO();
            BeanCopier copy = BeanCopier.create(Category.class, CategoryDTO.class, false);
            copy.copy(category, dto, null);
            categoryDTOS.add(dto);
        }
        List<CategoryDTO> allCateTree = getAllCateTree(categoryDTOS,childNum);
        return allCateTree;
    }

    private List<CategoryDTO> getAllCateTree(List<CategoryDTO> list,int childNum){
        for (CategoryDTO DTO : list) {
            QueryWrapper<Category> query = new QueryWrapper<Category>();
            query.lambda().eq(Category::getParentId, DTO.getId()).eq(Category::getEnabled,false).orderByAsc(Category::getSort);
            Page<Category> page = new Page(0,childNum);
            this.page(page,query);

            if(page.getRecords() == null && page.getRecords().isEmpty()){
                continue;
            }
            List<CategoryDTO> childDTOS = Lists.newArrayList();
            for (Category category : page.getRecords()) {
                CategoryDTO child = new CategoryDTO();
                BeanCopier copy = BeanCopier.create(Category.class, CategoryDTO.class, false);
                copy.copy(category, child, null);
                childDTOS.add(child);
            }
            DTO.setList(childDTOS);
            getAllCateTree(childDTOS,childNum);
        }
        return list;
    }

}
