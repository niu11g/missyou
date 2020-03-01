package com.lin.missyou;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

//默认扫描与启动类同级以及下级的类
//@Import(HeroConfiguration.class)
//@Import(LOLConfigurationSelector.class)
//@EnableLOLConfiguration
public class TestApplication {
    private String mJSONSource;
    private String mXML;
    private String mPath = "//Volumes//D//x.txt";
    private String mSavedXML = "//Volumes//D//result.xml";
    private void getSource(String name) throws IOException
    {
        File file = new File(mPath);
//        /Volumes/D/x.txt
        FileInputStream inputStream = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        char[] buffer = new char[(int) file.length()];
        reader.read(buffer, 0, buffer.length);
        inputStream.close();
        mJSONSource = new String(buffer);
        //System.out.println("String length: " + mJSONSource);
    }

    public void run() throws IOException
    {
        getSource(mPath);
        parse();
        saveFile();
    }
    private void parse()
    {
        JSON json = JSONSerializer.toJSON(mJSONSource);

        XMLSerializer xmlSerializer = new XMLSerializer();
        xmlSerializer.setTypeHintsEnabled( false );
        xmlSerializer.setRootName("body" );
        mXML = xmlSerializer.write( json );
        System.out.println(mXML);

    }

    private void saveFile()
    {
        try
        {
            FileOutputStream fos=new FileOutputStream(new File(mSavedXML));
            OutputStreamWriter osw=new OutputStreamWriter(fos);
            osw.write(mXML);
            osw.flush();
            osw.close();
        }
        catch(Exception ee)
        {
            ee.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
//        String str = String.valueOf(100.5);
//        float f2=(float)100.48;
//        BigDecimal b2  =   new BigDecimal(f2);
//        f2=b2.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
//        System.out.printf("str"+str);

//        ConfigurableApplicationContext context = new SpringApplicationBuilder(TestApplication.class)
////                .web(WebApplicationType.NONE)
////                .run(args);
////        ISkill iSkill = (ISkill) context.getBean("diana");
////        iSkill.r();

        TestApplication tool = new TestApplication();
        tool.run();

    }

}
