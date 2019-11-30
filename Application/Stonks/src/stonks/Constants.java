package stonks;

public interface Constants {

    /*Application window properties*/
    public static final String APP_TITLE = "Stonks";
    public static final int APP_WIDTH = 1280;
    public static final int APP_HEIGHT = 720;
    public static final int APP_REAL_HEIGHT = APP_HEIGHT - 25/*Height of windows bar*/;
    
    /*DialogBox window properties*/
    public static final int DBOX_WIDTH = 400;
    public static final int DBOX_HEIGHT = 200;

    public static enum DBOX_TYPE {
        SUCCESS, ERROR, CONFIRM
    }

    public static enum DBOX_RETURN {
        X_CLOSED, NO, YES, OK, EXCEPTION
    }
    public static final int DBOX_BUTTON_HEIGHT = 40;

    public static enum DBOX_CONTENT {
        /*ERROR MESSAGES*/
        ERROR_PROFILE_LIMIT("Limit of profiles reached", "You can only have 6 profiles, delete one to register another"),
        ERROR_GOAL_CREATE("Unable to create the goal", "An error occurred while processing your request. The goal wasn't created."),
        ERROR_GOAL_DELETE("Unable to delete the goal", "An error occurred while processing your request. The goal wasn't deleted."),
        ERROR_RECOVER_PASSWORD("Invalid answer", "Try again"),
        ERROR_LOGIN("Invalid password", "Try again"),
      
        /*SUCCESS MESSAGES*/
        /*To be changed*/ SUCCESS_CREATE_PROFILE("Limit of profiles reached", "You can only have 6 profiles, delete one to register another"),
        SUCCESS_GOAL_CREATE("Goal Created", "Your goal was successfully created!"),
        SUCCESS_GOAL_DELETE("Goal Deleted", "Your goal was successfully deleted!"),
        SUCCESS_RECOVER_PASSWORD("Valid answer", "Your password is: \"{}\""),
        
        /*CONFIRM MESSAGES*/
        CONFIRM_DELETE_PROFILE("Deleting profile \"{}\"", "This action will delete this profile permanently..."),
        CONFIRM_DELETE_GOAL("Deleting goal \"{}\"", "This action will delete this goal permanently...");

        private final String subTitle;
        private String newSubTitle;
        private final String text;
        private String newText;
        
        DBOX_CONTENT(String subTitle, String text){
            this.subTitle = newSubTitle = subTitle;
            this.text = newText = text;
        }

        public String getSubTitle() {
            return newSubTitle;
        }
      
        public String getText(){
            return newText;
        }
        
        public String setSubExtra(String extra){
            if(!subTitle.contains("{}"))
                return null;
            }

            newSubTitle = subTitle.replace("{}", extra);

            return newSubTitle;
        }
        
        public String setTextExtra(String extra){
            if(!text.contains("{}"))
                return null;
            
            newText = text.replace("{}", extra);
            
            return newText;
        }
    }

    /*SideMenu properties*/
    public static final int SIDEMENU_WIDTH = 256;
    public static final int SIDEMENU_HEIGHT = APP_REAL_HEIGHT;
    
    /*SideProfileBar properties*/
    public static final int SIDEPROFILEBAR_WIDTH = 115;
    public static final int SIDEPROFILEBAR_HEIGHT = APP_REAL_HEIGHT;

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
        INVALID_QUESTION,
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

        SECURITY_QUESTIONS(String question) {
            this.question = question;
        }

        public String getQuestion() {
            return question;
        }
    }

    /*Profile*/
    public static enum PROFILE_FIELD {
        FIRST_NAME, LAST_NAME, SECURITY_QUESTION, SECURITY_ANSWER, PASSWORD, COLOR
    };
    public static final int PROFILE_EDIT_VIEW_WIDTH = APP_WIDTH - SIDEMENU_WIDTH;
    public static final int PROFILE_EDIT_VIEW_HEIGHT = APP_REAL_HEIGHT;
    public static final int PROFILE_AUTH_WIDTH = APP_WIDTH - SIDEPROFILEBAR_WIDTH;
    public static final int PROFILE_AUTH_HEIGHT = APP_REAL_HEIGHT;
    public static final int PROFILE_AUTH_BOX_WIDTH = 450;
    public static final int PROFILE_AUTH_BOX_LOGIN_HEIGHT = 300;
    public static final int PROFILE_AUTH_BOX_REGISTER_HEIGHT = 600;
    public static final int PROFILE_AUTH_BOX_RECOVER_PASSWORD_HEIGHT = 300;
    
    /*Dashboard*/

    /*Goal*/
    public static enum GOAL_FIELD {
        NAME, OBJECTIVE, DEADLINE
    };
    
    public static final int GOAL_NAME_MAXCHARS = 50;
    public static final int GOAL_NAME_MINCHARS = 1;
    public static final int GOAL_OBJECTIVE_MINVALUE = 1;
    public static final int GOAL_OBJECTIVE_MAXVALUE = 999999999;
    public static final int GOAL_VIEW_WIDTH = APP_WIDTH - SIDEMENU_WIDTH;
    public static final int GOAL_VIEW_HEIGHT = APP_HEIGHT;
    public static final int GOALS_CONTAINER_WIDTH = GOAL_VIEW_WIDTH - 160 - 10;
    public static final int GOAL_BOX_WIDTH = GOALS_CONTAINER_WIDTH - 40;
    public static final int GOAL_BOX_HEIGHT = 125;

    /*Wallet*/

    /*Property Change Events*/
    public static enum STONKS_EVENT {GOTO_GOAL_VIEW};
    public static enum AUTH_EVENT {CREATE_PROFILE, UPDATE_SELECTION, GOTO_LOGIN, GOTO_REGISTER, GOTO_RECOVER_PASSWORD};
    public static enum GOAL_EVENT {CREATE_GOAL, DELETE_GOAL};
    public static enum PROFILE_EVENT {};
    public static enum DASHBOARD_EVENT {};
}
