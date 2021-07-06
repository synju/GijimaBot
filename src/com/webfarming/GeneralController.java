package com.webfarming;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.LinkedList;
import java.util.List;

public class GeneralController {
	public SendMessage handle(String data, Message message) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(message.getChatId().toString());

		if(data.equals("/message_freight_manager")) {
			displayFreightManagers(sendMessage);
		}
		else {
			// Default to Menu
			displayMenu(sendMessage);
		}

		return sendMessage;
	}

	public void displayMenu(SendMessage sendMessage) {
		sendMessage.setText("How can we help you?");
		sendMessage.setParseMode("Markdown");

		// ROWS
		List<InlineKeyboardButton> row1 = new LinkedList<>();
		List<InlineKeyboardButton> row2 = new LinkedList<>();
		List<InlineKeyboardButton> row3 = new LinkedList<>();
		List<InlineKeyboardButton> row4 = new LinkedList<>();
		List<InlineKeyboardButton> row5 = new LinkedList<>();
		row1.add(generateUrlButton("Live Support", "https://telegram.me/gijimadurban"));
		row2.add(generateButton("Message my Freight Manager", "/message_freight_manager"));
		row3.add(generateUrlButton("Contact Us", "https://gijimadeliveries.co.za/contact-us/"));
		row4.add(generateUrlButton("Join Gijima Deliveries Channel", "http://t.me/gijimadeliveries"));
		row5.add(generateUrlButton("Request Waybill", "https://gijimams.webfarming.co.za/?p=waybill_quote_request"));

		// Add Rows
		List<List<InlineKeyboardButton>> rowCollection = new LinkedList<>();
		rowCollection.add(row1);
		rowCollection.add(row2);
		rowCollection.add(row3);
		rowCollection.add(row4);
		rowCollection.add(row5);

		// Keyboard
		InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
		inlineKeyboardMarkup.setKeyboard(rowCollection);

		// Add Keyboard to message
		sendMessage.setReplyMarkup(inlineKeyboardMarkup);
	}

	public void displayFreightManagers(SendMessage sendMessage) {
		sendMessage.setText("Which Freight Manager do you want to Contact?");
		sendMessage.setParseMode("Markdown");

		// ROWS
		List<InlineKeyboardButton> row1 = new LinkedList<>();
		List<InlineKeyboardButton> row2 = new LinkedList<>();
		List<InlineKeyboardButton> row3 = new LinkedList<>();
		List<InlineKeyboardButton> row4 = new LinkedList<>();
		row1.add(generateUrlButton("Prajesh", "http://t.me/Prajeshr"));
		row2.add(generateUrlButton("Khoza", "http://t.me/Nomageje"));
		row3.add(generateUrlButton("Louis", "http://t.me/louis_gijima"));
		row4.add(generateButton("Return to Main Menu", "/start"));

		// Add Rows
		List<List<InlineKeyboardButton>> rowCollection = new LinkedList<>();
		rowCollection.add(row1);
		rowCollection.add(row2);
		rowCollection.add(row3);
		rowCollection.add(row4);

		// Keyboard
		InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
		inlineKeyboardMarkup.setKeyboard(rowCollection);

		// Add Keyboard to message
		sendMessage.setReplyMarkup(inlineKeyboardMarkup);
	}

	public InlineKeyboardButton generateButton(String text, String callbackData) {
		InlineKeyboardButton button = new InlineKeyboardButton();
		button.setText(text);
		button.setCallbackData(callbackData);
		return button;
	}

	public InlineKeyboardButton generateUrlButton(String text, String url) {
		InlineKeyboardButton button = new InlineKeyboardButton();
		button.setText(text);
		button.setUrl(url);
		return button;
	}
}
