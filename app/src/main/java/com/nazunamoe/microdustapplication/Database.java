package com.nazunamoe.microdustapplication;

import android.app.Application;
import android.util.Log;

/**
 * Created by nazunamoe on 2018-05-30.
 */

public class Database{
    LEDColor[] custom = new LEDColor[3];
    LEDColor[] preset1 = new LEDColor[8];
    LEDColor[] preset2 = new LEDColor[8];
    LEDColor[] preset3 = new LEDColor[8];
    LEDColor[] preset4 = new LEDColor[8];
    double longitude;
    double latitude;

    public LEDColor getpreset(int select, int number){
        if(select == 0) {
            if(number == 0) {
                return preset1[0];
            }else if(number == 1) {
                return preset1[1];
            }else if(number == 2) {
                return preset1[2];
            }else if(number == 3) {
                return preset1[3];
            }else if(number == 4) {
                return preset1[4];
            }else if(number == 5) {
                return preset1[5];
            }else if(number == 6) {
                return preset1[6];
            }else if(number == 7) {
                return preset1[7];
            }
        }else if(select == 1) {
            if(number == 0) {
                return preset2[0];
            }else if(number == 1) {
                return preset2[1];
            }else if(number == 2) {
                return preset2[2];
            }else if(number == 3) {
                return preset2[3];
            }else if(number == 4) {
                return preset2[4];
            }else if(number == 5) {
                return preset2[5];
            }else if(number == 6) {
                return preset2[6];
            }else if(number == 7) {
                return preset2[7];
            }
        }else if(select == 2) {
            if(number == 0) {
                return preset3[0];
            }else if(number == 1) {
                return preset3[1];
            }else if(number == 2) {
                return preset3[2];
            }else if(number == 3) {
                return preset3[3];
            }else if(number == 4) {
                return preset3[4];
            }else if(number == 5) {
                return preset3[5];
            }else if(number == 6) {
                return preset3[6];
            }else if(number == 7) {
                return preset3[7];
            }
        }else if(select == 3) {
            if(number == 0) {
                return preset4[0];
            }else if(number == 1) {
                return preset4[1];
            }else if(number == 2) {
                return preset4[2];
            }else if(number == 3) {
                return preset4[3];
            }else if(number == 4) {
                return preset4[4];
            }else if(number == 5) {
                return preset4[5];
            }else if(number == 6) {
                return preset4[6];
            }else if(number == 7) {
                return preset4[7];
            }
        }else if(select == 4) {
            if(number == 0) {
                return custom[0];
            }else if(number == 1) {
                return custom[1];
            }else if(number == 2) {
                return custom[2];
            }
        }else
            return null;
        return null;
    }

    public void setpreset(int select, int number, LEDColor value){
        Log.d("Test","Select = "+select+",Number = "+number+",Red = "+value.red+",Green = "+value.green+",Blue = "+value.blue);
        if(select == 0) {
            if(number == 0) {
                preset1[0] = value;
            }else if(number == 1) {
                preset1[1] = value;
            }else if(number == 2) {
                preset1[2] = value;
            }else if(number == 3) {
                preset1[3] = value;
            }else if(number == 4) {
                preset1[4] = value;
            }else if(number == 5) {
                preset1[5] = value;
            }else if(number == 6) {
                preset1[6] = value;
            }else if(number == 7) {
                preset1[7] = value;
            }
        }else if(select == 1) {
            if(number == 0) {
                preset2[0] = value;
            }else if(number == 1) {
                preset2[1] = value;
            }else if(number == 2) {
                preset2[2] = value;
            }else if(number == 3) {
                preset2[3] = value;
            }else if(number == 4) {
                preset2[4] = value;
            }else if(number == 5) {
                preset2[5] = value;
            }else if(number == 6) {
                preset2[6] = value;
            }else if(number == 7) {
                preset2[7] = value;
            }
        }else if(select == 2) {
            if(number == 0) {
                preset3[0] = value;
            }else if(number == 1) {
                preset3[1] = value;
            }else if(number == 2) {
                preset3[2] = value;
            }else if(number == 3) {
                preset3[3] = value;
            }else if(number == 4) {
                preset3[4] = value;
            }else if(number == 5) {
                preset3[5] = value;
            }else if(number == 6) {
                preset3[6] = value;
            }else if(number == 7) {
                preset3[7] = value;
            }
        }else if(select == 3) {
            if(number == 0) {
                preset4[0] = value;
            }else if(number == 1) {
                preset4[1] = value;
            }else if(number == 2) {
                preset4[2] = value;
            }else if(number == 3) {
                preset4[3] = value;
            }else if(number == 4) {
                preset4[4] = value;
            }else if(number == 5) {
                preset4[5] = value;
            }else if(number == 6) {
                preset4[6] = value;
            }else if(number == 7) {
                preset4[7] = value;
            }
        }else if(select == 4) {
            if(number == 0) {
                preset1[0] = value;
            }else if(number == 1) {
                preset1[1] = value;
            }else if(number == 2) {
                preset1[2] = value;
            }
        }
    }

    private static Database instance = null;

    public static synchronized Database getInstance(){
        if(null == instance){
            instance = new Database();
        }
        return instance;
    }
}
