
package com.test.pdf;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.AcroFields.Item;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class GeneratePdfTemplate {
    public static void main(String[] args) {
        //1 要填充的数据
        Map<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("operBranch", "开户银行");
        paraMap.put("address", "杭州市滨江区");
        paraMap.put("Name", "周瑶");
        paraMap.put("clientName", "客户名称周瑶");
        paraMap.put("prdCode", "ceshi01");
        paraMap.put("BankAcc", "6227002013321545");
        paraMap.put("prdName", "理财产品01");
        try {
            //2 读入pdf表单
            PdfReader reader = new PdfReader("d:/test.pdf");
            //3 根据表单生成一个新的pdf
            PdfStamper ps = new PdfStamper(reader,
                new FileOutputStream("d:/newPdf.pdf"));
            //4 获取pdf表单

            AcroFields s = ps.getAcroFields();

            //5给表单添加中文字体 
            BaseFont bfChinese = BaseFont.createFont("STSong-Light",
                "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            s.addSubstitutionFont(bfChinese);

            //6遍历pdf表单表格，同时给表格赋值
            Map<String, Item> fieldMap = s.getFields();
            Set<Map.Entry<String, Item>> set = fieldMap.entrySet();
            Iterator<Map.Entry<String, Item>> iterator = set.iterator();
            
            while (iterator.hasNext()) {
                Entry<String, Item> entry = iterator.next();
                String key = entry.getKey();
                if (paraMap.get(key) != null) {
                    s.setFieldProperty(key,"textsize",10f,null);
                    s.setField(key, paraMap.get(key));
                }
            }
            ps.setFormFlattening(true);
            ps.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
