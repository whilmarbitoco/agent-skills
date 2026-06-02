package com.pos.testing.ui;

/**
 * TestFX: automated JavaFX UI testing.
 * Simulates user interactions, verifies UI state.
 *
 * Setup:
 *   testImplementation 'org.testfx:testfx-core:4.0.18'
 *   testImplementation 'org.testfx:testfx-junit5:4.0.18'
 */
public class TestFxUiTesting {

    // Basic TestFX test structure:
    /*
    @ExtendWith(ApplicationExtension.class)
    class ProductViewTest {

        @Start
        void start(Stage stage) {
            ProductController controller = new ProductController(new FakeProductService());
            Scene scene = controller.createScene();
            stage.setScene(scene);
            stage.show();
        }

        @Test
        void shouldAddProduct(FxRobot robot) {
            // Click add button
            robot.clickOn("#addButton");
            // Type product name
            robot.clickOn("#nameField").write("Test Product");
            // Type price
            robot.clickOn("#priceField").write("99.99");
            // Click save
            robot.clickOn("#saveButton");
            // Verify table has new row
            verifyThat("#productTable", hasItems(1));
        }

        @Test
        void shouldShowErrorOnEmptyName(FxRobot robot) {
            robot.clickOn("#addButton");
            robot.clickOn("#saveButton");
            verifyThat("#errorLabel", LabeledMatchers.hasText("Name is required"));
        }
    }
    */

    // Key TestFX methods:
    // robot.clickOn("#id") — click by CSS ID
    // robot.write("text") — type text
    // robot.drag("#source").dropTo("#target") — drag and drop
    // verifyThat("#id", matcher) — assert UI state
    // robot.sleep(500) — wait for animation
}
