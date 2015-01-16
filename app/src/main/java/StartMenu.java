import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.example.flight.GamePlay;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.example.flight.StartGame;

public class StartMenu implements Screen {

    // наш основной класс
    final StartGame game;

    // Объявим все необходимые объекты
    private Stage stage;
    private TextButton play, exit;
    private Table table;
    private LabelStyle labelStyle;

    // Конструктор принимает объект нашего основого класса (объяснения позже)
    public StartMenu(final StartGame gam) {
        game = gam;

        // Сцена -- она поможет существенно уменьшить количество кода и упростить нам жизнь
        stage = new Stage(new ScreenViewport());

        // Скин для кнопок. Изображения вы найдете по ссылке внизу статьи
        Skin skin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("images/game/images.pack"));
        skin.addRegions(buttonAtlas);
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = game.font;
        textButtonStyle.up = skin.getDrawable("button-up");
        textButtonStyle.down = skin.getDrawable("button-down");
        textButtonStyle.checked = skin.getDrawable("button-up");

        labelStyle = new LabelStyle();
        labelStyle.font = game.font;
        table = new Table();
        table.setFillParent(true);

        // Кнопка играть. Добавляем новый listener, чтобы слушать события касания. После касания, выбрирует и переключает на экран выбора уровней, а этот экран уничтожается
        play = new TextButton("Играть", textButtonStyle);
        play.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.vibrate(20);
                return true;
            };
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GamePlay(game));
                dispose();
            };
        });

        // Кнопка выхода. Вообще это не обязательно. Просто для красоты, ибо обычно пользователь жмет на кнопку телефона.
        exit = new TextButton("Выход", textButtonStyle);
        exit.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.vibrate(20);
                return true;
            };
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                dispose();
            };
        });
        table.add(play);
        table.row();
        table.add(exit);
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);  // Устанавливаем нашу сцену основным процессором для ввода (нажатия, касания, клавиатура etc.)
        Gdx.input.setCatchBackKey(true); // Это нужно для того, чтобы пользователь возвращался назад, в случае нажатия на кнопку Назад на своем устройстве
    }

    @Override
    public void render(float delta) {
        // Очищаем экран и устанавливаем цвет фона черным
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Рисуем сцену
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        // Уничтожаем сцену и объект game.
        stage.dispose();
        game.dispose();
    }
}
