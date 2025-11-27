package domain.entities;

public class PremiumPlan extends SubscriptionPlan {
    @Override
    public boolean canEnroll(int currentEnrollments) {
        return true; // Unlimited
    }

    @Override
    public String getPlanName() {
        return "PremiumPlan";
    }

    @Override
    public int getMaxEnrollments() {
        return Integer.MAX_VALUE; // Unlimited
    }

    @Override
    public String toString() {
        return "PremiumPlan{unlimited enrollments}";
    }
}
