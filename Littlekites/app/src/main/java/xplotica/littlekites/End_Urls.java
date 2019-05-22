package xplotica.littlekites;

/**
 * Created by santa on 3/30/2017.
 */
public class End_Urls {

   // public static final String APIURL ="http://androidappfirst.com/school/";
    // public static final String APIURL ="http://g2evolution.in/school/";
   // public static final String APIURL ="http://xplotica.com/school/";
    public static final String APIURL ="http://rmglobaltrust.in/littlekites/";
   // public static final String APIURL ="http://g2evolution.in/school_demo/";


    //----------------------------------Signup----------------------//

    public static final String SIGNUP_URL = APIURL+"School_android/signUp";

    public static final String SIGNUP_MOBILE ="mobile";
    public static final String SIGHNUP_PASSWORD ="password";

    //----------------------------------Verify_Phone----------------------//

    public static final String VERIFY_URL = APIURL+"School_android/OTP_Validation";

    public static final String VERIFY_MOBILE ="mobile";
    public static final String VERIFY_OTP ="otp";

    //----------------------------------Login----------------------//

    public static final String LOGIN_URL = APIURL+"School_android/password_Validation";

    public static final String LOGIN_MOBILE ="mobile";
    public static final String LOGIN_PASSWORD ="password";

    //----------------------------------FORGOT----------------------//

    public static final String FORGOT_URL = APIURL+"School_android/forgotPassword";

    public static final String FORGOT_MOBILE ="mobile";


    //---------------------------------Details----------------------//

    public static final String DETAILS_URL =APIURL +"School_android/getMeberList";

    public static final String DETAILS_MOBILE ="mobile";



    //--------------------------FCM_Token-----------------------//

    public static final String FCM_DETAILS_URL=APIURL+"School_android/updateFCM";


    public static final String FCM_DETAILS_LOGINTYPE ="loginType";
    public static final String FCM_DETAILS_LOGINID ="loginId";
    public static final String FCM_DETAILS_FCMTOKEN ="FcmToken";


    //----------------------------------Login----------------------//

    public static final String CHANGEPASSWORD_URL = APIURL+"School_android/changePassword";

    public static final String CHANGEPASSWORD_MOBILE ="mobile";
    public static final String CHANGEPASSWORD_PASSWORD ="oldpassword";
    public static final String CHANGEPASSWORD_CPASSWORD ="newpassword";

    //----------------------------------Gallery Count----------------------//

    public static final String GALLLERYCOUNT_URL = APIURL+"School_android/getGalleyCount";

    public static final String GALLLERYCOUNT_Schoolid ="schoolId";

    //----------------------------------Gallery View----------------------//

    public static final String GALLLERYVIEW_URL = APIURL+"School_android/getSchoolGalley";

    public static final String GALLLERYVIEW_Schoolid ="schoolId";
    public static final String GALLLERYVIEW_Galleryid ="galleryId";


//----------------------------------Teacher_Login----------------------//

    public static final String TEACHER_LOGIN = APIURL+"School_android/";

    public static final String TEACHER_MOBILENO ="mobile";
    public static final String TEACHER_TYPE ="loginType";
    public static final String TEACHER_FcmToken ="FcmToken";

    //----------------------------------PARENT_Login----------------------//

    public static final String PARENT_LOGIN = APIURL+"School_android/";

    public static final String PARENT_MOBILENO ="mobile";
    public static final String PARENT_TYPE ="loginType";
    public static final String PARENT_FcmToken ="FcmToken";

    //----------------------------------Teacher_OTP---------------------//

    public static final String TEACHER_OTP=APIURL+"School_android/otp_verification";

    public static final String TEACHER_OTPNUMBER ="otp";
    public static final String TEACHER_loginID="mobile";
    public static final String TEACHER_Type ="loginType";

    //----------------------------------Parent_OTP---------------------//

    public static final String PARENT_OTP=APIURL+"School_android/otp_verification";

    public static final String PARENT_OTPNUMBER ="otp";
    public static final String PARENT_LoginID ="mobile";
    public static final String PARENT_LoginType ="loginType";


    //---------------------------------Parent_Student_Details----------------------//

    public static final String STUDENT_DETAILS =APIURL +"School_android/studentDetails";

    public static final String STUDENT_DETAILS_MOBILE ="mobile";
    public static final String STUDENT_DETAILS_LOGINTYPE ="loginType";
    public static final String STUDENT_DETAILS_SCHOOLID ="schoolId";

 //---------------------------------Parent Profile----------------------//

 public static final String STUDENT_PROFILE =APIURL +"School_android/profileFetchingParent";

