package com.example.demo.entity;

import java.util.Map;

public class UserProfile {

	    private int id;
	    private String name;
	    private String alternativeText;
	    private String caption;
	    private int width;
	    private int height;
	    private Map<String, FormatInfo> formats;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAlternativeText() {
			return alternativeText;
		}
		public void setAlternativeText(String alternativeText) {
			this.alternativeText = alternativeText;
		}
		public String getCaption() {
			return caption;
		}
		public void setCaption(String caption) {
			this.caption = caption;
		}
		public int getWidth() {
			return width;
		}
		public void setWidth(int width) {
			this.width = width;
		}
		public int getHeight() {
			return height;
		}
		public void setHeight(int height) {
			this.height = height;
		}
		public Map<String, FormatInfo> getFormats() {
			return formats;
		}
		public void setFormats(Map<String, FormatInfo> formats) {
			this.formats = formats;
		}
}
