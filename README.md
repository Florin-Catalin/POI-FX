# PTTFx JavaFX Application

This project is a JavaFX application demonstrating:
- Multiple windows with independent controls
- Integration of an interactive map using Leaflet in a WebView
- (Planned) Switching between different map types

---

## TODO List

- [x] Display the app on different windows (Window 1, Window 2, Map Window)
- [x] Add an interactive map (Leaflet via WebView)
- [ ] Switch between different types of maps (e.g., OpenStreetMap, satellite, etc.)
- [ ] Add more map markers and popups
- [x] Add a `Marker` class in Java
- [x] Add markers programmatically via Java code
- [x] Cluster overlapping markers when zoomed out and show a count
- [ ] Send many markers asynchronously and add them to the map
- [ ] Display all markers in a list in the UI
- [ ] Improve UI/UX for window and map navigation
- [ ] Add unit tests for controllers and map logic
- [ ] Package and document the application for distribution

---

## How to Run

1. **Build the project:**
   ```sh
   mvn clean javafx:run
   ```

2. **Usage:**
   - Window 1: Button to display a hello message and open the interactive map.
   - Window 2: Button to display a hello message.
   - Map Window: Shows an interactive map with a marker.

---

## Resources

- The interactive map HTML is in [`src/main/resources/ufc/ptt/pttfx/map.html`](src/main/resources/ufc/ptt/pttfx/map.html)
- Main application entry point: [`HelloApplication.java`](src/main/java/ufc/ptt/pttfx/HelloApplication.java)
- Map window logic: [`MapWebViewWindow.java`](src/main/java/ufc/ptt/pttfx/MapWebViewWindow.java)

---

## License

MIT License (add your license here)