 public static final String STUDENT_PROFILE_MOBILE ="mobile";
 public static final String STUDENT_PROFILE_LOGINTYPE ="loginType";
 public static final String STUDENT_PROFILE_SCHOOLID ="schoolId";
 public static final String STUDENT_PROFILE_LOGINID ="loginId";

    //---------------------------------Parent Attendance Details----------------------//

    public static final String PATTEN_DETAILS =APIURL +"School_android/getAbsentList";

    public static final String PATTEN_DETAILS_SCHOOLNAME  ="schoolName";
    public static final String PATTEN_DETAILS_CLASSNAME ="className";
    public static final String PATTEN_DETAILS_SECTIONNAME ="sectionName";
    public static final String PATTEN_DETAILS_LOGINTYPE ="loginType";
    public static final String PATTEN_DETAILS_LOGINID="loginId";
    public static final String PATTEN_DETAILS_ABSENTDATE="absentDate";
    //---------------------------------Parent_Dairy_Details----------------------//

    public static final String DAIRY_DETAILS =APIURL +"School_android/diaryHistoryParent";

    public static final String DAIRY_DETAILS_LOGINTYPE ="loginType";
    public static final String DAIRY_DETAILS_SCHOOL_ID ="schoolId";
    public static final String DAIRY_DETAILS_DATE ="diaryDate";
    public static final String DAIRY_DETAILS_SECTIONID ="sectionId";
    public static final String DAIRY_DETAILS_CLASSID="classId";

    //--------------------------------------Parent_HomeWork_details--------------------//


    public static final String HOMEWORK_DETAILS =APIURL +"School_android/homeworkhistoryParent";

    public static final String HOMEWORK_DETAILS_LOGINTYPE ="loginType";
    public static final String HOMEWORK_DETAILS_SCHOOL_ID ="schoolId";
    public static final String HOMEWORK_DETAILS_DATE ="homeworkDate";
    public static final String HOMEWORK_DETAILS_SECTIONID ="sectionId";
    public static final String HOMEWORK_DETAILS_CLASSID="classId";



    //---------------------------------Parent_Event_Details----------------------//

    public static final String EVENT_DETAILS =APIURL +"School_android/eventshistoryParent";

    public static final String EVENT_DETAILS_CLASSID="classId";
    public static final String EVENT_DETAILS_LOGINTYPE ="loginType";
    public static final String EVENT_DETAILS_SCHOOLID ="schoolId";


//---------------------------------Parent Search----------------------//

    public static final String PARENT_CHATSEARCH =APIURL +"School_android/StudentSearch";
    public static final String PARENT_CHATSEARCH_SCHOOLID="schoolId";
    public static final String PARENT_CHATSEARCH_CLASSID ="classId";
    public static final String PARENT_CHATSEARCH_SECTIONID ="sectionId";
    public static final String PARENT_CHATSEARCH_ROLLNO ="rollNumber";
    public static final String PARENT_CHATSEARCH_LOGINTYPE ="loginType";


    //---------------------------------Parent Chat Sending----------------------//

    public static final String PARENT_CHATSEND =APIURL +"School_android/sendSingleMessage";
    public static final String PARENT_CHATSEND_SCHOOLID="schoolId";
    public static final String PARENT_CHATSEND_CLASSID ="classId";
    public static final String PARENT_CHATSEND_SECTIONID ="sectionId";
    public static final String PARENT_CHATSEND_SENDERID ="senderId";
    public static final String PARENT_CHATSEND_RECEIVERID ="receiverId";
    public static final String PARENT_CHATSEND_TEXTMESSAGE ="textMessage";
    public static final String PARENT_CHATSEND_LOGINTYPE ="loginType";


//---------------------------------Parent Chat Fetching----------------------//

    public static final String PARENT_CHATFETCH =APIURL +"School_android/getSingleMessage";
    public static final String PARENT_CHATFETCH_LOGINTYPE="loginType";
    public static final String PARENT_CHATFETCH_SENDERID ="senderId";
    public static final String PARENT_CHATFETCH_RECEIVERID ="receiverId";
    public static final String PARENT_CHATFETCH_SCHOOLID ="schoolId";
    public static final String PARENT_CHATFETCH_CLASSID ="classId";
    public static final String PARENT_CHATFETCH_SECTIONID ="sectionId";


    //----------------------------------Parent_School_Details----------------------//

    public static final String SCHOOL_DETAILS = APIURL+"School_android/";

    public static final String SCHOOL_MOBILENO ="mobile";
    public static final String SCHOOL_TYPE ="loginType";
    public static final String SCHOOL_LOGINID ="loginId";



