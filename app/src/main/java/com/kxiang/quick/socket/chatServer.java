package com.kxiang.quick.socket;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/4/5 9:13
 */

/**
 * 控制台聊天程序
 * 服务端应用程序
 *
 * @author Jacob
 */
public class chatServer {
//    /**
//     * ServerSocket 是运行在服务端的Socket
//     * 用来监听端口，等待客户端的连接，
//     * 一旦连接成功就会返回与该客户端通信的Socket
//     */
//    private ServerSocket serverSocket;
//
//    /**
//     * 创建线程池来管理客户端的连接线程
//     * 避免系统资源过度浪费
//     */
//    private ExecutorService threadPool;
//
//    /**
//     * 该属性用来存放客户端之间私聊的信息
//     */
//    private Map<String, Socket> allOut;
//
//    /**
//     * 构造方法，服务端初始化
//     */
//    public chatServer() {
//        try {
//            /*
//            * 创建ServerSocket，并申请服务端口
//			* 将来客户端就是通过该端口连接服务端程序的
//			*/
//            serverSocket = new ServerSocket(40012);
//
//			/*
//            * 初始化Map集合，存放客户端信息
//			*/
//            allOut = new HashMap<>();
//
//			/*
//            * 初始化线程池，设置线程的数量
//			*/
//            threadPool = Executors.newFixedThreadPool(10);
//
//			/*
//            * 初始化用来存放客户端输出流的集合，
//			* 每当一个客户端连接，就会将该客户端的输出流存入该集合；
//			* 每当一个客户端断开连接，就会将集合中该客户端的输出流删除；
//			* 每当转发一条信息，就要遍历集合中的所有输出流(元素)
//			* 因此转发的频率高于客户端登入登出的频率，
//			* 还是应该使用ArrayList来存储元素，仅限群聊，私聊不行
//			* allOut = new ArrayList<PrintWriter>();
//			*/
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /*
//    * 将客户端的信息以Map形式存入集合中
//    */
//    private void addOut(String key, Socket value) {
//        synchronized (this) {
//            allOut.put(key, value);
//        }
//    }
//
//    /*
//    * 将给定的输出流从共享集合中删除
//    * 参数为客户端nickName,作为Map的key键
//    */
//    private synchronized void removeOut(String key) {
//        allOut.remove(key);
//        System.out.println("当前在线人数为：" + allOut.size());
//    }
//
//    /*
//    * 将给定的消息转发给所有客户端
//    */
//    private synchronized void sendMsgToAll(String message) {
////        for (OutputStream out : allOut.values().) {
////            out.println(message);
////            System.out.println("当前在线人数为：" + allOut.size());
////        }
//    }
//
//    /*
//    * 将给定的消息转发给私聊的客户端
//    */
//    private synchronized void sendMsgToPrivate(String nickname, String message) throws IOException {
//        //将对应客户端的聊天信息取出作为私聊内容发送出去
//        OutputStream out = allOut.get(nickname).getOutputStream();
//
//        if (out != null) {
//
//            out.write(SocketHeaderUtils
//                    .getHeader(SocketHeaderUtils.CHART_SINGLE, nickname, message)
//                    .getBytes("utf-8"));
//            LogUtils.toE("当前在线私聊人数为：" + allOut.size());
//        }
//    }
//
//    /**
//     * 服务端启动的方法
//     */
//    public void start() {
//        try {
//            while (true) {
//            /*
//            * 监听10086端口
//			*/
//                LogUtils.toE("等待客户端连接... ... ");
//            /*
//            * Socket accept() 这是一个阻塞方法，会一直在10086端口进行监听
//			* 直到一个客户端连接上，此时该方法会将与这个客户端进行通信的Socket返回
//			*/
//                Socket socket = serverSocket.accept();
//                LogUtils.toE("客户端连接成功………………………………………………！ ");
//
//			/*
//            * 启动一个线程，由线程来处理客户端的请求，这样可以再次监听
//			* 下一个客户端的连接了
//			*/
//                Runnable run = new GetClientMsgHandler(socket);
//                threadPool.execute(run); //通过线程池来分配线程
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 该线程体用来处理给定的某一个客户端的消息，循环接收客户端发送
//     * 的每一个字符串，并输出到控制台
//     *
//     * @author Jacob
//     */
//    class GetClientMsgHandler implements Runnable {
//        /*
//        * 该属性是当前线程处理的具体的客户端的Socket
//        * @see java.lang.Runnable#run()
//        */
//        private Socket socket;
//
//		/*
//        * 获取客户端的地址信息
//		* private String hostIP;
//		*/
//
//        /*
//        * 获取客户端的昵称
//        */
//        private String nickName;
//
//        /*
//        * 创建构造方法
//        */
//        public GetClientMsgHandler(Socket socket) {
//            this.socket = socket;
//            /*
//            * 获取远端客户的Ip地址信息
//			* 保存客户端的IP地址字符串
//			* InetAddress address = socket.getInetAddress();
//			* hostIP = address.getHostAddress();
//			*/
//        }
//
//        boolean login = false;
//        boolean running = true;
//
//        private void ClientContent() {
//            try {
//
//                while (running) {
//                    BufferedReader br = new BufferedReader(
//                            new InputStreamReader(socket.getInputStream())
//                    );
//                    //读服务器端发来的数据，阻塞直到收到结束符\n或\r
//                    StringBuffer buffer = null;
//                    char[] chars = new char[SocketHeaderUtils.HeaderLength];
//                    if (br.read(chars) != -1) {
//                        String header = new String(chars);
//                        LogUtils.toE("strheader:" + header);
//                        if (header.length() == SocketHeaderUtils.HeaderLength) {
//                            buffer = new StringBuffer();
//                            buffer.append(header);
//                            long readStringLength = Long.parseLong(header.substring(2, 3))
//                                    + Long.parseLong(header.substring(3, SocketHeaderUtils.HeaderLength));
//                            long readingLength = 0;
//                            int tempInt;
//                            while ((tempInt = br.read()) != -1) {
//                                readingLength++;
//                                buffer.append((char) tempInt);
//                                LogUtils.toE("strchar:" + (char) tempInt);
//                                if (readingLength >= readStringLength) {
//                                    break;
//                                }
//                            }
//                        }
//                    }
//
//
//                    if (buffer != null) {
//                        String str = buffer.toString();
//                        LogUtils.toE("readLine:" + str);
//                        //服务端将昵称验证结果通过自身的输出流发送给客户端
//                        OutputStream out = socket.getOutputStream();
//                        String nameString = str.substring(
//                                SocketHeaderUtils.HeaderLength,
//                                SocketHeaderUtils.HeaderLength + Integer.parseInt(str.substring(2, 3))
//                        );
//
//                        if (!login) {
//                            if (TextUtils.isEmpty(nameString)) {
//                                out.write(SocketHeaderUtils
//                                        .getHeader(
//                                                SocketHeaderUtils.LOGIN_FAIL,
//                                                "",
//                                                ""
//                                        ).getBytes("utf-8"));
//                                running = false;
//                            }
//                            if (allOut.containsKey(nameString)) {
//                                out.write(SocketHeaderUtils
//                                        .getHeader(
//                                                SocketHeaderUtils.LOGIN_FAIL,
//                                                "",
//                                                ""
//                                        ).getBytes("utf-8"));
//                                running = false;
//                            }
//                            else {
//                                out.write(SocketHeaderUtils
//                                        .getHeader(
//                                                SocketHeaderUtils.LOGIN_SUCCESS,
//                                                "",
//                                                ""
//                                        ).getBytes("utf-8"));
//                                nickName = nameString;
//                                addOut(nameString, socket);
//                                Thread.sleep(100);
//
//                                //服务端通知所有客户端，某用户登录
//                                //   sendMsgToAll("[系统通知]：欢迎**" + nameString + "**登陆聊天室!");
//                                login = true;
//                            }
//                        }
//                        else {
//                            String typeTitle = str.substring(0, 2);
//                            if (typeTitle.equals(SocketHeaderUtils.CHART_SINGLE)) {
//                                sendMsgToPrivate(
//                                        typeTitle,
//                                        str.substring(
//                                                SocketHeaderUtils.HeaderLength +
//                                                        Integer.parseInt(str.substring(2, 3)),
//                                                str.length()
//                                        )
//                                );
//                            }
//
//                        }
//                        //用Handler把读取到的信息发到主线程
//                    }
//                    else {
//
//                        removeOut(nickName);
//                        running = false;
//                    }
//                }
//
//
//            } catch (Exception e) {
//                //连接超时后会抛异常
//                LogUtils.toNetError(e);
//                e.printStackTrace();
//            } finally {
////                    if (br!=null){
////                        try {
////                            br.close();
////                        } catch (IOException e) {
////                            e.printStackTrace();
////                        }
////                    }
//            }
//            //执行下一次循环
//            LogUtils.toE("执行下一次循环:" + "下一次循环开始");
//        }
//
//
//        @Override
//        public void run() {
//            ClientContent();
//        }
//    }

}