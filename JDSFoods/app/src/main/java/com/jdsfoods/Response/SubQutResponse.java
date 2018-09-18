package com.jdsfoods.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dikhong on 30-07-2018.
 */

public class SubQutResponse {

    @SerializedName("flag")
    @Expose

    public Integer flag;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<SubQuote> getSubQuoteList() {
        return subQuoteList;
    }

    public void setSubQuoteList(List<SubQuote> subQuoteList) {
        this.subQuoteList = subQuoteList;
    }

    @SerializedName("image_path")
    @Expose
    public String imagePath;

    @SerializedName("data")
    @Expose
    List<SubQuote> subQuoteList;

    public class SubQuote {
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("no_of_items_in_packing")
        @Expose
        private String noOfItemsInPacking;
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
        private String serialNo;
        @SerializedName("real_unit_price")
        @Expose
        private String realUnitPrice;
        @SerializedName("product_unit_id")
        @Expose
        private String productUnitId;
        @SerializedName("product_unit_code")
        @Expose
        private String productUnitCode;
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
        private String blockQuantityApp;
        @SerializedName("approved_for_app")
        @Expose
        private String approvedForApp;
        @SerializedName("item_status")
        @Expose
        private String itemStatus;
        @SerializedName("item_load_quantity")
        @Expose
        private String itemLoadQuantity;
        @SerializedName("item_notes")
        @Expose
        private String itemNotes;
        @SerializedName("is_scan")
        @Expose
        private String isScan;

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

        public String getSerialNo() {
            return serialNo;
        }

        public void setSerialNo(String serialNo) {
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

        public String getProductUnitCode() {
            return productUnitCode;
        }

        public void setProductUnitCode(String productUnitCode) {
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

        public String getBlockQuantityApp() {
            return blockQuantityApp;
        }

        public void setBlockQuantityApp(String blockQuantityApp) {
            this.blockQuantityApp = blockQuantityApp;
        }

        public String getApprovedForApp() {
            return approvedForApp;
        }

        public void setApprovedForApp(String approvedForApp) {
            this.approvedForApp = approvedForApp;
        }

        public String getItemStatus() {
            return itemStatus;
        }

        public void setItemStatus(String itemStatus) {
            this.itemStatus = itemStatus;
        }

        public String getItemLoadQuantity() {
            return itemLoadQuantity;
        }

        public void setItemLoadQuantity(String itemLoadQuantity) {
            this.itemLoadQuantity = itemLoadQuantity;
        }

        public String getItemNotes() {
            return itemNotes;
        }

        public void setItemNotes(String itemNotes) {
            this.itemNotes = itemNotes;
        }

        public String getIsScan() {
            return isScan;
        }

        public void setIsScan(String isScan) {
            this.isScan = isScan;
        }

    }
}
