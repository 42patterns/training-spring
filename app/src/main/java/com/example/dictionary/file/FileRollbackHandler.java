package com.example.dictionary.file;

import org.apache.log4j.Logger;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;

import java.io.File;

public class FileRollbackHandler extends TransactionSynchronizationAdapter {

    private String filename;

    public FileRollbackHandler(String filename) {
        this.filename = filename;
    }

    private static Logger log = Logger.getLogger(FileRollbackHandler.class);
    @Override
    public void afterCompletion(int status) {
        if (TransactionSynchronization.STATUS_COMMITTED != status) {
            log.info("Transaction [status="+status+"]. Deleting the file: " + filename);

            File f = new File(filename);
            f.delete();
        }

    }

}
