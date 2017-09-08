package indi.sword.util.base;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.Message;
import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import indi.sword.util.base.bean.TextFile;

/**
 * 协议普及 ： POP3 、IMAP 、SMPT
 * POP3 (Post Office Protocol 3) ：协议允许电子邮件客户端下载服务器上的邮件，但是在客户端的操作（如移动邮件、标记已读等），不会反馈到服务器上。   (程序 READ_ONLY)
 * IMAP (Internet Mail Access Protocol） ：即交互式邮件存取协议。提供webmail 与电子邮件客户端之间的双向通信，客户端的操作都会反馈到服务器上，对邮件进行的操作，服务器上的邮件也会做相应的动作。(READ_WRITE)
 * SMTP (Simple Mail Transfer Protocol) ：即简单邮件传输协议。 它是一组用于从源地址到目的地址传输邮件的规范，通过它来控制邮件的中转方式。 SMTP客户向服务器分别指出发信人和收信人的电子邮件地址。
 * 
 * 记得把协议开起来
 * 
 * 
 * 邮箱的一些使用工具类
 * 
 * @Descrption
 * @author rd_jianbin_lin
 * @Date Sep 8, 2017 8:19:37 PM
 */
public class MailUtils {

	private static final Logger logger = LoggerFactory.getLogger(MailUtils.class);

	private MimeMessage mimeMsg; 		//MIME邮件对象   
    private Session session; 			//邮件会话对象   
    private Properties props; 			//系统属性  		-- 用于连接邮件服务器的参数配置（发送邮件时才需要用到）
    private String username;   			//smtp认证账号
    private String password; 			//smtp认证密码 
    private Multipart multipart; 		//Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象   
    
    /*---------------------构造邮件服务------------------------*/
    public MailUtils(){}
    /**
     * @Description: TODO 构造邮件对象
     * @author WangYanan 347576073@qq.com
     * @date 2015年8月28日 上午9:53:13
     */
    public MailUtils(boolean isSSL, String hostName, int port){
    	//构建邮箱服务
        setSmtpHost(isSSL, hostName, port);
        //构建邮箱主体
        createMimeMessage();
    }
    /**
     * @Description: 构建邮箱服务
     * @author WangYanan 347576073@qq.com
     * @date 2015年8月28日 上午10:02:01
     */
    private void setSmtpHost(boolean isSSL, String hostName, int port) {
        if(props == null)
		 {
			props = System.getProperties(); 	//获得系统属性对象
		}
        props.put("mail.smtp.host",hostName); 				//设置SMTP主机
        props.put("mail.smtp.ssl.enable", isSSL);
        props.put("mail.smtp.port", port);
    }
    /**
     * @Description: 创建MIME邮件对象
     * @author WangYanan 347576073@qq.com
     * @date 2015年8月28日 上午10:02:27
     */
    private boolean createMimeMessage()   
    {   
        try {
            session = Session.getDefaultInstance(props); //获得邮件会话对象   --根据参数配置，创建会话对象（为了发送邮件准备的）
            mimeMsg = new MimeMessage(session); //创建MIME邮件对象   
            multipart = new MimeMultipart();
            return true;
        } catch(Exception e){ 
        	e.printStackTrace();
            System.err.println("创建MIME邮件对象失败！"+e);   
            return false;   
        }
    }     
     
    
    /*---------------------自定义邮件配置------------------------*/
    /** 
     * 设置SMTP是否需要验证
     */  
    public void setNeedAuth(boolean need) {   
        System.out.println("设置smtp身份认证：mail.smtp.auth = "+need);   
        if(props == null) {
			props = System.getProperties();
		}   
        if(need){   
            props.put("mail.smtp.auth","true");   
        }else{   
            props.put("mail.smtp.auth","false");   
        }   
    }   
  
    /** 
     * 设置用户名和密码 
     */  
    public void setNamePass(String name,String pass) {   
        username = name;   
        password = pass;   
    }   
  
    /** 
     * 设置邮件主题
     */  
    public boolean setSubject(String mailSubject) {   
        System.out.println("设置邮件主题！");   
        try{   
            mimeMsg.setSubject(mailSubject);   
            return true;   
        }   
        catch(Exception e) {   
        	e.printStackTrace();
            System.err.println("设置邮件主题发生错误！");   
            return false;   
        }   
    }  
      
    /**  
     * 设置邮件正文 
     */   
    public boolean setBody(String mailBody) {   
        try{   
            BodyPart bp = new MimeBodyPart();   
            bp.setContent(""+mailBody,"text/html;charset=GBK");   
            multipart.addBodyPart(bp);   
            return true;   
        } catch(Exception e){   
        	e.printStackTrace();
        System.err.println("设置邮件正文时发生错误！"+e);   
        return false;   
        }   
    }
    
