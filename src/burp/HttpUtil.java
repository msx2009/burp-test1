package burp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class HttpUtil {
    final static String url = "http://localhost:1111/sendData";
    final static String params = "{\"token\":\"12345\","
            + "\"appId\":\"20180628173051169c85d965d164ed9ab1281d22ff350ec19\"" + "\"appName\":\"test33\""
            + "\"classTopic\":\"112\"" + "\"eventTag\":\"22\"" + "}";

    public static void main(String[] args) {

        String res = post(url, params);
        System.out.println(res);

    };
    public static String doGet(String httpurl) {
            HttpURLConnection connection = null;
            InputStream is = null;
            BufferedReader br = null;
            String result = null;// 返回结果字符串
            try {
                // 创建远程url连接对象
                URL url = new URL(httpurl);
                // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
                connection = (HttpURLConnection) url.openConnection();
                // 设置连接方式：get
                connection.setRequestMethod("GET");
                // 设置连接主机服务器的超时时间：15000毫秒
                connection.setConnectTimeout(15000);
                // 设置读取远程返回的数据时间：60000毫秒
                connection.setReadTimeout(60000);
                // 发送请求
                connection.connect();
                // 通过connection连接，获取输入流
                if (connection.getResponseCode() == 200) {
                    is = connection.getInputStream();
                    // 封装输入流is，并指定字符集
                    br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    // 存放数据
                    StringBuffer sbf = new StringBuffer();
                    String temp = null;
                    while ((temp = br.readLine()) != null) {
                        sbf.append(temp);
                        sbf.append("\r\n");
                    }
                    result = sbf.toString();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // 关闭资源
                if (null != br) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (null != is) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                connection.disconnect();// 关闭远程连接
            }

            return result;
        }

        public static String doPost(String httpUrl, String param) {

            HttpURLConnection connection = null;
            InputStream is = null;
            OutputStream os = null;
            BufferedReader br = null;
            String result = null;
            try {
                URL url = new URL(httpUrl);
                // 通过远程url连接对象打开连接
                connection = (HttpURLConnection) url.openConnection();
                // 设置连接请求方式
                connection.setRequestMethod("POST");
                // 设置连接主机服务器超时时间：15000毫秒
                connection.setConnectTimeout(15000);
                // 设置读取主机服务器返回数据超时时间：60000毫秒
                connection.setReadTimeout(60000);

                // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
                connection.setDoOutput(true);
                // 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
                connection.setDoInput(true);
                // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
                //connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("Content-Type", "application/json"); 
                // 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
                //connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
                // 通过连接对象获取一个输出流
                os = connection.getOutputStream();
                // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
                os.write(param.getBytes());
                // 通过连接对象获取一个输入流，向远程读取
                
                if (connection.getResponseCode() == 200) {

                    is = connection.getInputStream();
                    // 对输入流对象进行包装:charset根据工作项目组的要求来设置
                    br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                    StringBuffer sbf = new StringBuffer();
                    String temp = null;
                    // 循环遍历一行一行读取数据
                    while ((temp = br.readLine()) != null) {
                        sbf.append(temp);
                        sbf.append("\r\n");
                    }
                    result = sbf.toString();
                }
                
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // 关闭资源
                if (null != br) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (null != os) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (null != is) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // 断开与远程地址url的连接
                connection.disconnect();
            }
            return result;
        }
 
    /**
     * 发送HttpPost请求
     * 
     * @param strURL
     *            服务地址
     * @param params
     *            json字符串,例如: "{ \"id\":\"12345\" }" ;其中属性名必须带双引号<br/>
     * @return 成功:返回json字符串<br/>
     */
    public static String post(String strURL, String params) {
        System.out.println(strURL);
        System.out.println(params);
        BufferedReader reader = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(strURL);// 创建连接
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            // connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.setReadTimeout(1);
            connection.connect();
            //一定要用BufferedReader 来接收响应， 使用字节来接收响应的方法是接收不到内容的
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(params);
            out.flush();
            out.close();
            // 读取响应
           
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            String res = "";
          while ((line = reader.readLine()) != null) {
                res += line;
            }
            reader.close();
            
            connection.disconnect();
            
       
            return null;
            
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
           // e.printStackTrace();
            connection.disconnect();
            return null;
        }
      
    }

}