package g2evolution.Boutique;


public class EndUrl {



   // public static final String APIURLMain = "http://zigzi.sutures.ind.in/";
  //  public static final String APIURLMain = "http://Zigzi.g2evolution.com/";
    public static final String APIURLMain = "http://new-zigzi.g2evolution.com/";
    public static final String APIURLMaintest = "http://casual365.sutures.ind.in/";


    public static final String APIURL2 = "http://g2evolution.in/eshop_New/admin/payumoney/";


   // public static final String APIURLgrocerry = "http://g2evolution.in/gmt/admin/rest/";
    public static final String APIURLgrocerry = "http://g2evolution.in/boutique/admin/rest/";
  //  public static final String APIURLgrocerry = "http://new-zigzi.g2evolution.com/api/";


    public static final String straccessCode = "AVQB78FE28CH17BQHC";
    public static final String strmerchantId = "176524";
    public static final String strcurrency = "INR";

    public static final String strrsaKeyUrl = "http://www.grocshop.in/ccavenue/GetRSA.php";
    public static final String strredirectUrl = "http://www.grocshop.in/ccavenue/ccavResponseHandler.php";
    public static final String strcancelUrl = "http://www.grocshop.in/ccavenue/ccavResponseHandler.php";



    //-------------------------------Login-------------------------//

    public static final String LOGIN_URL = APIURLgrocerry + "login";

    public static final String LOGIN_MOBILENO = "loginWith";
    public static final String lOGIN_PASSWORD = "password";
    public static final String lOGIN_FCM_Token = "FCM_Token";



    //-------------------------------SignUp-------------------------//

    public static final String SIGNUP_URL = APIURLgrocerry + "registers";

    public static final String SIGNUPjsonobject_outside_register = "register";

    public static final String SIGNUP_inside_jsonUSENAME = "userName";
    public static final String SIGNUP_json_insideEMAILID = "userEmail";
    public static final String SIGNUP__json_insideMOBILENO = "userMobile";
    public static final String SINGUP__json_insidePASSWORD = "userPassword";
    public static final String SINGUP__json_insideregisterFrom = "registerFrom";
    public static final String SINGUP__json_FCM_Token = "FCM_Token";

    //-------------------------------SignIn-------------------------//


    public static final String SIGNIN_URL = APIURLgrocerry + "registers";


    public static final String SIGNIN_USENAME = "userName";
    public static final String SIGNIN_EMAILID = "userEmail";
    public static final String SIGNIN_MOBILENO = "userMobile";
    public static final String SIGNIN_PASSWORD = "userPassword";
    public static final String SIGNIN_registerFrom = "registerFrom";
    public static final String SIGNIN_FCM_Token = "FCM_Token";


     //-------------------------------Signup_otp-------------------------//

     public static final String SIGNUP_OTP_URL = APIURLgrocerry + "otpVerification";

     public static final String SIGNUPOTPjsonobject_outside_otp = "otpverify";


     public static final String SIGNUP_OTP_userMobile = "userMobile";
     public static final String SIGNUP_OTP_otp = "otp";



    //-------------------------------Forget Password-------------------------//

    public static final String FORGTEPASSWORD_URL = APIURLgrocerry + "forgotPassword";

    public static final String FORGET_MOBILENO = "userMobile";



    //-------------------------------Forget Password  otp-------------------------//

    public static final String FORGTEPASSWORDOTP_URL = APIURLgrocerry + "otpVerification";

    public static final String FORGTEPASSWORDOTPjsonobject_outside_otp = "otpverify";

    public static final String FORGTEPASSWORDOTP_MOBILENO = "userMobile";
    public static final String FORGTEPASSWORDOTP_OTP = "otp";



    //-------------------------------New Password-------------------------//

    public static final String NewPASSWORD_URL = APIURLgrocerry + "changePassword";

    public static final String NewPASSWORD_MOBILENO = "userMobile";
    public static final String NewPASSWORD_PASSWORD = "newpasssword";


    //----------------------------fragmen_home---------------//

    public static final String fragment_home_expandable =APIURLgrocerry+ "getsub_cat";


    //-----------------------------------Get_SubCategory_List-----------------------

    public  static  final  String Get_SubCategory_List_URL = APIURLgrocerry+"getsubcatBycat";

    public static final String Get_CatId = "catId";


    //=---------------------------------------Get_Child_SubCategory_List-----------------

    public  static  final  String Get_Child_SubCategory_List = APIURLgrocerry+"getchildcatBysubCat";

