package com.csc.vim.framework.po.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csc.vim.framework.common.dao.DctmDao;
import com.csc.vim.framework.model.PurchaseOrder;
import com.csc.vim.framework.properties.DctmModelType;
import com.csc.vim.framework.util.DctmHelper;
import com.csc.vim.framework.util.DateUtils;
import com.documentum.fc.client.IDfPersistentObject;
import com.documentum.fc.client.IDfSysObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfId;
import com.documentum.fc.common.DfTime;

@Repository
public class PurchaseOrderDAODctm extends DctmDao<PurchaseOrder> implements IPurchaseOrderDAO{

	@Autowired
	private DctmHelper dctmInstance;
	
	@Autowired
	private DateUtils dateUtils;
	
	private static final String DATE_FORMAT					= "dd/MM/yyyy";
	private static final String PURCHASEORDER				= "purchase_order";
	private static final String NAMESPACE 					= "demat";
	public static final String PURCHASEORDER_TYPE 			=  NAMESPACE + "_" + PURCHASEORDER;
	
	private static final String PURCHASE_ORDER_DATE      	= "po_date";
	private static final String PURCHASE_ORDER_DOC_TYPE  	= "po_document_type";
	private static final String PURCHASE_ORDER_NUMBER    	= "po_number";
	private static final String PURCHASE_ORDER_NUMBER_POS	= "po_number_position";
	private static final String PO_SUPPLIER_NAME         	= "po_supplier_name";
	private static final String PO_SUPPLIER_NUMBER      	= "po_supplier_number";
	
	@Override
	public PurchaseOrder create(PurchaseOrder pPo)  throws DfException  {
	
			
		// Build the dctm object to persist in dctm
		IDfSysObject poDctm = (IDfSysObject) mySession.newObject(PURCHASEORDER_TYPE);
		updateDctmBeanWithJavaBean(poDctm, pPo);
		poDctm.link(pPo.getDctmFolder());
		
		// create in the Dctm repository
		poDctm.save();
		
		
		// Update he Java Po with its new r_object_id
		pPo.setrObjectId(poDctm.getObjectId().toString());
			

		
		return pPo;
	}

	
	/**
	 * Get in Dctm the PurchaseOrder Obbject based on its r_object_id
	 * 
	 * @author syongwaiman2
	 * @since 1.0
	 * @param pId	r_object_id  of the PurchaseOrder object
	 * @return The PurchaseOrder object in Dctm 
	 */
	@Override
	public PurchaseOrder getById(String pId) throws DfException {
		
		// retrived the object from Dctm
		IDfPersistentObject sysObject = mySession.getObject(new DfId(pId));
		
		// Create the Java Business Object from this Dctm object
		PurchaseOrder po = new PurchaseOrder();		
		updateJavaBeanWithDctmBean(po, sysObject);
		
		return po;
	}

	/**
	 * Get all the Purchase Order objects link to an object provided in input
	 * 
	 * @author syongwaiman2
	 * @since 1.0
	 * @param pParentId			r_object_id of the parent (for instance the r_object_id of an invoice if we want the its Po)
	 * @param ptransactionnal	If equals to false, the service will open/close a session
	 * @return List of Purchase Order child of the parent in input (the parent is most likely the invoice)
	 */
	@Override
	public Collection<PurchaseOrder> getChildrenOfInvoice(String pParentId)
			throws DfException {
		Collection<PurchaseOrder> result = new ArrayList<PurchaseOrder>();
		
		// Get the list of supplier children of this invoice (Actualla a lsit of r_object_id)
		Collection<String> listRObjectId = dctmHelper.getChild(mySession, pParentId, DctmModelType.RL_INVOICE_PO);
		
		// build the supplier object from the r_object_id
		Iterator<String> it = listRObjectId.iterator();
		while(it.hasNext()) {
			String id = it.next();
			PurchaseOrder po = getById(id);
			result.add(po);
		}
		
		return result;
	}


	/**
	 * @author syongwaiman2
	 * @since 1.0
	 * @param pJavaBean The Java Business Object to be updated with data retrieved from the repository
	 * @param pDctmBean The Dctm Object retrieved from repository
	 */
	@Override
	protected void updateJavaBeanWithDctmBean(PurchaseOrder pJavaBean,
			IDfPersistentObject pDctmBean) throws DfException {

		pJavaBean.setPoDate(pDctmBean.getTime(PURCHASE_ORDER_DATE).getDate());
		pJavaBean.setPoDocumentType(pDctmBean.getString(PURCHASE_ORDER_DOC_TYPE));
		pJavaBean.setPoNumber(pDctmBean.getString(PURCHASE_ORDER_NUMBER));
		pJavaBean.setPoNumberPosition(pDctmBean.getString(PURCHASE_ORDER_NUMBER_POS));
		pJavaBean.setSupplierName(pDctmBean.getString(PO_SUPPLIER_NAME));
		pJavaBean.setSupplierNumber(pDctmBean.getString(PO_SUPPLIER_NUMBER));
		
	}


	/**
	 * @author syongwaiman2
	 * @since 1.0
	 * @param pDctmBean The DCTM to be updated
	 * @param pJavaBean The Java Business Object that contains the value to be transfered to the dctm object
	 */
	@Override
	protected void updateDctmBeanWithJavaBean(IDfPersistentObject pDctmBean,
			PurchaseOrder pJavaBean) throws DfException {
		
		pDctmBean.setTime(PURCHASE_ORDER_DATE, new DfTime(dateUtils.dateToString(pJavaBean.getPoDate(), DATE_FORMAT),
														DATE_FORMAT));
		pDctmBean.setString(PURCHASE_ORDER_DOC_TYPE, pJavaBean.getPoDocumentType());
		pDctmBean.setString(PURCHASE_ORDER_NUMBER, pJavaBean.getPoNumber());
		pDctmBean.setString(PURCHASE_ORDER_NUMBER_POS, pJavaBean.getPoNumberPosition());
		pDctmBean.setString(PO_SUPPLIER_NAME, pJavaBean.getSupplierName());
		pDctmBean.setString(PO_SUPPLIER_NUMBER, pJavaBean.getSupplierNumber());
		
		
	}
	
}
