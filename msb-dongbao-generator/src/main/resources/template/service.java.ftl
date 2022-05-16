package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};

import com.msb.dongbao.common.base.dto.PageResult;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import ${package.DTO}.${entity}PageDTO;
import ${package.DTO}.${entity}DTO;
import ${package.VO}.${entity}VO;

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @version V1.0
 * @contact
 * @date ${date}
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

  ResultWrapper<PageResult<${entity}VO>> pageList${entity}(${entity}PageDTO pageDTO);

  ResultWrapper<Integer> doSave(${entity}DTO dto);

  ResultWrapper<Integer> doDelete(Integer id);

  ResultWrapper<Integer> doUpdate(${entity}DTO dto);

  ResultWrapper<${entity}VO> detail(Integer id);
}
</#if>
