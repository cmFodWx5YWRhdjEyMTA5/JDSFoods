package com.jdsfoods.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalePayment {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sale_id")
    @Expose
    private String saleId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("product_code")
    @Expose
    private String productCode;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_type")
    @Expose
    private String productType;
    @SerializedName("option_id")
    @Expose
    private Object optionId;
    @SerializedName("net_unit_price")
    @Expose
    private String netUnitPrice;
    @SerializedName("unit_price")
    @Expose
    private String unitPrice;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("warehouse_id")
    @Expose
    private Object warehouseId;
    @SerializedName("item_tax")
    @Expose
    private String itemTax;
    @SerializedName("tax_rate_id")
    @Expose
    private String taxRateId;
    @SerializedName("tax")
    @Expose
    private String tax;
    @SerializedName("discount")
    @Expose
    private Object discount;
    @SerializedName("item_discount")
    @Expose
    private Object itemDiscount;
    @SerializedName("subtotal")
    @Expose
    private String subtotal;
    @SerializedName("serial_no")
    @Expose
    private Object serialNo;
    @SerializedName("real_unit_price")
    @Expose
    private Object realUnitPrice;
    @SerializedName("sale_item_id")
    @Expose
    private Object saleItemId;
    @SerializedName("product_unit_id")
    @Expose
    private String productUnitId;
    @SerializedName("product_unit_code")
    @Expose
    private Object productUnitCode;
    @SerializedName("unit_quantity")
    @Expose
    private String unitQuantity;
    @SerializedName("comment")
    @Expose
    private Object comment;
    @SerializedName("purchase_price")
    @Expose
    private Object purchasePrice;
    @SerializedName("total_purchase_price")
    @Expose
    private Object totalPurchasePrice;
    @SerializedName("profit")
    @Expose
    private Object profit;
    @SerializedName("packing_quantity")
    @Expose
    private String packingQuantity;
    @SerializedName("single_quantity")
    @Expose
    private Object singleQuantity;
    @SerializedName("position_no")
    @Expose
    private Object positionNo;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("no_of_items_in_packing")
    @Expose
    private String noOfItemsInPacking;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Object getOptionId() {
        return optionId;
    }

    public void setOptionId(Object optionId) {
        this.optionId = optionId;
    }

    public String getNetUnitPrice() {
        return netUnitPrice;
    }

    public void setNetUnitPrice(String netUnitPrice) {
        this.netUnitPrice = netUnitPrice;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Object getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Object warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getItemTax() {
        return itemTax;
    }

    public void setItemTax(String itemTax) {
        this.itemTax = itemTax;
    }

    public String getTaxRateId() {
        return taxRateId;
    }

    public void setTaxRateId(String taxRateId) {
        this.taxRateId = taxRateId;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public Object getDiscount() {
        return discount;
    }

    public void setDiscount(Object discount) {
        this.discount = discount;
    }

    public Object getItemDiscount() {
        return itemDiscount;
    }

    public void setItemDiscount(Object itemDiscount) {
        this.itemDiscount = itemDiscount;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public Object getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Object serialNo) {
        this.serialNo = serialNo;
    }

    public Object getRealUnitPrice() {
        return realUnitPrice;
    }

    public void setRealUnitPrice(Object realUnitPrice) {
        this.realUnitPrice = realUnitPrice;
    }

    public Object getSaleItemId() {
        return saleItemId;
    }

    public void setSaleItemId(Object saleItemId) {
        this.saleItemId = saleItemId;
    }

    public String getProductUnitId() {
        return productUnitId;
    }

    public void setProductUnitId(String productUnitId) {
        this.productUnitId = productUnitId;
    }

    public Object getProductUnitCode() {
        return productUnitCode;
    }

    public void setProductUnitCode(Object productUnitCode) {
        this.productUnitCode = productUnitCode;
    }

    public String getUnitQuantity() {
        return unitQuantity;
    }

    public void setUnitQuantity(String unitQuantity) {
        this.unitQuantity = unitQuantity;
    }

    public Object getComment() {
        return comment;
    }

    public void setComment(Object comment) {
        this.comment = comment;
    }

    public Object getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Object purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Object getTotalPurchasePrice() {
        return totalPurchasePrice;
    }

    public void setTotalPurchasePrice(Object totalPurchasePrice) {
        this.totalPurchasePrice = totalPurchasePrice;
    }

    public Object getProfit() {
        return profit;
    }

    public void setProfit(Object profit) {
        this.profit = profit;
    }

    public String getPackingQuantity() {
        return packingQuantity;
    }

    public void setPackingQuantity(String packingQuantity) {
        this.packingQuantity = packingQuantity;
    }

    public Object getSingleQuantity() {
        return singleQuantity;
    }

    public void setSingleQuantity(Object singleQuantity) {
        this.singleQuantity = singleQuantity;
    }

    public Object getPositionNo() {
        return positionNo;
    }

    public void setPositionNo(Object positionNo) {
        this.positionNo = positionNo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNoOfItemsInPacking() {
        return noOfItemsInPacking;
    }

    public void setNoOfItemsInPacking(String noOfItemsInPacking) {
        this.noOfItemsInPacking = noOfItemsInPacking;
    }

}