    //---------------------------------Parent Notice----------------------//

    public static final String PARENT_NOTICE_URL =APIURL +"School_android/noticeList";

    public static final String PARENT_NOTICE_LOGINTYPE ="loginType";
    public static final String PARENT_NOTICE_SCHOOLID ="schoolId";

//---------------------------------testing-----------------------------------//

    public static final String url="http://thememoirs.in/kaffeecup/feader/feed_fetch_individual.php";

    public static final String url_id ="feader_reg_id";



   //---------------------------------Teacher Profile----------------------//

   public static final String TEACHER_PROFILE =APIURL +"School_android/profileFetching";

   public static final String TEACHER_PROFILE_MOBILE ="mobile";
   public static final String TEACHER_PROFILE_LOGINTYPE ="loginType";
   public static final String TEACHER_PROFILE_SCHOOLID ="schoolId";
   public static final String TEACHER_PROFILE_LOGINID ="loginId";




   //--------------------------Teacher_Attendance_details-----------------------//

    public static final String ATTENDANCE_DETAILS=APIURL+"School_android/classTeacher";


    public static final String ATTENDANCE_DETAILS_LOGINTYPE ="loginType";
    public static final String ATTENDANCE_DETAILS_SCHOOLID ="school_Id";
    public static final String ATTENDANCE_DETAILS_CLASSID ="class_Id";
    public static final String ATTENDANCE_DETAILS_SECTIONID ="section_Id";


    //--------------------------Teacher_Attendance_entry-----------------------//

    public static final String ATTENDANCE_ENTRY=APIURL+"School_android/attendenceEntry";


    public static final String ATTENDANCE_ENTRY_LOGINTYPE ="loginType";
    public static final String ATTENDANCE_ENTRY_SCHOOLID ="school_Id";
    public static final String ATTENDANCE_ENTRY_LOGINID ="loginId";
    public static final String ATTENDANCE_ENTRY_SECTIONID ="sectionId";
    public static final String ATTENDANCE_ENTRY_CLASSID ="classId";
    public static final String ATTENDANCE_ENTRY_ABSENTID ="studentAbsentId[]";


    //--------------------------Teacher_Attendance_history-----------------------//

    public static final String ATTENDANCE_HISTORY=APIURL+"School_android/getAbsentListStaff";


    public static final String ATTENDANCE_HISTORY_LOGINTYPE ="loginType";
    public static final String ATTENDANCE_HISTORY_SCHOOLID ="schoolName";
    public static final String ATTENDANCE_HISTORY_LOGINID ="loginId";
    public static final String ATTENDANCE_HISTORY_SECTIONID ="sectionName";
    public static final String ATTENDANCE_HISTORY_CLASSID ="className";
    public static final String ATTENDANCE_HISTORY_DATE ="studentAbsentDate";



    //----------------------------------Teacher_dairy_entry---------------------//

    public static final String DAIRY_ENTRY=APIURL+"School_android/diaryEntry";

    public static final String DAIRY_LOGINID ="loginId";
    public static final String DAIRY_LOGINTYPE ="loginType";
    public static final String DAIRY_SCHOOLID ="schoolId";
    public static final String DAIRY_CLASSID ="classId";
    public static final String DAIRY_SECTIONID ="sectionId";
    public static final String DAIRY_DESCRIPTION ="description";


    //----------------------------------Teacher_dairy_history---------------------//

    public static final String DAIRY_HISTORY=APIURL+"School_android/diaryHistory";

    public static final String DAIRY_HISTORY_LOGINID ="loginId";
    public static final String DAIRY_HISTORY_LOGINTYPE ="loginType";
    public static final String DAIRY_HISTORY_SCHOOLID ="schoolId";
    public static final String DAIRY_HISTORY_CLASSID ="classId";
    public static final String DAIRY_HISTORY_SECTIONID ="sectionId";
    public static final String DAIRY_HISTORY_DATE ="diaryDate";

    //------------------------------Teacher_Homework_Spinner_Class--------------------//

    public static final String SPINNER_CLASS=APIURL+"School_android/classFetching";

    public static final String SPINNER_CLASS_LOGINID ="loginId";
    public static final String SPINNER_CLASS_SCHOOLID ="schoolId";
    public static final String SPINNER_CLASS_LOGINTYPE ="loginType";


    //------------------------------Teacher_Homework_Spinner_Section--------------------//

    public static final String SPINNER_SECTION=APIURL+"School_android/sectionFetching";

    public static final String SPINNER_SECTION_LOGINID ="loginId";
    public static final String SPINNER_SECTION_SCHOOLID ="schoolId";
    public static final String SPINNER_SECTION_LOGINTYPE ="loginType";
    public static final String SPINNER_SECTION_CLASSID ="classlId";