    public static final String Get_SubCat_CatId = "subcatId";



//-------------------------------------Product LIST--------------------------------

    public  static  final  String GetCategoryChildProductList_URL = APIURLgrocerry+"getproductbychildcat";

    public static final String Get_childCatId = "childCatId";
    public static final String Get_subcatId = "subcatId";
    public static final String Get_lowtoHigh= "lowtoHigh";
    public static final String Get_hightoLow= "hightoLow";
    public static final String Get_newestFirst= "newestFirst";
    public static final String Get_offerProduct= "offerProduct";
    public static final String Get_priceFilter= "priceFilter";
    public static final String Get_minPrice= "minPrice";
    public static final String Get_maxPrice= "maxPrice";
    public static final String Get_brands= "brands";






    //---------------------------------Products------------------------

    public static final String GetCategorywiseProducts_URL = APIURLgrocerry + "getCategorywiseProducts";



    //---------------------------------Recently_added Products------------------------


    public static final String Recently_added_URL = APIURLgrocerry + "getLatestProductsDetails";


    //---------------------------------getCategories------------------------

    public static final String GetCategorywise_URL = APIURLgrocerry + "getCategories";



    //---------------------------------product_details------------------------

    public static final String Product_details_URL = APIURLgrocerry + "getProductsDetails";

    public static final String Product_details_Id = "productId";


    //---------------------------------getCartCount------------------------

    public static final String GetCartCount_URL = APIURLgrocerry + "getCartCount";

    public static final String GetCartCount_Id = "userId";


    //-------------------------------addtocart-------------------------//

    public static final String Addtocart_URL = APIURLgrocerry + "addtocart";

    public static final String Addtocart_outside_otp = "addtocart";


    public static final String Addtocart_userId = "userId";
    public static final String Addtocart_productId = "productId";
    public static final String Addtocart_quantity = "quantity";

    //-------------------------------BOOK_NOW-------------------------//

    public static final String BOOK_NOW_URL = APIURLgrocerry + "confirmedOrder";


    public static final String BOOK_NOW_productId = "product_id";
    public static final String BOOK_NOW_quantity = "Qty";


    //---------------------------------getuserCartDetails------------------------

    public static final String GetuserCartDetails_URL = APIURLgrocerry + "getuserCartDetails";

    public static final String GetuserCartDetails_Id = "userId";



    //---------------------------------Address add------------------------

    public static final String ADDdeliveryAddress_URL = APIURLgrocerry + "deliveryAddress";

    public static final String ADDdeliveryAddressjsonobject_outside_otp = "address";

    public static final String ADDdeliveryAddress_Id = "userId";
    public static final String ADDdeliveryAddress_name = "name";
    public static final String ADDdeliveryAddress_address = "deliveryAddress";
    public static final String ADDdeliveryAddress_landmark = "landMark";
    public static final String ADDdeliveryAddress_mobile = "mobile";
    public static final String ADDdeliveryAddress_pincode = "pincode";
    public static final String ADDdeliveryAddress_imp_note = "imp_note";
    public static final String ADDdeliveryAddress_streditaddress = "emailId";
    public static final String ADDdeliveryAddress_latitude = "latitude";
    public static final String ADDdeliveryAddress_longitude = "longitude";
    public static final String ADDdeliveryAddress_add_landmark = "landMark1";

//---------------------------------DeliveryAddress------------------------

    public static final String DeliveryAddress_URL = APIURLgrocerry + "deliveryAddressList";

    public static final String DeliveryAddress_userId = "userId";




    //---------------------------------Address Update------------------------

    public static final String ADDdeliveryAddress_Updte_URL = APIURLgrocerry + "UpdatedeliveryAddress";

    public static final String ADDdeliveryAddressjsonobject_Updte_outside_otp = "address";

    public static final String ADDdeliveryAddress_Updte_Id = "userId";
    public static final String ADDdeliveryAddress_Updte_address_Id = "addressId";
    public static final String ADDdeliveryAddress_Updte_name = "name";
    public static final String ADDdeliveryAddress_Updte_address = "deliveryAddress";
    public static final String ADDdeliveryAddress_Updte_landmark = "landMark";
    public static final String ADDdeliveryAddress_Updte_mobile = "mobile";
    public static final String ADDdeliveryAddress_Updte_pincode = "pincode";

