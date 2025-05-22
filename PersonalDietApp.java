import java.util.*;

// Main class for Personal Diet App
public class PersonalDietApp {
    private static Scanner scanner = new Scanner(System.in);
    private static UserProfile userProfile = new UserProfile();
    private static DailyNutritionTracker nutritionTracker = new DailyNutritionTracker();
    private static FoodDatabase foodDatabase = new FoodDatabase();
    
    public static void main(String[] args) {
        System.out.println("=== Welcome to Personal Diet & Nutrition App ===");
        
        while (true) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    setupUserProfile();
                    break;
                case 2:
                    calculateCalories();
                    break;
                case 3:
                    calculateBMI();
                    break;
                case 4:
                    trackDailyNutrients();
                    break;
                case 5:
                    getMealRecommendations();
                    break;
                case 6:
                    viewNutritionSummary();
                    break;
                case 7:
                    System.out.println("Thank you for using Personal Diet App!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println("\n" + "=".repeat(50) + "\n");
        }
    }
    
    private static void displayMainMenu() {
        System.out.println("1. Setup User Profile");
        System.out.println("2. Calculate Daily Calories");
        System.out.println("3. Calculate BMI");
        System.out.println("4. Track Daily Nutrients");
        System.out.println("5. Get Meal Recommendations");
        System.out.println("6. View Nutrition Summary");
        System.out.println("7. Exit");
    }
    
    private static void setupUserProfile() {
        System.out.println("\n=== User Profile Setup ===");
        
        System.out.print("Enter your age: ");
        userProfile.setAge(getIntInput(""));
        
        System.out.print("Enter your gender (M/F): ");
        userProfile.setGender(scanner.nextLine().toUpperCase().charAt(0));
        
        System.out.print("Enter your weight (kg): ");
        userProfile.setWeight(getDoubleInput(""));
        
        System.out.print("Enter your height (cm): ");
        userProfile.setHeight(getDoubleInput(""));
        
        System.out.println("\nActivity Levels:");
        System.out.println("1. Sedentary (little/no exercise)");
        System.out.println("2. Light (light exercise 1-3 days/week)");
        System.out.println("3. Moderate (moderate exercise 3-5 days/week)");
        System.out.println("4. Active (hard exercise 6-7 days/week)");
        System.out.println("5. Very Active (very hard exercise/physical job)");
        
        int activityChoice = getIntInput("Choose your activity level (1-5): ");
        userProfile.setActivityLevel(activityChoice);
        
        System.out.println("Profile setup completed!");
    }
    
    private static void calculateCalories() {
        if (!userProfile.isProfileComplete()) {
            System.out.println("Please setup your profile first!");
            return;
        }
        
        System.out.println("\n=== Daily Calorie Calculator ===");
        
        double bmr = userProfile.calculateBMR();
        double dailyCalories = userProfile.calculateDailyCalories();
        
        System.out.printf("Your BMR (Basal Metabolic Rate): %.0f calories\n", bmr);
        System.out.printf("Your Daily Calorie Needs: %.0f calories\n", dailyCalories);
        
        // Weight goals
        System.out.println("\nFor different goals:");
        System.out.printf("Weight Loss: %.0f calories (deficit of 500)\n", dailyCalories - 500);
        System.out.printf("Weight Maintenance: %.0f calories\n", dailyCalories);
        System.out.printf("Weight Gain: %.0f calories (surplus of 500)\n", dailyCalories + 500);
    }
    
    private static void calculateBMI() {
        if (userProfile.getWeight() == 0 || userProfile.getHeight() == 0) {
            System.out.println("Please setup your profile with weight and height first!");
            return;
        }
        
        System.out.println("\n=== BMI Calculator ===");
        
        double bmi = userProfile.calculateBMI();
        String category = userProfile.getBMICategory();
        
        System.out.printf("Your BMI: %.1f\n", bmi);
        System.out.println("Category: " + category);
        
        // BMI interpretation
        System.out.println("\nBMI Categories:");
        System.out.println("Underweight: < 18.5");
        System.out.println("Normal weight: 18.5 - 24.9");
        System.out.println("Overweight: 25 - 29.9");
        System.out.println("Obesity: ≥ 30");
    }
    
