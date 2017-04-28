package com.kxiang.quick.socket;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/4/5 9:12
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * 控制台聊天程序
 * 客户端应用程序
 * @author Jacob
 *
 */
public class chatClient
{
    //客户端用于与服务端连接的Socket
    private Socket clientSocket;

    /**
     * 构造方法，客户端初始化
     */
    public chatClient()
    {
        try
        {
			/*
			* socket(String host, int port)
			* 地址： IP地址，用来定位网络上的计算机
			* 端口： 用来找到远端计算机上用来连接的服务端应用程序
			*/
            clientSocket = new Socket("localhost",12580);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 客户端昵称验证方法
     */
    private void inputNickName(Scanner scan) throws  Exception
    {
        String nickName = null;
        //创建输出流
        PrintWriter pw = new PrintWriter(
                new OutputStreamWriter(clientSocket.getOutputStream(),
                        "UTF-8"),true);
        //创建输入流
        BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        clientSocket.getInputStream(),"UTF-8"));

        while(true)
        {
            System.out.println("请创建您的昵称：");
            nickName = scan.nextLine();
            if (nickName.trim().equals(""))
            {
                System.out.println("昵称不得为空");
            }
            else
            {
                pw.println(nickName);
                String pass = br.readLine();
                if(pass!=null&&!pass.equals("OK"))
                {
                    System.out.println("昵称已经被占用，请更换！");
                }
                else
                {
                    System.out.println("你好！"+nickName+"可以开始聊天了");
                    break;
                }
            }
        }
    }

    /*
    * 客户端启动的方法
    */
    public void start()
    {
        try
        {
			/*
			* 创建Scanner，读取用户输入内容
			* 目的是设置客户端的昵称
			*/
            Scanner scanner = new Scanner(System.in);
            inputNickName(scanner);

			/*
			* 将用于接收服务器端发送过来的信息的线程启动
			*/
            Runnable run = new GetServerMsgHandler();
            Thread t = new Thread(run);
            t.start();

			/*
			* 建立输出流，给服务端发信息
			*/
            OutputStream os = clientSocket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os,"UTF-8");
            PrintWriter pw = new PrintWriter(osw,true);

            while(true)
            {
                pw.println(scanner.nextLine());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(clientSocket !=null)
            {
                try
                {
                    clientSocket.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 该线程体用来循环读取服务端发送过来的信息
     * 并输出到客户端的控制台
     */
    class GetServerMsgHandler implements Runnable
    {
        @Override
        public void run()
        {
            try
            {
                InputStream is = clientSocket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is,"UTF-8");
                BufferedReader br = new BufferedReader(isr);
                String msgString = null;
                while((msgString = br.readLine())!= null)
                {
                    System.out.println("服务端提示："+ msgString);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        chatClient client = new chatClient();
        client.start();
    }
}