    public static final String ADDdeliveryAddress__imp_note = "imp_note";
    public static final String ADDdeliveryAddress__streditaddress = "userEmail";
    public static final String ADDdeliveryAddress__latitude = "latitude";
    public static final String ADDdeliveryAddress__longitude = "longitude";

    public static final String ADDdeliveryAddress_addlandmark = "landMark1";



    //---------------------------------Address Delete------------------------

    public static final String AddressDelete_URL = APIURLgrocerry + "deletedeliveryAddress";

    public static final String AddressDelete_AddressId = "addressId";


    //---------------------------------Search Products------------------------

    public static final String Search_Products_URL = APIURLgrocerry + "fetch_productlist";

    public static final String Search_categoryname = "category_id";
    public static final String Search_subcategoryname = "sub_category_id";
    public static final String Search_child = "child_category_id";








    //---------------------------------Filter------------------------

    public static final String Filter_URL = APIURLgrocerry + "productSearch";

    public static final String Filter_searchType = "searchType";
    public static final String Filter_categoryId = "categoryId";
    public static final String Filter_subcatId = "subcatId";
    public static final String Filter_Pagenumber = "page_number";
    public static final String Filter_request_count = "page_limit";
    public static final String Filter_brandName= "brandName";


    //--------------------------------------------------------//

    public static final String Delete_URL = APIURLgrocerry + "deletecart";


    public static final String Deletejsonobject_outside_register = "deletecart";

    public static final String Delete_inside_json = "productId";
    public static final String Delete_json_inside = "userId";



    //---------------------------------Cart Update------------------------

    public static final String CartUpdate_URL = APIURLgrocerry + "qtyChange";

    public static final String CartUpdate_Userid = "userId";
    public static final String CartUpdate_Cartid = "productId";
    public static final String CartUpdate_Quantity = "quantity";


    //---------------------------------Rating------------------------

    public static final String Rating_URL = APIURLgrocerry + "rating_review";

    public static final String Rating_Userid = "userId";
    public static final String Rating_Eventid = "productId";
    public static final String Rating_Ratingcount = "ratingCount";
    public static final String Rating_Ratingtitle = "ratingTitle";
    public static final String Rating_Ratingcomment = "ratingComment";
    public static final String Rating_Ratingfrom = "ratingFrom";


    //---------------------------------Rating List------------------------

    public static final String RatingList_URL = APIURLgrocerry + "getEventsRatingDetails";

    public static final String RatingList_Eventid = "productId";



    //---------------------------------OrderHistory------------------------

    public static final String OrderHistory_URL = APIURLgrocerry + "getuserOrderHistory";

    public static final String OrderHistory_customer_id = "customer_id";


    //---------------------------------OrderDetails------------------------

    public static final String OrderDetails_URL = APIURLgrocerry + "getuserOrderHistoryDetails";

    public static final String OrderDetails_order_id = "orderId";


    //---------------------------------Order Delete------------------------

    public static final String OrderDelete_URL = APIURLgrocerry + "callcelledOrder";

    public static final String OrderDelete_Userid = "userId";
    public static final String OrderDelete_Orderid = "orderId";
    public static final String OrderDelete_Reason = "cancell_reason";
    public static final String OrderDelete_Mobileno = "mobileno";


    //---------------------------------OrderItem Delete------------------------

    public static final String OrderItemDelete_URL = APIURLgrocerry + "cancelledOrder_Item";

    public static final String OrderItemDelete_Userid = "userId";
    public static final String OrderItemDelete_Orderid = "orderId";
    public static final String OrderItemDelete_Orderitem = "itemId";
    public static final String OrderItemDelete_Reason = "cancell_reason";



    //---------------------------------confirmOrder------------------------


    public static final String ConfirmOrder_URL = APIURLgrocerry + "confirmOrder";

    public static final String ConfirmOrder_Id = "userId";



    //---------------------------------Payment URL------------------------

    public static final String GetHashCode_URL = APIURL2 + "getHashCode.php";


    //---------------------------------Pincode------------------------

    public static final String Pincode_URL = APIURLgrocerry + "getPincode";


    //---------------------------------brand list------------------------

    public static final String getbrands_URL = APIURLgrocerry + "getbrands";



    //----------------------------FEEDBACK---------------//


    public static final String fragment_feedback =APIURLgrocerry+ "addFeedback";

    public static final String fragment_feedback_userid = "userId";
    public static final String fragment_feedback_message = "feedback";
    public static final String fragment_feedback_emailid = "emailId";
    public static final String fragment_feedback_mobileno = "mobileno";