    private static void trackDailyNutrients() {
        System.out.println("\n=== Daily Nutrient Tracker ===");
        
        while (true) {
            System.out.println("\n1. Add Food Item");
            System.out.println("2. View Available Foods");
            System.out.println("3. View Today's Intake");
            System.out.println("4. Clear Today's Intake");
            System.out.println("5. Back to Main Menu");
            
            int choice = getIntInput("Choose an option: ");
            
            switch (choice) {
                case 1:
                    addFoodItem();
                    break;
                case 2:
                    foodDatabase.displayAvailableFoods();
                    break;
                case 3:
                    nutritionTracker.displayDailyIntake();
                    break;
                case 4:
                    nutritionTracker.clearDailyIntake();
                    System.out.println("Daily intake cleared!");
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    
    private static void addFoodItem() {
        System.out.print("Enter food name: ");
        String foodName = scanner.nextLine().toLowerCase();
        
        Food food = foodDatabase.getFood(foodName);
        if (food == null) {
            System.out.println("Food not found in database. Available foods:");
            foodDatabase.displayAvailableFoods();
            return;
        }
        
        double quantity = getDoubleInput("Enter quantity (grams): ");
        nutritionTracker.addFood(food, quantity);
        System.out.println("Food added successfully!");
    }
    
    private static void getMealRecommendations() {
        if (!userProfile.isProfileComplete()) {
            System.out.println("Please setup your profile first!");
            return;
        }
        
        System.out.println("\n=== Meal Recommendations ===");
        
        double dailyCalories = userProfile.calculateDailyCalories();
        System.out.printf("Based on your daily calorie needs: %.0f calories\n", dailyCalories);
        
        MealRecommendationEngine.generateRecommendations(dailyCalories, foodDatabase);
    }
    
    private static void viewNutritionSummary() {
        System.out.println("\n=== Nutrition Summary ===");
        nutritionTracker.displayDetailedNutritionSummary();
    }
    
    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return value;
    }
    
    private static double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        double value = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        return value;
    }
}

// User Profile Class
class UserProfile {
    private int age;
    private char gender;
    private double weight;
    private double height;
    private int activityLevel;
    
    // Getters and Setters
    public void setAge(int age) { this.age = age; }
    public void setGender(char gender) { this.gender = gender; }
    public void setWeight(double weight) { this.weight = weight; }
    public void setHeight(double height) { this.height = height; }
    public void setActivityLevel(int activityLevel) { this.activityLevel = activityLevel; }
    
    public int getAge() { return age; }
    public char getGender() { return gender; }
    public double getWeight() { return weight; }
    public double getHeight() { return height; }
    public int getActivityLevel() { return activityLevel; }
    
    public boolean isProfileComplete() {
        return age > 0 && weight > 0 && height > 0 && activityLevel > 0;
    }
    
    public double calculateBMR() {
        if (gender == 'M') {
            return 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {
            return 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }
    }
    
    public double calculateDailyCalories() {
        double bmr = calculateBMR();
        double[] multipliers = {0, 1.2, 1.375, 1.55, 1.725, 1.9};
        return bmr * multipliers[activityLevel];
    }
    
    public double calculateBMI() {
        double heightInMeters = height / 100.0;
        return weight / (heightInMeters * heightInMeters);
    }
    
    public String getBMICategory() {
        double bmi = calculateBMI();
        if (bmi < 18.5) return "Underweight";
        else if (bmi < 25) return "Normal weight";
        else if (bmi < 30) return "Overweight";
        else return "Obesity";
    }
}

// Food Class
class Food {
    private String name;
    private double calories;
    private double protein;
    private double carbs;
    private double fat;
    private double fiber;
    private double vitaminC;
    private double vitaminD;
    private double vitaminB12;
    private double calcium;
    private double iron;
    private double magnesium;
    private double potassium;
    private double zinc;
    private double folate;
    private double omega3;
    
    public Food(String name, double calories, double protein, double carbs, double fat) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }
    
    // Builder pattern for setting micronutrients
    public Food setFiber(double fiber) { this.fiber = fiber; return this; }
    public Food setVitaminC(double vitaminC) { this.vitaminC = vitaminC; return this; }
    public Food setVitaminD(double vitaminD) { this.vitaminD = vitaminD; return this; }
    public Food setVitaminB12(double vitaminB12) { this.vitaminB12 = vitaminB12; return this; }
    public Food setCalcium(double calcium) { this.calcium = calcium; return this; }
    public Food setIron(double iron) { this.iron = iron; return this; }
    public Food setMagnesium(double magnesium) { this.magnesium = magnesium; return this; }
    public Food setPotassium(double potassium) { this.potassium = potassium; return this; }
    public Food setZinc(double zinc) { this.zinc = zinc; return this; }
    public Food setFolate(double folate) { this.folate = folate; return this; }
    public Food setOmega3(double omega3) { this.omega3 = omega3; return this; }
    
