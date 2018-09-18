package com.jdsfoods.Retrofit;

import com.jdsfoods.Response.AcceptQutResponse;
import com.jdsfoods.Response.AllQuoteResponse;
import com.jdsfoods.Response.BankResponse;
import com.jdsfoods.Response.ForgetResponse;
import com.jdsfoods.Response.HistoryResponse;
import com.jdsfoods.Response.LoginResponse;
import com.jdsfoods.Response.OrderResponse;
import com.jdsfoods.Response.PayResponse;
import com.jdsfoods.Response.PaymentResponse;
import com.jdsfoods.Response.PendingResponse;
import com.jdsfoods.Response.ProDetailResponse;
import com.jdsfoods.Response.ProductResponse;
import com.jdsfoods.Response.ProfileResponse;
import com.jdsfoods.Response.SaleDetailResponse;
import com.jdsfoods.Response.SearchResponse;
import com.jdsfoods.Response.ShopResponse;
import com.jdsfoods.Response.SubQutResponse;
import com.jdsfoods.Response.TrackResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by dikhong on 26-07-2018.
 */

public interface JDSFoodService {

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("email") String email,
                              @Field("password") String password,
                              @Field("device_type") String deviceType,
                              @Field("device_token") String deviceToken);

    @FormUrlEncoded
    @POST("add_quotation")
    Call<ForgetResponse> addQuote(@Field("customer_id") String customerId,
                                  @Field("customer_name") String customerName,
                                  @Field("product_id") String productId,
                                  @Field("product_code") String productCode,
                                  @Field("product_name") String productName,
                                  @Field("unit_price") String unitPrice,
                                  @Field("quantity") String quantity,
                                  @Field("single_product_quantity") String singleProductQuantity,
                                  @Field("packing_quantity") String packingQuantity,
                                  @Field("subtotal") String subTotal,
                                  @Field("grand_total") String grandTotal,
                                  @Field("total_tax") String totalTax,
                                  @Field("product_unit_id") String productUnitId,
                                  @Field("item_tax") String itemTax,
                                  @Field("tax_rate_id") String taxRateId,
                                  @Field("product_unit_code") String productUnitCode,
                                  @Field("quote_id") String quoteId,
                                  @Field("user_confirm") String userConfirm,
                                  @Field("authorization") String authorization);

    @GET("forgot_password")
    Call<ForgetResponse> forget(@Query("email") String email);

    @GET("get_all_parent_categories")
    Call<ShopResponse> shopList(@Query("customer_id") String customerId,
                                @Query("authorization") String authorization);

    @GET("get_all_brands")
    Call<ShopResponse> brandList(@Query("authorization") String authorization);

    @GET("get_user_profile")
    Call<ProfileResponse> userProfile(@Query("id") String id,
                                      @Query("authorization") String authorization);

    @FormUrlEncoded
    @POST("pay_order_payment")
    Call<PayResponse> payNow(@Field("sale_id") String saleId,
                             @Field("payment_method") String paymentMethod,
                             @Field("total") String total,
                             @Field("customer_id") String customerId,
                             @Field("authorization") String authorization);

    @FormUrlEncoded
    @POST("get_payment_method")
    Call<PaymentResponse> payMethod(@Field("authorization") String authorization);

    @FormUrlEncoded
    @POST("get_bank_details")
    Call<BankResponse> bankDetail(@Field("authorization") String authorization);

    @FormUrlEncoded
    @POST("get_reviewed_quotation")
    Call<AcceptQutResponse> acceptedQuote(@Field("quote_id") String quoteId,
                                          @Field("authorization") String authorization);

    @GET("get_quotation_items_by_quoteid")
    Call<SubQutResponse> subQuoteList(@Query("quote_id") String quoteId,
                                      @Query("authorization") String authorization);
    @FormUrlEncoded
    @POST("pending_payment_bill")
    Call<PendingResponse> pendingBill(@Field("customer_id") String customerId,
                                      @Field("authorization") String authorization);

    @FormUrlEncoded
    @POST("complete_bill")
    Call<PendingResponse> completeBill(@Field("customer_id") String customerId,
                                       @Field("authorization") String authorization);

    @FormUrlEncoded
    @POST("sale_history")
    Call<HistoryResponse> saleHistory(@Field("customer_id") String customerId,
                                      @Field("authorization") String authorization);

    @FormUrlEncoded
    @POST("sale_detail")
    Call<SaleDetailResponse> saleDetail(@Field("id") String id,
                                        @Field("authorization") String authorization);



    @Multipart
    @POST("edit_user_profile")
    Call<ProfileResponse> editProfile(@Part MultipartBody.Part image,
                                     @PartMap Map<String, RequestBody> field);

    @FormUrlEncoded
    @POST("change_password")
    Call<ForgetResponse> changePwd(@Field("id") String id,
                                   @Field("old_password") String oldPassword,
                                   @Field("new_password") String newPassword);

    @FormUrlEncoded
    @POST("logout")
    Call<ForgetResponse> logout(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("search_food_item")
    Call<SearchResponse> searchFood(@Field("search") String search,
                                    @Field("authorization") String authorization);

    @FormUrlEncoded
    @POST("order_list")
    Call<OrderResponse> orderList(@Field("customer_id") String customerId,
                                  @Field("authorization") String authorization);

    @GET("product_details")
    Call<ProDetailResponse> productDetail(@Query("product_id") String productId,
                                          @Query("authorization") String authorization);

    @FormUrlEncoded
    @POST("order_track")
    Call<TrackResponse> trackOrder(@Field("quotation_id") String userId,
                                   @Field("authorization") String authorization);

    @GET("edit_quotation_item")
    Call<ForgetResponse> editQuoteItem(@Query("quote_item_id") String quoteItemId,
                                       @Query("unit_price") String unitPrice,
                                       @Query("quantity") String quantity,
                                       @Query("subtotal") String subtotal,
                                       @Query("authorization") String authorization);

    @GET("edit_quotation_item")
    Call<ForgetResponse> editQuote(@Query("quote_item_id") String quoteItemId,
                                   @Query("unit_price") String unitPrice,
                                   @Query("single_product_quantity") String singleProductQuantity,
                                   @Query("packing_quantity") String packingQuantity,
                                   @Query("quantity") String quantity,
                                   @Query("subtotal") String subtotal,
                                   @Query("authorization") String authorization);

    @GET("delete_quotation_item")
    Call<ForgetResponse> deleteQuote(@Query("quote_item_id") String quoteItemId,
                                     @Query("authorization") String authorization);


    @GET("get_all_quotation_items")
    Call<AllQuoteResponse> getAllQuote(@Query("quote_id") String quoteId,
                                       @Query("authorization") String authorization);

    @GET("get_all_sub_categories")
    Call<ShopResponse> getSubCat(@Query("parent_category_id") String parentCategoryId,
                                 @Query("authorization") String authorization);

    @GET("get_all_products")
    Call<ProductResponse> getProducts(@Query("category_id") String category_id,
                                      @Query("subcategory_id") String subcategory_id,
                                      @Query("brand") String brand,
                                      @Query("authorization") String authorization);

    @GET("final_quotation_submit")
    Call<ForgetResponse> submitQuote(@Query("quote_id") String quoteId,
                                     @Query("total") String total,
                                     @Query("grand_total") String grandTotal,
                                     @Query("order_discount") String orderDiscount,
                                     @Query("total_discount") String totalDiscount,
                                     @Query("shipping") String shipping,
                                     @Query("tax") String tax,
                                     @Query("authorization") String authorization);

    @FormUrlEncoded
    @POST("place_order")
    Call<PayResponse> placeOrder(@Field("quote_id") String quoteId,
                                     @Field("is_credit") String isCredit,
                                     @Field("pay_now") String payNow,
                                     @Field("payment_method") String paymentMethod,
                                     @Field("payment_term") String paymentTerm,
                                     @Field("amount") String amount,
                                     @Field("customer_id") String customerId,
                                     @Field("total_tax") String totalTax,
                                     @Field("total") String total,
                                     @Field("grand_total") String grandTotal,
                                     @Field("user_confirm") String userConfirm,
                                     @Field("authorization") String authorization);

}
