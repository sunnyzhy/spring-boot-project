# 集成activiti编辑器
```
main
|_ java
|_ _ com.zhy.activitiserver5
|_ _ _ activiti
|_ resources
|_ _ static
|_ _ _ diagram-viewer
|_ _ _ editor-app
|_ _ _ favicon.ico
|_ _ _ modeler.html
|_ _ stencilset.json
```

# 添加自定义的表单数据类型
1. 在编辑器中添加自定义的数据类型
```
打开 src\main\resources\static\editor-app\configuration\properties\form-properties-popup.html，查找 typeField, 在<select></select>中添加<option>float</option>
```

2. 定义数据类型的解析类
```
public class FloatFormType extends AbstractFormType {
    @Override
    public Object convertFormValueToModelValue(String s) {
        return Float.valueOf(s);
    }

    @Override
    public String convertModelValueToFormValue(Object o) {
        return o == null ? "" : o.toString();
    }

    @Override
    public String getName() {
        return "float";
    }
}
```

3. 把自定义的表单数据类型注册到流程引擎
```
@Bean
public BeanPostProcessor activitiConfigurer() {
    return new BeanPostProcessor() {

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            if (bean instanceof SpringProcessEngineConfiguration) {
                List<AbstractFormType> customFormTypes = Arrays.<AbstractFormType>asList(new FloatFormType());
                ((SpringProcessEngineConfiguration)bean).setCustomFormTypes(customFormTypes);
            }
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            return bean;
        }           
    };

}
```

# 打开activiti编辑器
http://localhost:8086/modeler.html?modelId=1