    // Getters
    public String getName() { return name; }
    public double getCalories() { return calories; }
    public double getProtein() { return protein; }
    public double getCarbs() { return carbs; }
    public double getFat() { return fat; }
    public double getFiber() { return fiber; }
    public double getVitaminC() { return vitaminC; }
    public double getVitaminD() { return vitaminD; }
    public double getVitaminB12() { return vitaminB12; }
    public double getCalcium() { return calcium; }
    public double getIron() { return iron; }
    public double getMagnesium() { return magnesium; }
    public double getPotassium() { return potassium; }
    public double getZinc() { return zinc; }
    public double getFolate() { return folate; }
    public double getOmega3() { return omega3; }
}

// Food Database Class
class FoodDatabase {
    private Map<String, Food> foods;
    
    public FoodDatabase() {
        initializeFoods();
    }
    
    private void initializeFoods() {
        foods = new HashMap<>();
        
        // Initialize food database with nutritional information per 100g
        foods.put("apple", new Food("Apple", 52, 0.3, 14, 0.2)
            .setFiber(2.4).setVitaminC(4.6).setPotassium(107));
            
        foods.put("banana", new Food("Banana", 89, 1.1, 23, 0.3)
            .setFiber(2.6).setVitaminC(8.7).setPotassium(358).setMagnesium(27));
            
        foods.put("chicken breast", new Food("Chicken Breast", 165, 31, 0, 3.6)
            .setIron(0.7).setZinc(1.0).setVitaminB12(0.3));
            
        foods.put("rice", new Food("Rice", 130, 2.7, 28, 0.3)
            .setFiber(0.4).setMagnesium(25).setIron(0.8));
            
        foods.put("broccoli", new Food("Broccoli", 34, 2.8, 7, 0.4)
            .setFiber(2.6).setVitaminC(89.2).setFolate(63).setIron(0.7));
            
        foods.put("salmon", new Food("Salmon", 208, 22, 0, 12)
            .setOmega3(1.8).setVitaminD(11).setVitaminB12(3.2));
            
        foods.put("spinach", new Food("Spinach", 23, 2.9, 3.6, 0.4)
            .setFiber(2.2).setIron(2.7).setFolate(194).setMagnesium(79));
            
        foods.put("oats", new Food("Oats", 389, 16.9, 66, 6.9)
            .setFiber(10.6).setMagnesium(177).setZinc(4).setIron(4.7));
            
        foods.put("egg", new Food("Egg", 155, 13, 1.1, 11)
            .setVitaminB12(0.6).setVitaminD(2).setZinc(1.3));
            
        foods.put("milk", new Food("Milk", 42, 3.4, 5, 1)
            .setCalcium(113).setVitaminD(1.3).setVitaminB12(0.4));
            
        foods.put("almonds", new Food("Almonds", 579, 21.2, 21.6, 49.9)
            .setFiber(12.5).setMagnesium(270).setVitaminE(25.6).setCalcium(269));
            
        foods.put("sweet potato", new Food("Sweet Potato", 86, 1.6, 20.1, 0.1)
            .setFiber(3).setVitaminC(2.4).setPotassium(337).setMagnesium(25));
    }
    
    public Food getFood(String name) {
        return foods.get(name.toLowerCase());
    }
    
    public void displayAvailableFoods() {
        System.out.println("\nAvailable Foods:");
        System.out.println("-".repeat(40));
        for (String foodName : foods.keySet()) {
            Food food = foods.get(foodName);
            System.out.printf("%-15s - %3.0f cal/100g\n", 
                food.getName(), food.getCalories());
        }
    }
    
    public Set<String> getFoodNames() {
        return foods.keySet();
    }
    
    public Collection<Food> getAllFoods() {
        return foods.values();
    }
}

// Daily Nutrition Tracker Class
class DailyNutritionTracker {
    private List<FoodEntry> dailyIntake;
    
    public DailyNutritionTracker() {
        dailyIntake = new ArrayList<>();
    }
    
