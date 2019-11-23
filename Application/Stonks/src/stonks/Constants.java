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
        CONFIRM_DELETE_PROFILE("Deleting profile \"{}\"", "This action will delete this profile permanently...");
        
        String subTitle;
        String newSubTitle;
        String text;
        
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
    
    /*General*/
    public static final String[] SECURITY_QUESTIONS_LIST = new String[]{
        "Select One",
        "What was your childhood nickname?",
        "What is the first name of your first grade teacher?",
        "What was the name of the town where you were born?",
        "What was the name of your first pet?",
        "Who is your favorite fictional character?"};
    
    /*Profile*/
    public static final int PROFILE_EDIT_VIEW_WIDTH = APP_WIDTH - SIDEMENU_WIDTH;
    public static final int PROFILE_EDIT_VIEW_HEIGHT = APP_HEIGHT;
    
    /*Dashboard*/
    
    /*Goal*/
    
    /*Wallet*/
    
}