    public static final String SliderImage_URL = APIURLgrocerry + "get_advertisement";

    //---------------------------------GetCategoryProduct------------------------

    //  public static final String GetCategoryProduct_URL = APIURL + "getCategoryProduct";
    public static final String GetCategoryProduct_URL = APIURLgrocerry + "getCategorywiseProducts";

    public static final String GetCategoryProduct_name = "categoryName";

//--------------------------------Get category, subcategory product--------------------

    public static final String GetCategorySubcategoryProduct_URL = APIURLgrocerry + "getCategory_Product";

    public static final String GetSubcatCategoryProduct_name = "categoryName";
    public static final String GetCategorySubcategoryProduct_name = "SubCategoryName";



//--------------------------------Get Profile--------------------

    public static final String Get_UserData_URL = APIURLgrocerry + "getProfileDetails";

    public static final String Get_UserData_user_id = "userId";



//--------------------------------Update Profile--------------------

    public static final String Update_UserProfile_URL = APIURLgrocerry + "updateProfilePic";

    public static final String Update_UserProfile_Userid = "userId";
    public static final String Update_UserProfile_Profileimage = "file";



 //--------------------------------Notifications -------------------
 public static final String Notifications_URL = APIURLgrocerry + "get_user_notifications";
 public static final String Notifications_URL_Userid = "user_id";


 //=========================Ecommerce============================






 //-------------------------------Login-------------------------//

 public static final String Login_URL = APIURLMain + "api/user/authentication/signin/index";

 public static final String Login_emailphone  = "emailphone";
 public static final String Login_password  = "password";
 public static final String Login_firebase_token  = "firebase_token";



 //-------------------------------Signup-------------------------//

 public static final String Signup_URL = APIURLMain + "api/user/authentication/signup/index";

 public static final String Signup_phone  = "phone";
 public static final String Signup_name = "name";
 public static final String Signup_email = "email";
 public static final String Signup_password = "password";



 //-------------------------------Signup OTP-------------------------//

 public static final String SignupOTP_URL = APIURLMain + "api/user/authentication/signup/verify";

 public static final String SignupOTP_register_exp  = "register_exp";
 public static final String SignupOTP_temp_user_id  = "temp_user_id";


 //-------------------------------Signup Resend OTP-------------------------//

 public static final String ResendSignupOTP_URL = APIURLMain + "api/user/authentication/signup/resend";

 public static final String ResendSignupOTP_URL_temp_user_id  = "temp_user_id";


 //---------------------------------Forgot Password--------------------------
 public static final String ForgotPassword_URL = APIURLMain + "api/user/authentication/forgot-password/send";
 public static final String ForgotPassword_emailphone  = "emailphone";



 //---------------------------------ForgotPassword OTP--------------------------
 public static final String ValidateForgotPasswordOTP_URL = APIURLMain + "api/user/authentication/forgot-password/verify";
 public static final String ValidateForgotPasswordOTP_URL_forgot_exp  = "forgot_exp";
 public static final String ValidateForgotPasswordOTP_URL_user_id   = "user_id";


 //---------------------------------Signup Update Password--------------------------
 public static final String SignupUpdatePassword_URL = APIURLMain + "api/user/authentication/password/update";
 public static final String SignupUpdatePassword_URL_user_id   = "user_id";
 public static final String SignupUpdatePassword_URL_password   = "password";


 //-------------------------------Get Profile Details-------------------------//

 public static final String GetProfileDetails_URL = APIURLMain + "api/user/authentication/signin/profile";

 public static final String GetProfileDetails_user_id  = "user_id";


 //-------------------------------Get Upload Profile-------------------------//

 public static final String GetUploadProfiles_URL = APIURLMain + "api/user/authentication/signin/profile";

 public static final String GetUploadProfile_user_id  = "user_id";
 public static final String GetUploadProfile_image  = "image";
 public static final String GetUploadProfile_name  = "name";
 public static final String GetUploadProfile_email  = "email";
 public static final String GetUploadProfile_fcm_token  = "fcm_token";


 //-------------------------------Change Password-------------------------//

 public static final String ChangePASSWORD_URL = APIURLMain + "api/user/authentication/password/change";

 public static final String ChangePASSWORD_user_id = "user_id";
 public static final String ChangePASSWORD_PASSWORD = "password";
 public static final String ChangePASSWORD_old_password = "old_password";

 //-----------------------------------Sliding Image-----------------------

