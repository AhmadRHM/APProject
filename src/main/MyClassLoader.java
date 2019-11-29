package main;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MyClassLoader extends ClassLoader {
    public Class myLoadClass(File file, String className){
        try {
            URL url = file.toURI().toURL();
            byte[] classData;
            classData = getClassData(url, className);
            Class clazz = defineClass(className, classData, 0, classData.length);
            resolveClass(clazz);
            return clazz;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public Class myLoadClass(byte[] classData, String className) {
        Class clazz = defineClass(className, classData, 0, classData.length);
        resolveClass(clazz);
        return clazz;
    }

    public byte[] getClassData(URL url, String className){
        try {
            URLConnection connection;
            connection = url.openConnection();
            InputStream input = connection.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int data = input.read();
            while (data != -1) {
                buffer.write(data);
                data = input.read();
            }
            input.close();
            return buffer.toByteArray();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