    //------------------------------Teacher_Homework_Spinner_Subject--------------------//

    public static final String SPINNER_SUBJECT=APIURL+"School_android/subjectFetching";

    public static final String SPINNER_SUBJECT_LOGINID ="loginId";
    public static final String SPINNER_SUBJECT_SCHOOLID ="schoolId";
    public static final String SPINNER_SUBJECT_LOGINTYPE ="loginType";

    //-------------------------------------Teacher_HomeWork Entry---------------------//

    public static final String HOMEWORK_ENTRY=APIURL+"School_android/homeworkEntry";

    public static final String HOMEWORK_ENTRY_LOGINTYPE ="loginType ";
    public static final String HOMEWORK_ENTRY_SCHOOLID ="schoolId";
    public static final String HOMEWORK_ENTRY_LOGINID ="loginId";
    public static final String HOMEWORK_ENTRY_CLASSID ="classId";
    public static final String HOMEWORK_ENTRY_SECTIONID ="sectionId";
    public static final String HOMEWORK_ENTRY_SUBJECTID ="subjectId";
    public static final String HOMEWORK_ENTRY_HOMEWORK ="homework";



    //-----------------------------------Teacher_Homework_history--------------------------//

    public static final String HOMEWORK_HISTORY= APIURL+"School_android/homeworkHistory";

    public static final String HOMEWORK_HISTROY_LOGINTYPE="loginType";
    public static final String HOMEWORK_HISTROY_SCHOOLID="schoolId";
    public static final String HOMEWORK_HISTROY_HOMEWORKDATE="homeworkDate";
    public static final String HOMEWORK_HISTROY_LOGINID="loginId";
    public static final String HOMEWORK_HISTROY_CLASSID="classId";
    public static final String HOMEWORK_HISTROY_SECTIONID="sectionId";



    //---------------------------------Teacher Chat Sending----------------------//

    public static final String TEACHER_CHATSEND =APIURL +"School_android/sendSingleMessage";
    public static final String TEACHER_CHATSEND_SCHOOLID="schoolId";
    public static final String TEACHER_CHATSEND_CLASSID ="classId";
    public static final String TEACHER_CHATSEND_SECTIONID ="sectionId";
    public static final String TEACHER_CHATSEND_SENDERID ="senderId";
    public static final String TEACHER_CHATSEND_RECEIVERID ="receiverId";
    public static final String TEACHER_CHATSEND_TEXTMESSAGE ="textMessage";
    public static final String TEACHER_CHATSEND_LOGINTYPE ="loginType";


//---------------------------------Teacher Chat Fetching----------------------//

    public static final String TEACHER_CHATFETCH =APIURL +"School_android/getSingleMessage";
    public static final String TEACHER_CHATFETCH_LOGINTYPE="loginType";
    public static final String TEACHER_CHATFETCH_SENDERID ="senderId";
    public static final String TEACHER_CHATFETCH_RECEIVERID ="receiverId";
    public static final String TEACHER_CHATFETCH_SCHOOLID ="schoolId";
    public static final String TEACHER_CHATFETCH_CLASSID ="classId";
    public static final String TEACHER_CHATFETCH_SECTIONID ="sectionId";

 //----------------------------------Teacher Logout----------------------//

 public static final String TEACHER_Logout = APIURL+"School_android/logout";

 public static final String TEACHER_Logout_MOBILENO ="mobile";
 public static final String TEACHER_Logout_TYPE ="loginType";

    //---------------------------------Holiday Details----------------------//

    public static final String HOLIDAY_DETAILS =APIURL +"School_android/holidayList";

    public static final String HOLIDAY_DETAILS_SCHOOLID  ="schoolId";
    public static final String HOLIDAY_DETAILS_HOLIDAYMONTH ="holidayMonth";


    //----------------------------------Teacher GroupFetch----------------------//

    public static final String ADGroupFetch_Url = APIURL+"School_android/getGroupDetails";

    public static final String ADGroupFetch_Schoolid ="school_Id";
    public static final String ADGroupFetch_Teacherid ="teacher_Id";

    //----------------------------------Teacher GroupMessageFetch----------------------//

    public static final String ADGroupMessageFetch_Url = APIURL+"School_android/getGroupMessage";


    public static final String ADGroupMessageFetch_Groupid ="group_Id";

    //----------------------------------Teacher GroupMessageSending----------------------//

    public static final String ADGroupMessage_Url = APIURL+"School_android/sendGroupMessage";