 public static final String SliderImgEcom_URL = APIURLMain + "api/component/banner/list";

 //-----------------------------------Category-----------------------

 public  static  final  String Get_Category_URL = APIURLMain+"api/rest/category/list";


//--------------------------------Get sub Category List--------------------

 public static final String GetSubCategoryList_URL = APIURLMain + "api/rest/category/sub_list";

 public static final String GetSubcatList_id = "id";

//========================================================================================================================================

//--------------------------------Get Products--------------------

 public static final String GetProductst_URL = APIURLMain + "api/rest/product/list";

 public static final String GetProducts_id = "id";



//--------------------------------Get Products Details--------------------

 public static final String GetProductstDetails_URL = APIURLMain + "api/rest/product/detail";

 public static final String GetProductsDetails_id = "product_id";


//-------------------------------Post Whishlist--------------------

 public static final String PostWhishlist_URL = APIURLMain + "api/rest/wishlist/store";

 public static final String PostWhishlist_User_id = "user_id";
 public static final String PostWhishlist_product_id = "product_id";



//-------------------------------CheckPincode--------------------

 public static final String CheckPincode_URL = APIURLMain + "api/rest/pin";

 public static final String CheckPincode_pincode = "pincode";

//-------------------------------Add To Cart--------------------

 public static final String AddToCart_URL = APIURLMain + "api/rest/cart/store";

 public static final String AddToCart_product_id = "product_id";
 public static final String AddToCart_product_name = "size";
 public static final String AddToCart_user_id = "user_id";
 public static final String AddToCart_quantity = "quantity";
 public static final String AddToCart_price = "price";


//-------------------------------Get Whishlist--------------------

 public static final String GetWhishlist_URL = APIURLMain + "api/rest/wishlist/list";

 public static final String GetWhishlist_User_id = "user_id";


//-------------------------------Delete Whishlist--------------------

 public static final String DeleteWhishlist_URL = APIURLMain + "api/rest/wishlist/remove";

 public static final String DeleteWhishlist_User_id = "user_id";
 public static final String DeleteWhishlist_id = "id";



//-------------------------------Get Reviews--------------------

 public static final String GetReviews_URL = APIURLMain + "api/rest/review/list";

 public static final String GetReviews_product_id = "product_id";


//-------------------------------Post Reviews--------------------

 public static final String PostReviews_URL = APIURLMain + "api/rest/review/store";

 public static final String PostReviews_product_id = "product_id";
 public static final String PostReviews_user_id = "user_id";
 public static final String PostReviews_title = "title";
 public static final String PostReviews_description = "description";
 public static final String PostReviews_rating = "rating";


//-------------------------------Add Address--------------------

 public static final String AddAddress_URL = APIURLMain + "api/user/address/store";

 public static final String AddAddress_id = "id";
 public static final String AddAddress_user_id = "user_id";
 public static final String AddAddress_address_type = "address_type";
 public static final String AddAddress_name = "name";
 public static final String AddAddress_phone = "phone";
 public static final String AddAddress_Alternate_phone = "alternate_phone";
 public static final String AddAddress_House_no = "house_no";
 public static final String AddAddress_Locality = "locality";
 public static final String AddAddress_Landmark = "landmark";
 public static final String AddAddress_City = "city";
 public static final String AddAddress_State = "state";
 public static final String AddAddress_pin = "pin";


//-------------------------------Delete Address--------------------

 public static final String DeleteAddress_URL = APIURLMain + "api/user/address/remove";

 public static final String DeleteAddress_id = "id";
//-------------------------------Get Address List--------------------

 public static final String GetAddressList_URL = APIURLMain + "api/user/address/list";

 public static final String GetAddressList_user_id = "user_id";


//-------------------------------Cart List--------------------

 public static final String CartList_URL = APIURLMain + "api/rest/cart/list";

 public static final String CartList_user_id = "user_id";


//-------------------------------Cart Delete--------------------

 public static final String CartDelete_URL = APIURLMain + "api/rest/cart/remove";

 public static final String CartDelete_id = "id";



//-------------------------------Cart Update--------------------

 public static final String PostCartUpdate_URL = APIURLMain + "api/rest/cart/store";

 public static final String PostCartUpdate_product_id = "product_id";
 public static final String PostCartUpdate_product_name = "size";
 public static final String PostCartUpdate_user_id = "user_id";
 public static final String PostCartUpdate_quantity = "quantity";
 public static final String PostCartUpdate_price = "price";


//-------------------------------CheckCoupon--------------------

