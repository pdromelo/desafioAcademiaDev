package domain.entities;

public class BasicPlan extends SubscriptionPlan {
    private static final int MAX_ENROLLMENTS = 3;

    @Override
    public boolean canEnroll(int currentEnrollments) {
        return currentEnrollments < MAX_ENROLLMENTS;
    }

    @Override
    public String getPlanName() {
        return "BasicPlan";
    }

    @Override
    public int getMaxEnrollments() {
        return MAX_ENROLLMENTS;
    }

    @Override
    public String toString() {
        return "BasicPlan{maxEnrollments=" + MAX_ENROLLMENTS + "}";
    }
}
