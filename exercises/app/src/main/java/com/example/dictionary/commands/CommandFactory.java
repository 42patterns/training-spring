package com.example.dictionary.commands;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.dictionary.CommandParameters;
import com.example.dictionary.TranslationProcess;

@Component
public class CommandFactory {

    @Autowired
    BeanFactory factory;

    public Command getCommand(TranslationProcess process) {
        CommandParameters params = process.getParams();
        switch (params.command) {
            case "exit":
                return factory.getBean(ExitCommand.class, process);
            case "search":
                return factory.getBean(TranslationCommand.class, process);
            case "show-all":
                return factory.getBean(ShowAllWordsCommand.class, process);
            case "show-saved":
                return factory.getBean(ShowSavedWordsCommand.class, process);
            case "save":
                return factory.getBean(SaveWordsCommand.class, process);
            default:
                return factory.getBean(VoidCommand.class, process);
        }
    }

}
