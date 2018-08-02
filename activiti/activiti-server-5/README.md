[TOC]
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
打开 src\main\resources\static\editor-app\configuration\properties\form-properties-popup.html，查找 typeField, 在 <select></select> 中添加 <option>float</option>
```

2. 定义数据类型的解析类
```java
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
```java
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

# 自定义流程图片生成器，解决工作流连线不显示文字的问题
1. 重写ProcessDiagramCanvas
```java
/**
 * @author zhy
 * @date 2018/7/17 16:41
 * 自定义工作流连线的字体样式
 */
public class MyProcessDiagramCanvas extends DefaultProcessDiagramCanvas {
    public MyProcessDiagramCanvas(int width, int height, int minX, int minY, String imageType, String activityFontName, String labelFontName, String annotationFontName, ClassLoader customClassLoader) {
        super(width, height, minX, minY, imageType, activityFontName, labelFontName, annotationFontName, customClassLoader);
    }

    @Override
    public void initialize(String imageType) {
        // 父类代码
        
        //11号，加粗
        LABEL_FONT = new Font(this.labelFontName, 1, 11);
        //黑色
        LABEL_COLOR = new Color(0, 0, 0);
        
        // 父类代码
    }
}
```

2. 重写ProcessDiagramGenerator
```java
/**
 * @author zhy
 * @date 2018/7/17 16:41
 * 自定义流程图片生成器，解决工作流连线不显示文字的问题
 */
@Component
public class MyProcessDiagramGenerator extends DefaultProcessDiagramGenerator {
    @Override
    protected void drawActivity(DefaultProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel,
                                FlowNode flowNode, List<String> highLightedActivities, List<String> highLightedFlows, double scaleFactor) {
                // 父类代码
                
                // Draw sequenceflow label
                GraphicInfo labelGraphicInfo = bpmnModel.getLabelGraphicInfo(sequenceFlow.getId());
                if (labelGraphicInfo != null) {
                    processDiagramCanvas.drawLabel(sequenceFlow.getName(), labelGraphicInfo, false);
                } else {
                    GraphicInfo lineCenter = getLineCenter(graphicInfoList);
                    processDiagramCanvas.drawLabel(sequenceFlow.getName(), lineCenter, false);
                }
                
                // 父类代码
    }

    private static void drawHighLight(DefaultProcessDiagramCanvas processDiagramCanvas, GraphicInfo graphicInfo) {
        processDiagramCanvas.drawHighLight((int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(), (int) graphicInfo.getHeight());
    }

    @Override
    protected DefaultProcessDiagramCanvas generateProcessDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities, List<String> highLightedFlows, String activityFontName, String labelFontName, String annotationFontName, ClassLoader customClassLoader, double scaleFactor) {
        // 父类代码
    }

    protected static DefaultProcessDiagramCanvas initProcessDiagramCanvas(BpmnModel bpmnModel, String imageType, String activityFontName, String labelFontName, String annotationFontName, ClassLoader customClassLoader) {
        // 父类代码
        
        return new MyProcessDiagramCanvas((int) maxX + 10, (int) maxY + 10, (int) minX, (int) minY, imageType, activityFontName, labelFontName, annotationFontName, customClassLoader);
    }
}
```

# 打开activiti编辑器
http://localhost:8086/modeler.html?modelId=1
