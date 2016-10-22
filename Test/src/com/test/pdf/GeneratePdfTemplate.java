
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
        //1 Ҫ��������
        Map<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("operBranch", "��������");
        paraMap.put("address", "�����б�����");
        paraMap.put("Name", "����");
        paraMap.put("clientName", "�ͻ���������");
        paraMap.put("prdCode", "ceshi01");
        paraMap.put("BankAcc", "6227002013321545");
        paraMap.put("prdName", "��Ʋ�Ʒ01");
        try {
            //2 ����pdf��
            PdfReader reader = new PdfReader("d:/test.pdf");
            //3 ���ݱ�����һ���µ�pdf
            PdfStamper ps = new PdfStamper(reader,
                new FileOutputStream("d:/newPdf.pdf"));
            //4 ��ȡpdf��

            AcroFields s = ps.getAcroFields();

            //5��������������� 
            BaseFont bfChinese = BaseFont.createFont("STSong-Light",
                "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            s.addSubstitutionFont(bfChinese);

            //6����pdf�����ͬʱ�����ֵ
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
