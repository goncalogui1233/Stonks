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
    private ProfileModel currentProfile;

    public HashMap<Integer, ProfileModel> getListProfiles() {
        return listProfiles;
    }

    public void setListProfiles(HashMap<Integer, ProfileModel> listProfiles) {
        this.listProfiles = listProfiles;
    }

    public ProfileModel getCurrentProfile() {
        return currentProfile;
    }

    public void setCurrentProfile(ProfileModel currentProfile) {
        this.currentProfile = currentProfile;
    }
    
    

    public StonksData() {
        
    }

    public StonksData loadDatabase() {
        File f = new File("data.bin"); //Colocar constante
        if (!f.exists()) {
            try {
                f.createNewFile();
                System.out.println("Create");
            } catch (IOException ex) {
                return null;
            }
        } else {
            FileInputStream fin;
            try {
                fin = new FileInputStream("data.bin");
                ObjectInputStream ois;
                try {
                    ois = new ObjectInputStream(fin);
                    try {
                        StonksData data;
                        data = (StonksData) ois.readObject();

                        ois.close();
                        fin.close();
                        return data;
                        
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(StonksData.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(StonksData.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(StonksData.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
        
        return null;
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