    /**  
     * 添加附件
     * @param filename 文件的绝对路径，仅支持txt附件
     */
    public boolean addFileAffix(String filename) {   
        System.out.println("增加邮件附件："+filename);
        if(filename != null){
            try{   
                BodyPart bp = new MimeBodyPart();
                FileDataSource fileds = new TextFile(filename);   
                bp.setDataHandler(new DataHandler(fileds));   
                bp.setFileName(MimeUtility.encodeText(fileds.getName()));  
                multipart.addBodyPart(bp);   
                return true;   
            } catch(Exception e){
            	e.printStackTrace();
                System.err.println("增加邮件附件："+filename+"发生错误！"+e);   
                return false;   
            } 
        }else {
        	return true;
        }
  
    }   
      
    /**  
     * 设置发信人
     */   
    public boolean setFrom(String from) {   
        System.out.println("设置发信人！");   
        try{   
            mimeMsg.setFrom(new InternetAddress(from)); //设置发信人   
            return true;   
        } catch(Exception e) { 
        	e.printStackTrace();
            return false;   
        }   
    }
    
    /**  
     * 设置收信人   
     */   
    public boolean setTo(String to){   
        if(to == null) {
			return false;
		}   
        try{   
            mimeMsg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));   
            return true;   
        } catch(MessagingException e) {   
        	e.printStackTrace();
            return false;   
        }     
    }   
      
    /**  
     * 设置抄送人   
     */   
    public boolean setCopyTo(String copyto)   
    {   
        if(copyto == null) {
			return true;
		}   
        try{   
        mimeMsg.setRecipients(Message.RecipientType.CC,InternetAddress.parse(copyto));   
        return true;   
        }   
        catch(Exception e)   
        { 
        	e.printStackTrace();
        	return false; }   
    }
    
    /*--------------------------发送邮件服务---------------------------*/
    /**
     * 
     * @Descrption 发送邮件
     * @author rd_jianbin_lin
     * @Date Sep 8, 2017 8:57:33 PM
     */
    public boolean sendOut()
    {   
        try{   
            mimeMsg.setContent(multipart);   
            mimeMsg.saveChanges();   
            System.out.println("正在发送邮件....");
            
            Session mailSession = Session.getInstance(props,null);
            Transport transport = mailSession.getTransport("smtp");
            transport.connect((String)props.get("mail.smtp.host"),username,password);   
            transport.sendMessage(mimeMsg,mimeMsg.getRecipients(Message.RecipientType.TO));
            
            System.out.println("发送邮件成功！");
            transport.close();
              
            return true;   
        } catch(Exception e) {
            System.err.println("邮件发送失败！"+e);
            e.printStackTrace();
            return false;
        }   
    }
	
    
    /*--------------------------操作邮件服务---------------------------*/
	/**
	 * 解析成功之后删除邮件服务器端的简历
	 * 
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Sep 8, 2017 8:32:14 PM
	 */
	public static void delEmail(Message msg) {
		try {
			msg.setFlag(Flags.Flag.DELETED, true);

		} catch (MessageRemovedException e) {

			logger.error("the mail is removed ...");

		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			saveEmailState(msg);
		}
	}

	/**
	 * 设置邮件未已读
	 * 
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Jul 3, 2017 2:45:58 PM
	 * @param msg
	 */
	public static void readEmail(Message msg) {
		try {
			msg.setFlag(Flags.Flag.SEEN, true);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存修改状态
	 * 
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Sep 8, 2017 5:48:59 PM
	 */
	public static void saveEmailState(Message msg) {
		try {

			msg.saveChanges();

		} catch (MessageRemovedException e) {

			logger.error("the mail is removed ...");

		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取邮件状态
	 * 
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Sep 8, 2017 5:34:49 PM
	 */
	public static String getMessageFlag(Message msg) {

		String state = "";
		try {
			Flag[] flags = msg.getFlags().getSystemFlags();

			for (Flag flag : flags) {
				if (flag == Flags.Flag.ANSWERED) {
					state = "ANSWERED";
					break;
				} else if (flag == Flags.Flag.DRAFT) {
					state = "DRAFT";
					break;
				} else if (flag == Flags.Flag.FLAGGED) {
					state = "FLAGGED";
					break;
				} else if (flag == Flags.Flag.RECENT) {
					state = "RECENT";
					break;
				} else if (flag == Flags.Flag.SEEN) {
					state = "SEEN";
					break;
				} else if (flag == Flags.Flag.USER) {
					state = "USER";
					break;
				} else {
					state = "NOTHING";
					break;
				}
			}
		} catch (MessageRemovedException e) {
			logger.error("邮件已经被删除了....");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return state;
	}
}