    public void addFood(Food food, double quantity) {
        dailyIntake.add(new FoodEntry(food, quantity));
    }
    
    public void clearDailyIntake() {
        dailyIntake.clear();
    }
    
    public void displayDailyIntake() {
        if (dailyIntake.isEmpty()) {
            System.out.println("No food items added today.");
            return;
        }
        
        System.out.println("\n=== Today's Food Intake ===");
        System.out.println("-".repeat(50));
        
        double totalCalories = 0;
        for (FoodEntry entry : dailyIntake) {
            double calories = (entry.getFood().getCalories() * entry.getQuantity()) / 100;
            totalCalories += calories;
            System.out.printf("%-15s %6.0fg - %4.0f cal\n", 
                entry.getFood().getName(), entry.getQuantity(), calories);
        }
        
        System.out.println("-".repeat(50));
        System.out.printf("Total Calories: %.0f\n", totalCalories);
    }
    
    public void displayDetailedNutritionSummary() {
        if (dailyIntake.isEmpty()) {
            System.out.println("No food items to analyze.");
            return;
        }
        
        NutritionSummary summary = calculateNutritionSummary();
        
        System.out.println("=== Detailed Nutrition Summary ===");
        System.out.println("-".repeat(40));
        
        // Macronutrients
        System.out.println("MACRONUTRIENTS:");
        System.out.printf("Calories: %.0f kcal\n", summary.totalCalories);
        System.out.printf("Protein: %.1f g\n", summary.totalProtein);
        System.out.printf("Carbohydrates: %.1f g\n", summary.totalCarbs);
        System.out.printf("Fat: %.1f g\n", summary.totalFat);
        System.out.printf("Fiber: %.1f g\n", summary.totalFiber);
        
        System.out.println("\nMICRONUTRIENTS:");
        // Vitamins
        System.out.println("Vitamins:");
        System.out.printf("  Vitamin C: %.1f mg\n", summary.totalVitaminC);
        System.out.printf("  Vitamin D: %.1f µg\n", summary.totalVitaminD);
        System.out.printf("  Vitamin B12: %.1f µg\n", summary.totalVitaminB12);
        System.out.printf("  Folate: %.1f µg\n", summary.totalFolate);
        
        // Minerals
        System.out.println("Minerals:");
        System.out.printf("  Calcium: %.1f mg\n", summary.totalCalcium);
        System.out.printf("  Iron: %.1f mg\n", summary.totalIron);
        System.out.printf("  Magnesium: %.1f mg\n", summary.totalMagnesium);
        System.out.printf("  Potassium: %.1f mg\n", summary.totalPotassium);
        System.out.printf("  Zinc: %.1f mg\n", summary.totalZinc);
        
        // Other nutrients
        System.out.println("Other:");
        System.out.printf("  Omega-3: %.1f g\n", summary.totalOmega3);
        
        // Daily value percentages (approximate)
        System.out.println("\n=== Daily Value Percentages (Approximate) ===");
        System.out.printf("Vitamin C: %.0f%% (RDA: 90mg men, 75mg women)\n", 
            (summary.totalVitaminC / 82.5) * 100);
        System.out.printf("Calcium: %.0f%% (RDA: 1000mg)\n", 
            (summary.totalCalcium / 1000) * 100);
        System.out.printf("Iron: %.0f%% (RDA: 18mg women, 8mg men)\n", 
            (summary.totalIron / 13) * 100);
        System.out.printf"Magnesium: %.0f%% (RDA: 400mg men, 310mg women)\n", 
            (summary.totalMagnesium / 355) * 100);
    }
    
    private NutritionSummary calculateNutritionSummary() {
        NutritionSummary summary = new NutritionSummary();
        
        for (FoodEntry entry : dailyIntake) {
            Food food = entry.getFood();
            double multiplier = entry.getQuantity() / 100.0;
            
            summary.totalCalories += food.getCalories() * multiplier;
            summary.totalProtein += food.getProtein() * multiplier;
            summary.totalCarbs += food.getCarbs() * multiplier;
            summary.totalFat += food.getFat() * multiplier;
            summary.totalFiber += food.getFiber() * multiplier;
            summary.totalVitaminC += food.getVitaminC() * multiplier;
            summary.totalVitaminD += food.getVitaminD() * multiplier;
            summary.totalVitaminB12 += food.getVitaminB12() * multiplier;
            summary.totalCalcium += food.getCalcium() * multiplier;
            summary.totalIron += food.getIron() * multiplier;
            summary.totalMagnesium += food.getMagnesium() * multiplier;
            summary.totalPotassium += food.getPotassium() * multiplier;
            summary.totalZinc += food.getZinc() * multiplier;
            summary.totalFolate += food.getFolate() * multiplier;
            summary.totalOmega3 += food.getOmega3() * multiplier;
        }
        
        return summary;
    }
}

