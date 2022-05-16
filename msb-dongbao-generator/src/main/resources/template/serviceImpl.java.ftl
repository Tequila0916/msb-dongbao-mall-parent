package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;

import com.msb.dongbao.common.base.dto.BaseReturnCode;
import com.msb.dongbao.common.base.dto.PageResult;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import ${package.DTO}.${entity}PageDTO;
import ${package.DTO}.${entity}DTO;
import ${package.VO}.${entity}VO;

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @version V1.0
 * @contact
 * @date ${date}
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    @Override
    public ResultWrapper<PageResult<${entity}VO>> pageList${entity}(${entity}PageDTO pageDTO){
        // TODO 上下文获取信息,进行业务逻辑处理
        return BaseReturnCode.SUCCESS.toData(null);
    }

    @Override
    public ResultWrapper<Integer> doSave(${entity}DTO dto){
        // TODO 上下文获取信息,进行业务逻辑处理
        return BaseReturnCode.SUCCESS.toData(null);
    }

    @Override
    public ResultWrapper<Integer> doDelete(Integer id){
        // TODO 上下文获取信息,进行业务逻辑处理
        return BaseReturnCode.SUCCESS.toData(null);
    }


    @Override
    public ResultWrapper<Integer> doUpdate(${entity}DTO dto){
        // TODO 上下文获取信息,进行业务逻辑处理
        return BaseReturnCode.SUCCESS.toData(null);
    }

    @Override
    public ResultWrapper<${entity}VO> detail(Integer id){
        // TODO 上下文获取信息,进行业务逻辑处理
        return BaseReturnCode.SUCCESS.toData(null);
    }
}
</#if>
