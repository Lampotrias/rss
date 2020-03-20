package com.example.rss.presentation.itemList.state;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.disposables.Disposable;

public abstract class Paginator<T> {

    public interface ViewController<T> {
        void showData(Boolean show, List<T> data);
        void showErrorMessage(Throwable error);
        void showEmptyError(Boolean show, Throwable error);
        void showRefreshProgress(Boolean show);
        void showEmptyProgress(Boolean show);
        void showPageProgress(Boolean show);
        void showEmptyView(Boolean show);
    }

    public interface Page<P>{
        Maybe<List<P>> invoke(int page);
    }

    private final ViewController<T> viewController;
    private final Paginator.Page<T> dataSource;

    private final int FIRST_PAGE = 1;

    private State<T> currentState = new EMPTY();
    private int currentPage = 0;
    private List<T> currentData = new ArrayList<>();
    private Disposable disposable;

    public Paginator(Paginator.Page<T> dataSource, ViewController<T> viewController) {
        this.dataSource = dataSource;
        this.viewController = viewController;
    }

    public void refresh() {
        currentState.refresh();
    }

    public void loadNewPage() {
        currentState.loadNewPage();
    }

    public void release() {
        currentState.release();
    }

    private void loadPage(int page) {
        disposable = this.dataSource.invoke(page)
                .subscribe(
                        currentState::newData,
                        currentState::fail);
    }

    private class EMPTY extends State<T> {

        @Override
        public void refresh() {
            super.refresh();
            currentState = new EMPTY_PROGRESS();
            loadPage(FIRST_PAGE);
        }

        @Override
        public void release() {
            super.release();
            currentState = new RELEASED();
            disposable.dispose();
        }
    }

    private class EMPTY_PROGRESS extends State<T> {

        @Override
        public void restart() {
            loadPage(FIRST_PAGE);
        }

        @Override
        public void newData(List<T> data) {
            if (!data.isEmpty()) {
                currentState = new DATA();
                currentData.clear();
                currentData.addAll(data);
                currentPage = FIRST_PAGE;
                viewController.showData(true, currentData);
                viewController.showEmptyProgress(false);
            } else {
                currentState = new EMPTY_DATA();
                viewController.showEmptyProgress(false);
                viewController.showEmptyView(true);
            }
        }

        @Override
        public void fail(Throwable error) {
            currentState = new EMPTY_ERROR();
            viewController.showEmptyProgress(false);
            viewController.showEmptyError(true, error);
        }

        @Override
        public void release() {
            currentState = new RELEASED();
            disposable.dispose();
        }
    }

    private class EMPTY_ERROR extends State<T> {

        @Override
        public void restart() {
            currentState = new EMPTY_PROGRESS();
            viewController.showEmptyError(false, null);
            viewController.showEmptyProgress(true);
            loadPage(FIRST_PAGE);
        }

        @Override
        public void refresh() {
            currentState = new EMPTY_PROGRESS();
            viewController.showEmptyError(false, null);
            viewController.showEmptyProgress(true);
            loadPage(FIRST_PAGE);
        }

        @Override
        public void release() {
            currentState = new RELEASED();
            disposable.dispose();
        }
    }

    private class EMPTY_DATA extends State<T> {

        @Override
        public void restart() {
            currentState = new EMPTY_PROGRESS();
            viewController.showEmptyView(false);
            viewController.showEmptyProgress(true);
            loadPage(FIRST_PAGE);
        }

        @Override
        public void refresh() {
            currentState = new EMPTY_PROGRESS();
            viewController.showEmptyView(false);
            viewController.showEmptyProgress(true);
            loadPage(FIRST_PAGE);
        }

        @Override
        public void release() {
            currentState = new RELEASED();
            disposable.dispose();
        }
    }

    private class DATA extends State<T> {

        @Override
        public void restart() {
            currentState = new EMPTY_PROGRESS();
            viewController.showData(false, new ArrayList<>());
            viewController.showEmptyProgress(true);
            loadPage(FIRST_PAGE);
        }

        @Override
        public void refresh() {
            currentState = new REFRESH();
            viewController.showRefreshProgress(true);
            loadPage(FIRST_PAGE);
        }

        @Override
        public void loadNewPage() {
            currentState = new PAGE_PROGRESS();
            viewController.showPageProgress(true);
            loadPage(currentPage + 1);
        }

        @Override
        public void release() {
            currentState = new RELEASED();
            disposable.dispose();
        }
    }

    private class REFRESH extends State<T> {

        @Override
        public void restart() {
            currentState = new EMPTY_PROGRESS();
            viewController.showData(false, new ArrayList<>());
            viewController.showRefreshProgress(false);
            viewController.showEmptyProgress(true);
            loadPage(FIRST_PAGE);
        }

        @Override
        public void newData(List<T> data) {
            if (!data.isEmpty()) {
                currentState = new DATA();
                currentData.clear();
                currentData.addAll(data);
                currentPage = FIRST_PAGE;
                viewController.showRefreshProgress(false);
                viewController.showData(true, currentData);
            } else {
                currentState = new EMPTY_DATA();
                currentData.clear();
                viewController.showData(false, new ArrayList<>());
                viewController.showRefreshProgress(false);
                viewController.showEmptyView(true);
            }
        }

        @Override
        public void fail(Throwable error) {
            currentState = new DATA();
            viewController.showRefreshProgress(false);
            viewController.showErrorMessage(error);
        }

        @Override
        public void release() {
            currentState = new RELEASED();
            disposable.dispose();
        }
    }

    private class PAGE_PROGRESS extends State<T> {

        @Override
        public void restart() {
            currentState = new EMPTY_PROGRESS();
            viewController.showData(false, new ArrayList<>());
            viewController.showPageProgress(false);
            viewController.showEmptyProgress(true);
            loadPage(FIRST_PAGE);
        }

        @Override
        public void newData(List<T> data) {
            if (!data.isEmpty()) {
                currentState = new DATA();
                currentData.addAll(data);
                currentPage++;
                viewController.showPageProgress(false);
                viewController.showData(true, currentData);
            } else {
                currentState = new ALL_DATA();
                viewController.showPageProgress(false);
            }
        }

        @Override
        public void refresh() {
            currentState = new REFRESH();
            viewController.showPageProgress(false);
            viewController.showRefreshProgress(true);
            loadPage(FIRST_PAGE);
        }

        @Override
        public void fail(Throwable error) {
            currentState = new DATA();
            viewController.showPageProgress(false);
            viewController.showErrorMessage(error);
        }

        @Override
        public void release() {
            currentState = new RELEASED();
            disposable.dispose();
        }
    }

    private class ALL_DATA extends State<T> {

        @Override
        public void restart() {
            currentState = new EMPTY_PROGRESS();
            viewController.showData(false, new ArrayList<>());
            viewController.showEmptyProgress(true);
            loadPage(FIRST_PAGE);
        }

        @Override
        public void refresh() {
            currentState = new REFRESH();
            viewController.showRefreshProgress(true);
            loadPage(FIRST_PAGE);
        }

        @Override
        public void release() {
            currentState = new RELEASED();
            disposable.dispose();
        }
    }

    private class RELEASED extends State<T> {

    }
}
