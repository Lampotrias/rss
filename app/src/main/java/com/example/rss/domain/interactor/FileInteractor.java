package com.example.rss.domain.interactor;

import com.example.rss.domain.File;
import com.example.rss.domain.executor.IPostExecutionThread;
import com.example.rss.domain.executor.IThreadExecutor;
import com.example.rss.domain.repositories.IRepository;
import com.example.rss.domain.xml.XmlChannelRawObject;

import javax.inject.Inject;

import io.reactivex.Maybe;

public class FileInteractor extends BaseInteractor {

    private final IRepository repository;

    @Inject
    public FileInteractor(IRepository repository, IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    private Maybe<Long> saveFile(File file){
        return repository.addFile(file).compose(getIOToMainTransformerMaybe());
    }

    public Maybe<Long> parseFileAndSave(XmlChannelRawObject rawObject){
        return repository.addFile(prepareFile(rawObject)).compose(getIOToMainTransformerMaybe());
    }

    private File prepareFile(XmlChannelRawObject xmlChannelRawObject){
        File file = new File();
        file.setDescription(xmlChannelRawObject.getFile().getDescription());
        file.setPath(xmlChannelRawObject.getFile().getPath());
        file.setExternal(false);
        file.setType("image");
        return file;
    }
}