    public static final String ADGroupMessage_Schoolid ="school_Id";
    public static final String ADGroupMessage_Senderid ="sender_Id";
    public static final String ADGroupMessage_Groupid ="receiver_groupId";
    public static final String ADGroupMessage_Groupmessage ="group_message";


    //----------------------------------Teacher GroupImageuploading----------------------//

    public static final String ProfilephotoGroupUpload_Url = APIURL+"School_android/sendGroupImage";

    public static final String ProfilephotoGroup_Schoolid ="school_Id";
    public static final String ProfilephotoGroupUpload_Senderid ="sender_Id";
    public static final String ProfilephotoGroupUpload_Groupid ="receiver_groupId";
    public static final String ProfilephotoGroup_GroupImage ="groupImage";

    //----------------------------------Teacher GroupImage Fetch----------------------//

    public static final String GroupImageFetch_Url = APIURL+"School_android/getGroupImage";


    public static final String GroupImageFetch_Groupid ="group_Id";


    //----------------------------------Teacher GroupInfo Fetch----------------------//

    public static final String GroupInfo_Url = APIURL+"School_android/getGroupInfo";

    public static final String GroupInfo_Id ="group_Id";

    //----------------------------------Teacher Time Table----------------------//

    public static final String TimeTable_URL = APIURL+"School_android/teacherTimeTable";

    public static final String TimeTable_SCHOOLID ="schoolId";
    public static final String TimeTable_TEACHERID ="teacherId";
    public static final String TimeTable_DAYID ="dayId";


    //----------------------------------Parent_Payment----------------------//

    public static final String PARENT_PAYMENT_URL = APIURL+"School_android/paymentfile";

    //----------------------------------Parent_Payment_Reponse----------------------//

    public static final String PARENT_PAYMENT_RESPONSE_URL = APIURL+"School_android/paymentfile";

    public static final String PAYMENTAMOUNT_RID ="schoolId";
    public static final String PAYMENTAMOUNT_MOBILE ="teacherId";
    public static final String PAYMENTAMOUNT_STATUSOFPAY ="dayId";
    public static final String PAYMENTAMOUNT_TRANSID ="dayId";

    //----------------------------------Teacher Leave Request----------------------//

    public static final String LEAVEMANAGEMENT_URL = APIURL+"School_android/leave_validate";

    public static final String LEAVEMANAGEMENT_TEACHERID ="teacherId";
    public static final String LEAVEMANAGEMENT_FROMDATE ="from_date";
    public static final String LEAVEMANAGEMENT_TODATE ="to_date";
    public static final String LEAVEMANAGEMENT_SCHOOLID ="schoolId";
    public static final String LEAVEMANAGEMENT_CLASSID ="classId";
    public static final String LEAVEMANAGEMENT_SCETIONID ="sectionId";
    public static final String LEAVEMANAGEMENT_LEANEDESC ="leaveDescription";

    //----------------------------------Teacher Leave History----------------------//

    public static final String LEAVEMANAGEMENTHistory_URL = APIURL+"School_android/teacherLevaeStatus";

    public static final String LEAVEMANAGEMENTHistory_TEACHERID ="teacherId";
    public static final String LEAVEMANAGEMENTHistory_SCHOOLID ="schoolId";
    public static final String LEAVEMANAGEMENTHistory_CLASSID ="classId";
    public static final String LEAVEMANAGEMENTHistory_SCETIONID ="sectionId";


    //----------------------------------Parent Fees Info----------------------//

    public static final String PARENTFEESINFO_URL = APIURL+"School_android/installment";

    public static final String PARENTFEESINFO_SchoolID ="school_id";
    public static final String PARENTFEESINFO_StudentID ="student_id";
    public static final String PARENTFEESINFO_ClassID ="class_id";




    //----------------------------------Parent Fees History----------------------//

    public static final String PARENTFEESHIS_URL = APIURL+"School_android/installmentPaymentHistory";

    public static final String PARENTFEESHIS_StudentID ="studentId";

    //----------------------------------Parent Payment----------------------//

    public static final String PARENTPAYMENT_URL = APIURL+"School_android/installmentPayment";

    public static final String PARENTPAYMENT_schoolId ="schoolId";
    public static final String PARENTPAYMENT_studentId ="studentId";
    public static final String PARENTPAYMENT_installmentId ="installmentId";
    public static final String PARENTPAYMENT_transationId ="transationId";
    public static final String PARENTPAYMENT_transactionStatus ="transactionStatus";
    public static final String PARENTPAYMENT_transtactionDate ="transtactionDate";
    public static final String PARENTPAYMENT_paymentType ="paymentType";
    public static final String PARENTPAYMENT_amount ="amount";

}
