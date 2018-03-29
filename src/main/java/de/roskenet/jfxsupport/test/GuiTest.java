package de.roskenet.jfxsupport.test;

import org.assertj.core.util.Preconditions;
import org.junit.BeforeClass;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.testfx.framework.junit.ApplicationTest;

import de.felixroske.jfxsupport.AbstractFxmlView;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiTest extends ApplicationTest implements ApplicationContextAware {

    @BeforeClass
    public static void setHeadlessMode() {
        String headlessProp = System.getProperty("JAVAFX_HEADLESS", "true");
        Boolean headless = Boolean.valueOf(headlessProp);
        String geometryProp = System.getProperty("JAVAFX_GEOMETRY", "1600x1200-32");
        
        if (headless) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("java.awt.headless", "true");
            System.setProperty("headless.geometry", geometryProp);
        } else {
            System.setProperty("java.awt.headless", "false");
        }
    }
    
    public static boolean headless;
    public static String geometry;
    
    private ApplicationContext appCtx;
    private AbstractFxmlView viewBean;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appCtx = applicationContext;
    }

    protected void init(Class<? extends AbstractFxmlView> viewClass) throws Exception {
        super.init();
        viewBean = appCtx.getBean(viewClass);
    }
    
    protected void init(final AbstractFxmlView viewBean) throws Exception {
        super.init();
        this.viewBean = viewBean;
    }
   
//    public static void setHeadless() {
//        if (headless) {
//            System.setProperty("testfx.robot", "glass");
//            System.setProperty("glass.platform", "Monocle");
//            System.setProperty("monocle.platform", "Headless");
//            System.setProperty("testfx.headless", "true");
//            System.setProperty("prism.order", "sw");
//            System.setProperty("prism.text", "t2k");
//            System.setProperty("java.awt.headless", "true");
//            System.setProperty("headless.geometry", geometry);
//        } else {
//            System.setProperty("java.awt.headless", "false");
//        }
//    }

//  @After
//  public void tearDown() throws Exception {
//      FxToolkit.hideStage();
//      FxToolkit.cleanupStages();
//      release(new KeyCode[] {});
//      release(new MouseButton[] {});
//  }
    
    /* Just a shortcut to retrieve widgets in the GUI. */
    public <T extends Node> T find(final String query) {
        return lookup(query).query();
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Preconditions.checkNotNull(viewBean, "No view to set up! Have you called init() before?");
        
        Scene scene = viewBean.getView().getScene();
        
        if (scene == null) {
            Scene x = new Scene(viewBean.getView());
            stage.setScene(x);
        }
        else {
            stage.setScene(scene);
        }
        stage.show();
    }

}