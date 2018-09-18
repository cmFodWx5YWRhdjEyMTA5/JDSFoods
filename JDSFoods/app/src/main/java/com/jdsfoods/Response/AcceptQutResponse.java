package com.jdsfoods.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AcceptQutResponse {

    @SerializedName("flag")
    @Expose
    private Integer flag;
    @SerializedName("image_base")
    @Expose
    private String imageBase;
    @SerializedName("quote_detail")
    @Expose
    private QuoteDetail quoteDetail;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getImageBase() {
        return imageBase;
    }

    public void setImageBase(String imageBase) {
        this.imageBase = imageBase;
    }

    public QuoteDetail getQuoteDetail() {
        return quoteDetail;
    }

    public void setQuoteDetail(QuoteDetail quoteDetail) {
        this.quoteDetail = quoteDetail;
    }

    public class QuoteDetail {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("reference_no")
        @Expose
        private String referenceNo;
        @SerializedName("customer_id")
        @Expose
        private String customerId;
        @SerializedName("customer")
        @Expose
        private String customer;
        @SerializedName("warehouse_id")
        @Expose
        private String warehouseId;
        @SerializedName("biller_id")
        @Expose
        private String billerId;
        @SerializedName("biller")
        @Expose
        private String biller;
        @SerializedName("note")
        @Expose
        private String note;
        @SerializedName("internal_note")
        @Expose
        private Object internalNote;
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("product_discount")
        @Expose
        private String productDiscount;
        @SerializedName("order_discount")
        @Expose
        private String orderDiscount;
        @SerializedName("order_discount_id")
        @Expose
        private Object orderDiscountId;
        @SerializedName("total_discount")
        @Expose
        private String totalDiscount;
        @SerializedName("product_tax")
        @Expose
        private String productTax;
        @SerializedName("order_tax_id")
        @Expose
        private Object orderTaxId;
        @SerializedName("order_tax")
        @Expose
        private String orderTax;
        @SerializedName("total_tax")
        @Expose
        private String totalTax;
        @SerializedName("shipping")
        @Expose
        private String shipping;
        @SerializedName("grand_total")
        @Expose
        private String grandTotal;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("created_by")
        @Expose
        private String createdBy;
        @SerializedName("updated_by")
        @Expose
        private String updatedBy;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("attachment")
        @Expose
        private Object attachment;
        @SerializedName("supplier_id")
        @Expose
        private String supplierId;
        @SerializedName("supplier")
        @Expose
        private Object supplier;
        @SerializedName("quote_from_app")
        @Expose
        private Object quoteFromApp;
        @SerializedName("is_quote_from_app")
        @Expose
        private String isQuoteFromApp;
        @SerializedName("is_user_agree")
        @Expose
        private Object isUserAgree;
        @SerializedName("review_by_admin")
        @Expose
        private String reviewByAdmin;
        @SerializedName("is_delivered")
        @Expose
        private Object isDelivered;
        @SerializedName("delivery_vehicle")
        @Expose
        private Object deliveryVehicle;
        @SerializedName("delivery_time")
        @Expose
        private Object deliveryTime;
        @SerializedName("load_by")
        @Expose
        private Object loadBy;
        @SerializedName("order_status")
        @Expose
        private Object orderStatus;
        @SerializedName("quote_item")
        @Expose
        public List<QuoteItem> quoteItem = null;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getReferenceNo() {
            return referenceNo;
        }

        public void setReferenceNo(String referenceNo) {
            this.referenceNo = referenceNo;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getCustomer() {
            return customer;
        }

        public void setCustomer(String customer) {
            this.customer = customer;
        }

        public String getWarehouseId() {
            return warehouseId;
        }

        public void setWarehouseId(String warehouseId) {
            this.warehouseId = warehouseId;
        }

        public String getBillerId() {
            return billerId;
        }

        public void setBillerId(String billerId) {
            this.billerId = billerId;
        }

        public String getBiller() {
            return biller;
        }

        public void setBiller(String biller) {
            this.biller = biller;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public Object getInternalNote() {
            return internalNote;
        }

        public void setInternalNote(Object internalNote) {
            this.internalNote = internalNote;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getProductDiscount() {
            return productDiscount;
        }

        public void setProductDiscount(String productDiscount) {
            this.productDiscount = productDiscount;
        }

        public String getOrderDiscount() {
            return orderDiscount;
        }

        public void setOrderDiscount(String orderDiscount) {
            this.orderDiscount = orderDiscount;
        }

        public Object getOrderDiscountId() {
            return orderDiscountId;
        }

        public void setOrderDiscountId(Object orderDiscountId) {
            this.orderDiscountId = orderDiscountId;
        }

        public String getTotalDiscount() {
            return totalDiscount;
        }

        public void setTotalDiscount(String totalDiscount) {
            this.totalDiscount = totalDiscount;
        }

        public String getProductTax() {
            return productTax;
        }

        public void setProductTax(String productTax) {
            this.productTax = productTax;
        }

        public Object getOrderTaxId() {
            return orderTaxId;
        }

        public void setOrderTaxId(Object orderTaxId) {
            this.orderTaxId = orderTaxId;
        }

        public String getOrderTax() {
            return orderTax;
        }

        public void setOrderTax(String orderTax) {
            this.orderTax = orderTax;
        }

        public String getTotalTax() {
            return totalTax;
        }

        public void setTotalTax(String totalTax) {
            this.totalTax = totalTax;
        }

        public String getShipping() {
            return shipping;
        }

        public void setShipping(String shipping) {
            this.shipping = shipping;
        }

        public String getGrandTotal() {
            return grandTotal;
        }

        public void setGrandTotal(String grandTotal) {
            this.grandTotal = grandTotal;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Object getAttachment() {
            return attachment;
        }

        public void setAttachment(Object attachment) {
            this.attachment = attachment;
        }

        public String getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(String supplierId) {
            this.supplierId = supplierId;
        }

        public Object getSupplier() {
            return supplier;
        }

        public void setSupplier(Object supplier) {
            this.supplier = supplier;
        }

        public Object getQuoteFromApp() {
            return quoteFromApp;
        }

        public void setQuoteFromApp(Object quoteFromApp) {
            this.quoteFromApp = quoteFromApp;
        }

        public String getIsQuoteFromApp() {
            return isQuoteFromApp;
        }

        public void setIsQuoteFromApp(String isQuoteFromApp) {
            this.isQuoteFromApp = isQuoteFromApp;
        }

        public Object getIsUserAgree() {
            return isUserAgree;
        }

        public void setIsUserAgree(Object isUserAgree) {
            this.isUserAgree = isUserAgree;
        }

        public String getReviewByAdmin() {
            return reviewByAdmin;
        }

        public void setReviewByAdmin(String reviewByAdmin) {
            this.reviewByAdmin = reviewByAdmin;
        }

        public Object getIsDelivered() {
            return isDelivered;
        }

        public void setIsDelivered(Object isDelivered) {
            this.isDelivered = isDelivered;
        }

        public Object getDeliveryVehicle() {
            return deliveryVehicle;
        }

        public void setDeliveryVehicle(Object deliveryVehicle) {
            this.deliveryVehicle = deliveryVehicle;
        }

        public Object getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(Object deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public Object getLoadBy() {
            return loadBy;
        }

        public void setLoadBy(Object loadBy) {
            this.loadBy = loadBy;
        }

        public Object getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(Object orderStatus) {
            this.orderStatus = orderStatus;
        }

        public List<QuoteItem> getQuoteItem() {
            return quoteItem;
        }

        public void setQuoteItem(List<QuoteItem> quoteItem) {
            this.quoteItem = quoteItem;
        }

    }

    public class QuoteItem {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("quote_id")
        @Expose
        private String quoteId;
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
        private String optionId;
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
        private String warehouseId;
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
        private String discount;
        @SerializedName("item_discount")
        @Expose
        private String itemDiscount;
        @SerializedName("subtotal")
        @Expose
        private String subtotal;
        @SerializedName("serial_no")
        @Expose
        private Object serialNo;
        @SerializedName("real_unit_price")
        @Expose
        private String realUnitPrice;
        @SerializedName("product_unit_id")
        @Expose
        private String productUnitId;
        @SerializedName("product_unit_code")
        @Expose
        private Object productUnitCode;
        @SerializedName("unit_quantity")
        @Expose
        private String unitQuantity;
        @SerializedName("single_product_quantity")
        @Expose
        private String singleProductQuantity;
        @SerializedName("packing_quantity")
        @Expose
        private String packingQuantity;
        @SerializedName("block_quantity_app")
        @Expose
        private Object blockQuantityApp;
        @SerializedName("approved_for_app")
        @Expose
        private String approvedForApp;
        @SerializedName("item_status")
        @Expose
        private Object itemStatus;
        @SerializedName("item_load_quantity")
        @Expose
        private Object itemLoadQuantity;
        @SerializedName("item_notes")
        @Expose
        private Object itemNotes;
        @SerializedName("is_scan")
        @Expose
        private Object isScan;
        @SerializedName("no_of_items_in_packing")
        @Expose
        private String noOfItemsInPacking;
        @SerializedName("image")
        @Expose
        private String image;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQuoteId() {
            return quoteId;
        }

        public void setQuoteId(String quoteId) {
            this.quoteId = quoteId;
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

        public String getOptionId() {
            return optionId;
        }

        public void setOptionId(String optionId) {
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

        public String getWarehouseId() {
            return warehouseId;
        }

        public void setWarehouseId(String warehouseId) {
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

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getItemDiscount() {
            return itemDiscount;
        }

        public void setItemDiscount(String itemDiscount) {
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

        public String getRealUnitPrice() {
            return realUnitPrice;
        }

        public void setRealUnitPrice(String realUnitPrice) {
            this.realUnitPrice = realUnitPrice;
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

        public String getSingleProductQuantity() {
            return singleProductQuantity;
        }

        public void setSingleProductQuantity(String singleProductQuantity) {
            this.singleProductQuantity = singleProductQuantity;
        }

        public String getPackingQuantity() {
            return packingQuantity;
        }

        public void setPackingQuantity(String packingQuantity) {
            this.packingQuantity = packingQuantity;
        }

        public Object getBlockQuantityApp() {
            return blockQuantityApp;
        }

        public void setBlockQuantityApp(Object blockQuantityApp) {
            this.blockQuantityApp = blockQuantityApp;
        }

        public String getApprovedForApp() {
            return approvedForApp;
        }

        public void setApprovedForApp(String approvedForApp) {
            this.approvedForApp = approvedForApp;
        }

        public Object getItemStatus() {
            return itemStatus;
        }

        public void setItemStatus(Object itemStatus) {
            this.itemStatus = itemStatus;
        }

        public Object getItemLoadQuantity() {
            return itemLoadQuantity;
        }

        public void setItemLoadQuantity(Object itemLoadQuantity) {
            this.itemLoadQuantity = itemLoadQuantity;
        }

        public Object getItemNotes() {
            return itemNotes;
        }

        public void setItemNotes(Object itemNotes) {
            this.itemNotes = itemNotes;
        }

        public Object getIsScan() {
            return isScan;
        }

        public void setIsScan(Object isScan) {
            this.isScan = isScan;
        }

        public String getNoOfItemsInPacking() {
            return noOfItemsInPacking;
        }

        public void setNoOfItemsInPacking(String noOfItemsInPacking) {
            this.noOfItemsInPacking = noOfItemsInPacking;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
