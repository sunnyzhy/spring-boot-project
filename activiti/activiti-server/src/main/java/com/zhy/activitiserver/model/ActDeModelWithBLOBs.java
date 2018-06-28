package com.zhy.activitiserver.model;

public class ActDeModelWithBLOBs extends ActDeModel {
    private String modelEditorJson;

    private byte[] thumbnail;

    public String getModelEditorJson() {
        return modelEditorJson;
    }

    public void setModelEditorJson(String modelEditorJson) {
        this.modelEditorJson = modelEditorJson == null ? null : modelEditorJson.trim();
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }
}