package com.po;;

public class Menu extends BasePO{
	
	private Integer menuId;
	
	private String menuName;
	
	private Integer parentId;
	
	private String icon;
	
	private String link;
	
	
	public Integer getMenuId() {
		return menuId;
	}
	
	public String getMenuName() {
		return menuName;
	}
	
	public Integer getParentId() {
		return parentId;
	}
	
	public String getIcon() {
		return icon;
	}
	
	public String getLink() {
		return link;
	}
	
	
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public String toString(){
		return "Menu";
	}
}
