package com.hz.pxp.common;

public class CommonUtils {
    public static String encryptionString(String input,String key){
        String str = "";
        int ch;
        if(key.length() == 0){
            return input;
        }else if(!input.equals(null)){
            for(int i = 0,j = 0;i < input.length();i++,j++){
                if(j > key.length() - 1){
                    j = j % key.length();
                }
                ch = input.codePointAt(i) + key.codePointAt(j);
                if(ch > 65535){
                    ch = ch % 65535;//ch - 33 = (ch - 33) % 95 ;
                }
                str += (char)ch;
            }
        }
        return str;
    }

    public static String decryptionString(String input,String key){
        String str = "";
        int ch;
        if(key.length() == 0){
            return input;
        }else if(!input.equals(key)){
            for(int i = 0,j = 0;i < input.length();i++,j++){
                if(j > key.length() - 1){
                    j = j % key.length();
                }
                ch = (input.codePointAt(i) + 65535 - key.codePointAt(j));
                if(ch > 65535){
                    ch = ch % 65535;//ch - 33 = (ch - 33) % 95 ;
                }
                str += (char)ch;
            }
        }
        return str;
    }
}
