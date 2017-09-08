package indi.sword.util.base.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.FileDataSource;

public class TextFile extends FileDataSource {  
    private static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";  
    private final static Pattern pattern = Pattern.compile("^text/");  
      
    private String contentType;  
    private int head_count=0;  
      
    public TextFile(File file){
        super(file);
        determinateType();
    }  
      
    private void determinateType(){  
        contentType = super.getContentType();  
        Matcher m = pattern.matcher(contentType);  
        if(m.find() && getFile().exists() && getFile().isFile()){  
            InputStream in = null;  
            try {
                in = new FileInputStream(getFile());  
                if(is_utf_8(in)){  
                    contentType = contentType + "; charset=utf-8";  
                    head_count = 3;  
                } else {  
                    contentType = DEFAULT_CONTENT_TYPE;  
                }  
            } catch (IOException e) {  
                contentType = DEFAULT_CONTENT_TYPE;  
            } finally {  
                try {  
                    if(in!=null) {  
                        in.close();  
                    }  
                } catch (IOException e1) {  
                }  
            }  
        }  
    }  

    public TextFile(String s) {  
        this(new File(s));
    }  

    @Override  
    public InputStream getInputStream() throws IOException {  
        InputStream in = super.getInputStream();  
        for(int i=0;i<head_count;i++) in.read();  
        return in;  
    }  

    @Override  
    public String getContentType() {  
        return contentType;  
    }  
      
    private boolean is_utf_8(InputStream in) throws IOException{  
        byte[] b3 = new byte[3];  
        int count = in.read(b3, 0, 3);  
        if(count<3) return false;  
        int b0 = b3[0] & 0xFF;  
        int b1 = b3[1] & 0xFF;  
        int b2 = b3[2] & 0xFF;  
        if (b0 == 0xEF && b1 == 0xBB && b2 == 0xBF){  
            return true;  
        }  
        return false;  
    }  
      
}  