## 代码生成器说明文档

> 生成某一张表的代码步骤说明

1. 如果生成的该类属于某一模块下，需要修改声明对应的模块名.

```
    // 指定生成的模块名
    pc.setModuleName("test");
```

2. 指定生成的表

```
    // 需要生成的表
    strategy.setInclude(new String[] {"test"});
        
```

3. 运行 MpGenerator 即可


> 待优化部分

