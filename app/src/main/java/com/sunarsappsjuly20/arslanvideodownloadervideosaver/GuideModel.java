package com.sunarsappsjuly20.arslanvideodownloadervideosaver;

public class GuideModel {

    private String num;
    private String tv_for_display;
    private int sliders_drawables;

    public GuideModel(String num, String tv_for_display, int sliders_drawables) {
        this.num = num;
        this.tv_for_display = tv_for_display;
        this.sliders_drawables = sliders_drawables;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTv_for_display() {
        return tv_for_display;
    }

    public void setTv_for_display(String tv_for_display) {
        this.tv_for_display = tv_for_display;
    }

    public int getSliders_drawables() {
        return sliders_drawables;
    }

    public void setSliders_drawables(int sliders_drawables) {
        this.sliders_drawables = sliders_drawables;
    }
}
