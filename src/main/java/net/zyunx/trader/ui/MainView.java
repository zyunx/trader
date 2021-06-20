package net.zyunx.trader.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {

        /**
     * 
     */
    private static final long serialVersionUID = 1L;

        public MainView() {
                add(new Button("Click me", e -> Notification.show("Hello, Spring+Vaadin user!")));
        }
}