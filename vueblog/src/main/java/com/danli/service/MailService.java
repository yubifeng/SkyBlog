package com.danli.service;

/**
 * @author: fanfanli
 * @date: 2021/8/10
 */public interface MailService {

    /**
     * 发送普通文本邮件
     *
     * @param toAccount 收件人
     * @param subject 主题
     * @param content 内容
     */
    void sendSimpleMail(String toAccount, String subject, String content);

    /**
     * 发送HTML邮件
     *
     * @param toAccount 收件人
     * @param subject 主题
     * @param content 内容（可以包含<html>等标签）
     */
    void sendHtmlMail(String toAccount, String subject, String content);


}

