package com.hjw.util;
import java.util.Collections;  
import java.util.Enumeration;  
import java.util.LinkedHashSet;  
import java.util.Properties;  
import java.util.Set;  
   
/**
 *  
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.util   
     * @Description:  
     * @author: zt    
     * @date:   2017年8月22日 下午4:44:27   
     * @version V2.0.0.0
 */
public class OrderedProper extends Properties {  
   
    private static final long serialVersionUID = -4627607243846121965L;  
       
    private final LinkedHashSet<Object> keys = new LinkedHashSet<Object>();  
   
    public Enumeration<Object> keys() {  
        return Collections.<Object> enumeration(keys);  
    }  
   
    public Object put(Object key, Object value) {  
        keys.add(key);  
        return super.put(key, value);  
    }  
   
    public Set<Object> keySet() {  
        return keys;  
    }  
   
    public Set<String> stringPropertyNames() {  
        Set<String> set = new LinkedHashSet<String>();  
   
        for (Object key : this.keys) {  
            set.add((String) key);  
        }  
   
        return set;  
    }  
}