package com.example.koshanti_beauty_bot.service;

import com.example.koshanti_beauty_bot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    public TelegramBot(BotConfig config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getBotKey();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                default:
                    sendMessage(chatId, "Извините, я не знаю такую команду, попробуй ещё..");
                    break;
            }
        }

    }

    private void startCommandReceived(long chatId, String name) {

        String birthdayMessage = "Мы не знаем, что тебе пожелать... Глупо желать красоты, самой прекрасной девушке. " +
                "Тебе не нужна удача, ведь ты всего можешь добиться сама. Пожелать счастья!? Но что такое счастье?! " +
                "Мы желаем тебе то, что нужно каждому человеку, то, что делает человека прекрасным, то, " +
                "что становится для него величайшей удачей и счастьем... Мы желаем тебе любить и быть бесконечно любимой! " +
                "С днем рождения!\n" +
                "\n" +
                "Есть люди, к которым тянет, рядом с которыми хорошо и надежно. Ты – одна из них! " +
                "Поэтому сегодня, в твой день рождения, тебя окружают твои верные друзья. " +
                "Так пусть же не только в праздники, но и в любой другой день рядом с тобой будут преданные тебе люди, " +
                "готовые прийти на помощь! В твой день рождения от всего сердца желаю тебе крепкого здоровья! " +
                "Иди по жизни легко, без ненужных тревог и изматывающих проблем. Пусть все заботы будут только в радость. " +
                "Счастья тебе, солнечных дней и большой удачи! С праздником!\n" +
                "... Мы дарим тебе виртуального друга(бро-бот) - с нас любой функционал, веб сайт(в разработке) " +
                "и поддержка. С тебя оставатся такой же и радовать нас - ведь без тебя это все бессмысленно! \n\n";

        String answer = "Приветствую тебя " + name +
                "\n Я бот-помошник Koshanti " +
                "\n - топ beauty мастера" +
                "\n - топ друга (лучше не найти)" +
                "\n - топ тату-мастера" +
                "\n - собеседника и психолога" +
                "\n В общем тебе сюда, по любому!!";

        sendMessage(chatId, birthdayMessage + answer);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
