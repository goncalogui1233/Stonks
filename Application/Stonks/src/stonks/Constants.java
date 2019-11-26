package stonks;

public interface Constants {
    /*Application window properties*/
    public static final String APP_TITLE = "Stonks";
    public static final int APP_WIDTH = 1280;
    public static final int APP_HEIGHT = 720;
    
    /*DialogBox window properties*/
    public static final int DBOX_WIDTH = 400;
    public static final int DBOX_HEIGHT = 200;
    public static enum DBOX_TYPE {SUCCESS, ERROR, CONFIRM}
    public static enum DBOX_RETURN {X_CLOSED, NO, YES, OK, EXCEPTION}
    public static final int DBOX_BUTTON_HEIGHT = 40;
    public static enum DBOX_CONTENT {
        /*ERROR MESSAGES*/
        ERROR_PROFILE_LIMIT("Limit of profiles reached", "You can only have 6 profiles, delete one to register another"),
        
        /*SUCCESS MESSAGES*/
        SUCCESS_CREATE_PROFILE("Limit of profiles reached", "You can only have 6 profiles, delete one to register another"),
        
        /*CONFIRM MESSAGES*/
        CONFIRM_DELETE_PROFILE("Deleting profile \"{}\"", "This action will delete this profile permanently..."),
        CONFIRM_DELETE_GOAL("Deleting goal \"{}\"", "This action will delete this goal permanently...");
        
        private final String subTitle;
        private String newSubTitle;
        private final String text;
        
        DBOX_CONTENT(String subTitle, String text){
            this.subTitle = newSubTitle = subTitle;
            this.text = text;
        }
        
        public String getSubTitle(){
            return newSubTitle;
        }
        
        public String getText(){
            return text;
        }
        
        public String setExtra(String extra){
            if(!subTitle.contains("{}"))
                return null;
            
            newSubTitle = subTitle.replace("{}", extra);
            
            return newSubTitle;
        }
    }
    
    /*SideMenu properties*/
    public static final int SIDEMENU_WIDTH = 256;
    public static final int SIDEMENU_HEIGHT = APP_HEIGHT;
    
    /*SideProfileBar properties*/
    public static final int SIDEPROFILEBAR_WIDTH = 115;
    public static final int SIDEPROFILEBAR_HEIGHT = APP_HEIGHT;
    
    /*General*/
    public static final int MAX_PROFILES = 6;
    public static enum VALIDATE {
        OK,/*Everything other than this is an error*/
        MIN_CHAR,
        MAX_CHAR,
        NEGATIVE_OR_ZERO,
        EMPTY,
        MIN_NUMBER,
        MAX_NUMBER,
        MIN_DATE,
        FORMAT,
        UNDEFINED;
    };
    
    public static enum SECURITY_QUESTIONS {
        /*Empty*/
        PROTOTYPE("Select One"),
        
        /*Possible security questions*/
        CHILDHOOD_NICKNAME("What was your childhood nickname?"),
        TEACHER_NAME("What is the first name of your first grade teacher?"),
        TOWN_BORN("What was the name of the town where you were born?"),
        FIRST_PET("What was the name of your first pet?"),
        FICTIONAL_CHAR("Who is your favorite fictional character?");
        
        private final String question;
        
        SECURITY_QUESTIONS(String question){
            this.question = question;
        }
        
        public String getQuestion(){
            return question;
        }
    }
    
    /*Profile*/
    public static enum PROFILE_FIELD {FIRST_NAME, LAST_NAME, SECURITY_QUESTION, SECURITY_ANSWER, PASSWORD, COLOR};
    public static final int PROFILE_EDIT_VIEW_WIDTH = APP_WIDTH - SIDEMENU_WIDTH;
    public static final int PROFILE_EDIT_VIEW_HEIGHT = APP_HEIGHT;
    public static final int PROFILE_AUTH_WIDTH = APP_WIDTH - SIDEPROFILEBAR_WIDTH;
    public static final int PROFILE_AUTH_HEIGHT = APP_HEIGHT;
    public static final int PROFILE_AUTH_BOX_WIDTH = 450;
    public static final int PROFILE_AUTH_BOX_LOGIN_HEIGHT = 300;
    public static final int PROFILE_AUTH_BOX_REGISTER_HEIGHT = 600;
    public static final int PROFILE_AUTH_BOX_RECOVER_PASSWORD_HEIGHT = 400;
    
    /*Dashboard*/
    
    /*Goal*/
    public static enum GOAL_FIELD {NAME, OBJECTIVE, DEADLINE};
    
    /*Wallet*/
    
}
