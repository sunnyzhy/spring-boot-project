package com.zhy.activitiserver.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProcessModel {
    private String id;
    private String resourceId;
    private Stencilset stencilset;
    private Properties properties;
    private List<ChildShapes> childShapes;

    @Data
    class Stencilset {
        private String namespace = "http://b3mn.org/stencilset/bpmn2.0#";
    }

    @Data
    class Properties{
        private String process_id;
        private String name;
    }

    @Data
    class ChildShapes {
        private Bounds bounds;
        private List<ChildShapes1> childShapes;
        private List<Dockers> dockers;
        private List<Outgoing> outgoing;
        private Stencil stencil;
        @Data
        class Bounds{
            private LowerRight lowerRight;
            private UpperLeft upperLeft;
            @Data
            class LowerRight{
                private Integer x= 130;
                private Integer y = 193;
            }
            @Data
            class UpperLeft{
                private Integer x= 100;
                private Integer y = 163;
            }

            public Bounds(){
                lowerRight = new LowerRight();
                upperLeft = new UpperLeft();
            }
        }

        @Data
        class ChildShapes1{

        }

        @Data
        class Dockers {

        }

        @Data
        class Outgoing{

        }

        private String resourceId = "startEvent1";
        @Data
        class Stencil{
            private String id = "StartNoneEvent";
        }

        public ChildShapes(){
            bounds = new Bounds();
            childShapes = new ArrayList<>();
            dockers = new ArrayList<>();
            outgoing = new ArrayList<>();
            stencil = new Stencil();
        }
    }

    public ProcessModel(String process_id, String name){
        id = "canvas";
        resourceId = "canvas";
        stencilset = new Stencilset();
        properties = new Properties();
        properties.setName(name);
        properties.setProcess_id(process_id);
        childShapes = new ArrayList<>();
        childShapes.add(new ChildShapes());
    }
}