// Food Entry Class
class FoodEntry {
    private Food food;
    private double quantity;
    
    public FoodEntry(Food food, double quantity) {
        this.food = food;
        this.quantity = quantity;
    }
    
    public Food getFood() { return food; }
    public double getQuantity() { return quantity; }
}

// Nutrition Summary Class
class NutritionSummary {
    public double totalCalories = 0;
    public double totalProtein = 0;
    public double totalCarbs = 0;
    public double totalFat = 0;
    public double totalFiber = 0;
    public double totalVitaminC = 0;
    public double totalVitaminD = 0;
    public double totalVitaminB12 = 0;
    public double totalCalcium = 0;
    public double totalIron = 0;
    public double totalMagnesium = 0;
    public double totalPotassium = 0;
    public double totalZinc = 0;
    public double totalFolate = 0;
    public double totalOmega3 = 0;
}

// Meal Recommendation Engine
class MealRecommendationEngine {
    public static void generateRecommendations(double dailyCalories, FoodDatabase foodDatabase) {
        double breakfastCals = dailyCalories * 0.25;
        double lunchCals = dailyCalories * 0.35;
        double dinnerCals = dailyCalories * 0.30;
        double snackCals = dailyCalories * 0.10;
        
        System.out.println("\n=== Meal Distribution ===");
        System.out.printf("Breakfast: %.0f calories (25%%)\n", breakfastCals);
        System.out.printf("Lunch: %.0f calories (35%%)\n", lunchCals);
        System.out.printf("Dinner: %.0f calories (30%%)\n", dinnerCals);
        System.out.printf("Snacks: %.0f calories (10%%)\n", snackCals);
        
        System.out.println("\n=== Sample Meal Plans ===");
        
        // Breakfast suggestions
        System.out.println("\nBREAKFAST OPTIONS:");
        System.out.printf("• 80g Oats + 200ml Milk = %.0f cal\n", 
            (389 * 0.8) + (42 * 2));
        System.out.printf("• 2 Eggs + 1 slice bread = %.0f cal\n", 
            (155 * 2) + 80);
        System.out.printf("• 150g Banana + 30g Almonds = %.0f cal\n", 
            (89 * 1.5) + (579 * 0.3));
        
        // Lunch suggestions
        System.out.println("\nLUNCH OPTIONS:");
        System.out.printf("• 150g Chicken Breast + 200g Rice + 100g Broccoli = %.0f cal\n", 
            (165 * 1.5) + (130 * 2) + (34 * 1));
        System.out.printf("• 200g Salmon + 150g Sweet Potato = %.0f cal\n", 
            (208 * 2) + (86 * 1.5));
        
        // Dinner suggestions
        System.out.println("\nDINNER OPTIONS:");
        System.out.printf("• 120g Chicken + 150g Rice + 100g Spinach = %.0f cal\n", 
            (165 * 1.2) + (130 * 1.5) + (23 * 1));
        System.out.printf("• 150g Salmon + 200g Sweet Potato + vegetables = %.0f cal\n", 
            (208 * 1.5) + (86 * 2) + 50);
        
        // Snack suggestions
        System.out.println("\nHEALTHY SNACK OPTIONS:");
        System.out.printf("• 1 Apple + 20g Almonds = %.0f cal\n", 
            (52 * 1.5) + (579 * 0.2));
        System.out.printf("• 200ml Milk + 1 Banana = %.0f cal\n", 
            (42 * 2) + (89 * 1));
        
        System.out.println("\n=== Nutrition Tips ===");
        System.out.println("• Include protein in every meal");
        System.out.println("• Eat 5-9 servings of fruits and vegetables daily");
        System.out.println("• Choose whole grains over refined grains");
        System.out.println("• Include healthy fats (omega-3, nuts, avocado)");
        System.out.println("• Stay hydrated - drink 8-10 glasses of water daily");
        System.out.println("• Limit processed foods and added sugars");
    }
}
