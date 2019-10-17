package g2evolution.Boutique;

/**
 * Created by G2e Android on 24-05-2017.
 */

public class End_Urls {

    public static final String APIURL ="http://g2evolution.in/b2b/app/";
    public static final String APIURL1 = "http://www.amedo.in/api/";

    public static final String SECURITYUSERNAME ="uname";
    public static final String SECURITYPASSWORD = "upassword";


    public static final String SECURITYUSERANS ="g2evolution";
    public static final String SECURITYPASSANS = "admin";

    //---------------------------------Products------------------------
    public static final String Products_URL = APIURL1+"filter_medicine";


    //-------------------------------Login-------------------------//

    public static final String LOGIN_URL =APIURL +"login.php";

    public static final String LOGIN_MOBILENO = "mobileno";
    public static final String lOGIN_PASSWORD ="password";

    //-------------------------------SignUp-------------------------//

    public static final String SIGNUP_URL =APIURL +"register.php";


    public static final String SIGNUP_USENAME = "name";
    public static final String SIGNUP_EMAILID ="email_id";
    public static final String SIGNUP_MOBILENO ="mobile";
    public static final String SINGUP_PASSWORD ="password";

    public static final String SIGNUP_CNFPASSWORD ="confirmPassword";


    //-------------------------------Signup_otp-------------------------//

    public static final String SIGNUP_OTP_URL =APIURL +"otpregister.php";

    public static final String SIGNUP_OTP_CUSTOMERID = "loginId";
    public static final String SIGNUP_OTP_PASSWORD ="otp_num";




    //-------------------------------Forget Password-------------------------//

    public static final String FORGTEPASSWORD_URL =APIURL +"forgotpassword.php";

    public static final String FORGET_MOBILENO = "mobileNo";



    //-------------------------------Recent_POST-------------------------//

    public static final String Recent_POST_URL =APIURL +"recent_post.php";




    //-------------------------------nEAR BY POST-------------------------//

    public static final String NEARBY_POST_URL =APIURL +"nearby.php";



    public static final String NEARBY_POST_LATITUDE = "latitude";
    public static final String NEARBY_POST_LONGITUDE = "longitude";

    //------------------------------ROLE_LIST+Business-----------------------------//


    public static final String ROLE_LIST =APIURL +"serviceroleList.php";

    public static final String ROLE_LIST_SERVICEID = "serviceId";


    //------------------------------PRODUCT_LIST+Business-----------------------------//


    public static final String PRODUCT_LIST =APIURL +"productList.php";

    public static final String PRODUCT_LIST_SERVICEID = "serviceId";
    public static final String PRODUCT_LIST_ROLEID = "roleId";




    //------------------------------Filter Search----------------------------//

    public static final String Filter_URL =APIURL +"filter.php";

    public static final String CITY_ID = "cityId";
    public static final String LOCATION_ID = "locationId";
    public static final String CATEGORY_ID = "catId";
    public static final String SUBCATEGORY_ID = "subcatId";
    public static final String SERVICE_ID = "serviceId";
    public static final String MIN_PRICE = "minPrice";
    public static final String MAX_PRICE = "maxPrice";


  //  -------------------------------Activity_filter_cat_details-------------------//

    public static final String Category_details =APIURL +"catagoryList.php";

    public static final String ServiceID = "serviceId";


    //------------------------------Spinner_SubcATEGORY--------------------//

    public static final String SUBCATEGORY_Details=APIURL+"subcatList.php";

    public static final String SUBCATEGORY_SERVICEID ="serviceId";
    public static final String SUBCATEGORY_CATEGORYID ="catagoryId";


    //-------------------------------Search-----------------------//


    public static final String Search_URL =APIURL +"search.php";

    public static final String Search_CITY_ID = "cityId";
    public static final String Search_LOCATION_ID = "locationId";
    public static final String Search_SUBCATEGORY_ID = "title";


    //------------------------------PRODUCT_DETAILS----------------------------//

    public static final String PRODUCT_DETAILS =APIURL +"productDetails.php";

    public static final String PRODUCT_DETAILSID = "postadv";



    //------------------------------POST_AQDVERTISEMENT_SELECT SERVICE_Spinner_Class--------------------//

    public static final String SPINNER_CLASS=APIURL+"servicesList.php";


    //------------------------------_Spinner_Role--------------------//

    public static final String SPINNER_SECTION=APIURL+"serviceroleList.php";

    public static final String SPINNER_SECTION_SERVICEID ="serviceId";


    //------------------------------Spinner_Category--------------------//

    public static final String SPINNER_SUBJECT=APIURL+"catagoryList.php";

    public static final String SPINNER_SUBJECT_CATEGORYID="serviceId";



    //------------------------------Spinner_SubcATEGORY--------------------//

    public static final String SPINNER_SUBCATEGORY=APIURL+"subcatList.php";

    public static final String SPINNER_SUBCATEGORY_SERVICEID ="serviceId";
    public static final String SPINNER_SUBCATEGORY_CATEGORYID ="catagoryId";



    //------------------------------POST_AQDVERTISEMENT_SELECT LOCATION_Spinner_--------------------//

    public static final String SELECT_LOCATION=APIURL+"cityList.php";


    //------------------------------POST_AQDVERTISEMENT_SELECT_AREA_Spinner_--------------------//

    public static final String SELECT_AREA=APIURL+"areaList.php";

    public static final String SELECT_LOCATION_ID="cityId";



    //-----------------------------POST_ADVERTISEMENT_ENTRY URL-----------------------------//

    public static final String POST_ADVERTISEMENT_ENTRY_URL=APIURL+"postadvertisement.php";



    //------------------------------CONTACT_OWNER_--------------------//

    public static final String CONTACT_OWNER=APIURL+"contact_owner.php";

    public static final String CONTACT_OWNER_ID="login_id";
    public static final String CONTACT_POST_MESSAGE="post_message";



    //----------------------------Change Password------------------------------//

    public static final String CHANGE_PASSWORD=APIURL+"change_password.php";

    public static final String CHANGE_PASSWORD_LOGINID="login_id";
    public static final String CHANGE_PASSWORD_OLD_PASSWORD="old_password";
    public static final String CHANGE_PASSWORD_NEW_PASSWORD="new_password";



    //-------------------------Upload and Retrive Image------------///

    public static final String PROFILE_URL= APIURL + "upload_image.php";
    public static final String PROFILE_IMAGE="file";
    public static final String PROFILE_ID="login_id";

    public static final String PROFILEFETCH_URL= APIURL + "fetch_upload.php";
    public static final String PROFILEFETCH_ID="login_id";

}
