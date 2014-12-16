package com.boundmap.panner;

import com.boundmap.panner.models.Waypoint;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.scene.layout.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





public class ApplicationState extends Region {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationState.class);

    public static class ApplicationStateEvent extends Event
    {
        public static final EventType<ApplicationStateEvent> MISSION_CONFIG_UPDATED = new EventType<>(ANY, "MISSION_CONFIG_UPDATED");
        public static final EventType<ApplicationStateEvent> SELECTED_WAYPOINT_UPDATED = new EventType<>(ANY, "SELECTED_WAYPOINT_UPDATED");

        public ApplicationStateEvent(EventType<? extends Event> arg0) {
            super(arg0);
        }
        public ApplicationStateEvent(Object arg0, EventTarget arg1, EventType<? extends Event> arg2) {
            super(arg0, arg1, arg2);
        }
    }

    private Waypoint selectedWaypoint;

    public ApplicationState() {
        super();
    }


    public void setSelectedWaypoint(Waypoint waypoint) {
        this.selectedWaypoint = waypoint;
        fireSelectedWaypointUpdatedEvent();
    }

    public Waypoint getSelectedWaypoint() {
        return selectedWaypoint;
    }


    private void fireSelectedWaypointUpdatedEvent() {
        fireEvent(new ApplicationStateEvent(ApplicationStateEvent.SELECTED_WAYPOINT_UPDATED));
    }
}
