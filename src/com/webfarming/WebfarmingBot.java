package com.webfarming;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class WebfarmingBot extends TelegramLongPollingBot {
	private final GeneralController generalController;
	Message message;
	String messageType = "Other";

	public WebfarmingBot() {
		this.generalController = new GeneralController();
	}

	public void sendMsg(SendMessage sendMessage) {
		try {
			execute(sendMessage);
		}
		catch(TelegramApiException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpdateReceived(Update update) {
		message = update.getMessage();

		try {
			User user = (update.hasCallbackQuery()) ? update.getCallbackQuery().getFrom() : update.getMessage().getFrom();
			String data = (update.hasCallbackQuery()) ? update.getCallbackQuery().getData() : message.getText();

			if(data != null) {
				if(update.hasCallbackQuery()) {
					messageType = "Callback";
					CallbackQuery callbackQuery = update.getCallbackQuery();
					message = callbackQuery.getMessage();
					this.sendMsg(this.generalController.handle(data, message));
					System.out.println("[messageId: " + message.getMessageId() + "] - [username: " + user.getUserName() + "] - [messageType: " + messageType + "] - [message: " + data + "]");
				}
				else if(message != null) {
					messageType = "Command";
					this.sendMsg(this.generalController.handle(data, message));
					System.out.println("[messageId: " + message.getMessageId() + "] - [username: " + user.getUserName() + "] - [messageType: " + messageType + "] - [message: " + data + "]");
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getBotUsername() {
		return "gijimabot";
	}

	@Override
	public String getBotToken() {
		return "1678832139:AAFaYXGGRn2PPk5n_Ls1AU9T7013paelaV0";
	}
}
