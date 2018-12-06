package ru.bellintegrator.hrbase.OutputProfile;

public interface WrapperProfile {
    public interface Data {}
    public interface OrganizationShort extends Data, OrganizationProfile.Short {}
    public interface OrganizationFull extends Data, OrganizationProfile.Full {}
}