 public static final String CheckCoupon_URL = APIURLMain + "api/rest/coupon/list";

 public static final String CheckCoupon_coupon_code = "coupon_code";


//-------------------------------Get Payment Details--------------------

 public static final String GetPaymentDetails_URL = APIURLMain + "api/rest/place_order/list";

 public static final String GetPaymentDetails_user_id = "user_id";
 public static final String GetPaymentDetails_coupon_price = "coupon_price";
 public static final String GetPaymentDetails_credits_price = "credits_price";


//-------------------------------Get Shipping Method--------------------

 public static final String GetShippingMethod_URL = APIURLMain + "api/rest/shipping_method/list";



//-------------------------------Place Order--------------------

 public static final String PlaceOrder_URL = APIURLMain + "api/rest/order/store";

 public static final String PlaceOrder_user_id = "user_id";
 public static final String PlaceOrder_coupon_id = "coupon_id";
 public static final String PlaceOrder_shipping_method_id = "shipping_method_id";
 public static final String PlaceOrder_shipping_price = "shipping_price";
 public static final String PlaceOrder_payment_mode_id = "payment_mode_id";
 public static final String PlaceOrder_coupon_price = "coupon_price";
 public static final String PlaceOrder_credits_price = "credits_price";
 public static final String PlaceOrder_delivery_address_id = "delivery_address_id";
 public static final String PlaceOrder_credits_id = "credits_id";
 public static final String PlaceOrder_gst = "gst";


//-------------------------------Get Order List--------------------

 public static final String GetOrderList_URL = APIURLMain + "api/rest/order/list";

 public static final String GetOrderList_user_id = "user_id";



//-------------------------------Get Order Details--------------------

 public static final String GetOrderDetails_URL = APIURLMain + "api/rest/order/order_detail";

 public static final String GetOrderDetails_order_id = "order_id";



//-------------------------------Get Order Cancel--------------------

 public static final String GetOrderCancel_URL = APIURLMain + "api/rest/order/cancel";

 public static final String GetOrderCancel_order_id = "id";
 public static final String GetOrderCancel_user_id = "user_id";
 public static final String GetOrderCancel_order_status = "order_status";
 public static final String GetOrderCancel_order_status_id = "order_status_id";
 public static final String GetOrderCancel_reason = "reason";



//-------------------------------CategoryList URL--------------------

 public static final String CategoryList_URL = APIURLMain + "api/resource/listing/packages-category";

 public static final String CategoryList_CategoryId = "resource_packages_id";


//-------------------------------Qualification URL--------------------

 public static final String Qualification_URL = APIURLMain + "api/resource/listing/education";


//-------------------------------Language URL--------------------

 public static final String Language_URL = APIURLMain + "api/resource/listing/languages";



//-------------------------------UploadResource URL--------------------

 public static final String UploadResource_URL = APIURLMain + "api/resource/subscription/manage/store";

 public static final String UploadResource_resource_packages_id = "resource_packages_id";
 public static final String UploadResource_user_id = "user_id";
 public static final String UploadResource_price = "price";
 public static final String UploadResource_order_number = "order_number";
 public static final String UploadResource_transaction_id = "transaction_id";
 public static final String UploadResource_payment_status = "payment_status";
 public static final String UploadResource_description = "description";
 public static final String UploadResource_category_id = "category_id";
 public static final String UploadResource_cv_count = "cv_count";





//-------------------------------GetResourceList URL--------------------

 public static final String GetResourceList_URL = APIURLMain + "api/resource/listing/resumes";

 public static final String GetResourceList_subscription_id = "subscription_id";
 public static final String GetResourceList_category_id = "category_id";
 public static final String GetResourceList_education_id = "education_id";
 public static final String GetResourceList_language_id = "language_id";
 public static final String GetResourceList_min_salary = "min_salary";
 public static final String GetResourceList_max_salary = "max_salary";
 public static final String GetResourceList_sort_by = "sort_by";


 //-----------------------------------Category Blog-----------------------

 public  static  final  String Get_CategoryBlog_URL = APIURLMain+"api/blog/listing/category";



 //-----------------------------------Sliding Image Blog-----------------------

 public static final String SliderImgBlog_URL = APIURLMain + "api/blog/listing/category-transc";

 public static final String SliderImgBlog_category_id = "category_id";


 //---------------------------------Search Products------------------------

 public static final String Offer_Products_URL = APIURLMain + "api/rest_api/offer/list";


}