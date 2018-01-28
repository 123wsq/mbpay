package com.tangdi.production.tdauth.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单树
 * @author luoyang 
 *
 */
public class Tree {

	@Override
	public String toString() {
		return "Tree [id=" + id + ", text=" + text + ", checked=" + checked
				+ ", menuParId=" + menuParId + ", url=" + url + ", attributes="
				+ attributes + ", children=" + children + "]";
	}
	private String id;
	private String text;
	private boolean checked;
	private String  menuParId;
	private String url;
    private Attributes attributes;
	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public boolean getChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public List <Tree> children = new ArrayList<Tree>();
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public List<Tree> getChildren() {
		return children;
	}
	public void setChildren(List<Tree> children) {
		this.children = children;
	}
	public String getMenuParId() {
		return menuParId;
	}
	public void setMenuParId(String menuParId) {
		this.menuParId = menuParId;
	} 
	
}
