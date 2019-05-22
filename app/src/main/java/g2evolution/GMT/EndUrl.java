package g2evolution.GMT;


public class EndUrl {


    public static final String APIURL2 = "http://g2evolution.in/eshop_New/admin/payumoney/";


    public static final String APIURLgrocerry = "http://g2evolution.in/gmt/admin/rest/";


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



    //---------------------------------Search Products------------------------

    public static final String Offer_Products_URL = APIURLgrocerry + "getOfferProducts";







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



}