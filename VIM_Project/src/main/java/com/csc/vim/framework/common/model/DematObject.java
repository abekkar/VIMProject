package com.csc.vim.framework.common.model;


/**
 * The main purpose of this class to make Business Object benefits a rObjectID, dctmFolder and a parentId
 * DctmFolder is useful when creating the business object in the dctm repository
 * parentId is usefull when relation must be created between this object and another object
 * @since 1.0
 * @author syongwaiman2
 *
 */
public class DematObject{

	protected String rObjectId;				// r_object_id of the object in dctm
	protected String dctmFolder;			// fullPath folder containing this business object
	protected String parentId;				// parentLink of the object. For a supplier or a PO, for instace, this property refers to the invoice
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getrObjectId() {
		return rObjectId;
	}
	
	public void setrObjectId(String rObjectId) {
		this.rObjectId = rObjectId;
	}

	public String getDctmFolder() {
		return dctmFolder;
	}

	public void setDctmFolder(String dctmFolder) {
		this.dctmFolder = dctmFolder;
	}
	
}
