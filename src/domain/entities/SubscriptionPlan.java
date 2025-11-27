package domain.entities;

public abstract class SubscriptionPlan {
    public abstract boolean canEnroll(int currentEnrollments);
    public abstract String getPlanName();
    public abstract int getMaxEnrollments();
}
