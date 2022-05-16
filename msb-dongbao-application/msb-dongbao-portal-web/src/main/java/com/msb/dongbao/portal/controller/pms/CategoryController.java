package com.msb.dongbao.portal.controller.pms;

import com.msb.dongbao.common.base.constant.CommonConstants;
import com.msb.dongbao.common.base.constant.RedisConstants;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.pms.model.dto.CategoryDTO;
import com.msb.dongbao.pms.model.dto.CategorySpecDTO;
import com.msb.dongbao.pms.service.ICategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 商品类目 前端控制器
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-08
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Api(value = "商品类目 控制器", tags = "商品类目 控制器")
@RestController
@RequestMapping("/pms/category")
@Validated
@Slf4j
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @ApiOperation(value = "新增类目/规格/规格项")
    @PostMapping("/addCategorySpec")
    public ResultWrapper addCategorySpec(@RequestBody CategorySpecDTO dto) {
        return categoryService.addCategorySpec(dto);
    }

    @ApiOperation(value = "查询类目列表")
    @PostMapping("/list")
    public ResultWrapper<List<CategoryDTO>> listCategory() {
        ValueOperations<String, List<CategoryDTO>> valueOperations = redisTemplate.opsForValue();
        List<CategoryDTO> category = valueOperations.get(RedisConstants.CATEGORY_KEY);
        if (category != null && !category.isEmpty()) {
            log.info("从redis中获取类目列表");
            return ResultWrapper.getSuccessBuilder().data(category).build();
        } else {
            RLock lock = redissonClient.getLock(RedisConstants.CATEGORY_LOCK_KEY);
            List<CategoryDTO> categoryList = categoryService.listCategory(CommonConstants.CATEGORY_PARENT_SIZE,
                    CommonConstants.CATEGORY_CHILD_SIZE);
            try {
                lock.lock();
                valueOperations.set(RedisConstants.CATEGORY_KEY, categoryList);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            return ResultWrapper.getSuccessBuilder().data(category).build();
        }
    }
}
