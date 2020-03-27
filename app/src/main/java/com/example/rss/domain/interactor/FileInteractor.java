package com.example.rss.domain.interactor;

import com.example.rss.domain.File;
import com.example.rss.domain.executor.IPostExecutionThread;
import com.example.rss.domain.executor.IThreadExecutor;
import com.example.rss.domain.repositories.IRepository;
import com.example.rss.domain.xml.XmlEntityHasFile;

import javax.inject.Inject;

import io.reactivex.Maybe;

public class FileInteractor extends BaseInteractor {

    private final IRepository repository;

    @Inject
    public FileInteractor(IRepository repository, IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    private Maybe<Long> saveFile(File file) {
        return repository.addFile(file).compose(getIOToMainTransformerMaybe());
    }

    public Maybe<Integer> deleteFileById(Long id) {
        return repository.deleteFileById(id).compose(getIOToMainTransformerMaybe());
    }

    public Maybe<String> getLinkByFileId(Long fileId) {
        String emptyPath = "";
        if (fileId > 0) {
            return this.getFileById(fileId).map(File::getPath).defaultIfEmpty(emptyPath);
        } else
            return Maybe.just(emptyPath);
    }

    public Maybe<Long> parseFileAndSave(XmlEntityHasFile rawObject) {
        if (rawObject.getEnclosure() != null) {
            return repository.addFile(prepareFile(rawObject)).compose(getIOToMainTransformerMaybe());
        } else
            return Maybe.just(0L);
    }

    public Maybe<File> getFileById(Long id) {
        return repository.getFileById(id).compose(getIOToMainTransformerMaybe());
    }

    private File prepareFile(XmlEntityHasFile xmlEntityHasFile) {
        File file = new File();
        file.setDescription(xmlEntityHasFile.getEnclosure().getDescription());
        file.setPath(xmlEntityHasFile.getEnclosure().getPath());
        file.setExternal(false);
        file.setType("image");
        return file;
    }
}
