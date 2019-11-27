package stonks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.ProfileModel;

public class StonksData implements Serializable {
    private static final long serialVersionUID = 1L;

    private HashMap<Integer, ProfileModel> listProfiles;
    private ProfileModel authProfile;

    public HashMap<Integer, ProfileModel> getListProfiles() {
        return listProfiles;
    }

    public void setListProfiles(HashMap<Integer, ProfileModel> listProfiles) {
        this.listProfiles = listProfiles;
    }

    public ProfileModel getAuthProfile() {
        return authProfile;
    }

    public void setCurrentProfile(ProfileModel authProfile) {
        this.authProfile = authProfile;
    }

    public StonksData() {
        this.listProfiles = new HashMap<>();
    }

    public StonksData loadDatabase() {

        File f = new File("data.bin"); //Colocar constante
        FileInputStream fin;
        ObjectInputStream ois;

        if (!f.exists()) {
            try {
                f.createNewFile();
                System.out.println("Create");
                return this;
            } catch (IOException ex) {
                return null;
            }
        } else {

            try {

                FileInputStream fileIn = new FileInputStream("data.bin");
                try (ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
                    Object obj = objectIn.readObject();

                    StonksData data = (StonksData) obj;

                    if (data.listProfiles == null) {
                        data.listProfiles = new HashMap<>();
                    }
                    
                    data.authProfile = null;

                    return data;
                }

            } catch (Exception ex) {
                return this;
            }
        }

        //return null;
    }

    public void updateDatabase() {
        FileOutputStream fout;
        try {
            fout = new FileOutputStream("data.bin");

            ObjectOutputStream oos;
            try {
                oos = new ObjectOutputStream(fout);
                oos.writeObject(this);

                oos.close();
                fout.close();
            } catch (IOException ex) {
                Logger.getLogger(StonksData.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(StonksData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
