package com.example.zhu.jokes.MakeAJoke;

import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;


/**
 * Created by zhu on 16/4/28.
 */
public class MakeAJokePresenterImplTest {

    @Mock
    SharedPreferences.Editor mEditor;

    @Mock
    SharedPreferences data;


    @Mock
    MakeAJokeInteractorFactoryImpl makeAJokeInteractorFactory;

    @Mock
    MakeAJokeInteractor makeAJokeInteractor;

    @Mock
    MakeAJokeView makeAJokeView;

    @Captor
    ArgumentCaptor<MakeAJokeInteractor.OnGetAJokeFinishedListener> getAJokeFinishedListenerArgumentCaptor;

    MakeAJokePresenterImpl makeAJokePresenter;

    String DEFAULT_TEXT = "请点一下 再笑一个";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(makeAJokeInteractorFactory.createMakeAJokeInteractor()).thenReturn(makeAJokeInteractor);
        makeAJokePresenter = new MakeAJokePresenterImpl(makeAJokeView, makeAJokeInteractorFactory);

    }


    @Test
    public void testOnError() throws Exception {
        String msg = "message";
        makeAJokePresenter.onError(msg);
        verify(makeAJokeView).hideProgress();
        verify(makeAJokeView).showErrorMsg(msg);
    }

    @Test
    public void testOnRefreshBtnClick() throws Exception {
        makeAJokePresenter.onRefreshBtnClick();
        verify(makeAJokeView).showProgress();
        verify(makeAJokeInteractor).getAJoke(getAJokeFinishedListenerArgumentCaptor.capture());
//        String joke = "A JOKE";
//        getAJokeFinishedListenerArgumentCaptor.getValue().onFinished(joke);
    }

    @Test
    public void testSaveData() throws Exception {
        String joke = "A JOKE";

        // when...thenReturn 要写在真实方法调用之前
        when(data.edit()).thenReturn(mEditor);
        when(makeAJokeView.getJoke()).thenReturn(joke);

        makeAJokePresenter.saveData(data);

        verify(makeAJokeView).getJoke();
        verify(mEditor).putString("jokeText", joke);
        verify(mEditor).commit();
        verify(makeAJokeInteractor).saveData(data);
//        doNothing().when(makeAJokeInteractor).saveData(data);
    }

    @Test
    public void testRestoreData() throws Exception {
        String joke = "A JOKE";

        when(data.getString("jokeText", DEFAULT_TEXT)).thenReturn(joke);

        makeAJokePresenter.restoreData(data);

        verify(data).getString("jokeText", DEFAULT_TEXT);
        verify(makeAJokeView).setJoke(joke);
        verify(makeAJokeInteractor).restoreData(data);
    }
}